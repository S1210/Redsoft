package ru.alex.redsoft.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import ru.alex.redsoft.R;
import ru.alex.redsoft.adapters.RecyclerViewMainAdapter;
import ru.alex.redsoft.contractors.MainContractor;
import ru.alex.redsoft.models.Datum;
import ru.alex.redsoft.presenters.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainContractor.View {

    public static final String ID_PRODUCT = "idProduct";
    public static final String COUNT_PRODUCT = "countProduct";
    public static final String NAME_PRODUCT = "nameProduct";
    private final long DELAY = 500; // milliseconds

    private MainContractor.Presenter mPresenter;

    private CoordinatorLayout clMain;
    private RecyclerView recyclerView;
    private ImageButton ibClear;
    private MaterialEditText etSearch;
    private ProgressBar pbMain;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainPresenter(this);
        init();
    }

    private void init() {
        clMain = findViewById(R.id.cl_main);
        ibClear = findViewById(R.id.ib_clear);
        etSearch = findViewById(R.id.et_search);
        pbMain = findViewById(R.id.pb_main);
        ibClear.setOnClickListener(v -> {
            etSearch.setText("");
            mPresenter.loadProducts();
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                mPresenter.loadProductsWithFilter(Objects.requireNonNull(etSearch.getText()).toString());
                            }
                        },
                        DELAY
                );
            }
        });
        recyclerView = findViewById(R.id.rv_main);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    if ((layoutManager.getChildCount() + layoutManager.findFirstVisibleItemPosition()) >= layoutManager.getItemCount()) {
                        mPresenter.loadProducts();
                    }
                }
            }
        });
        mPresenter.loadProducts();
    }

    @Override
    public void setAdapter(RecyclerViewMainAdapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void openProduct(Datum datum, int position) {
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra(ID_PRODUCT, datum.getId());
        intent.putExtra(COUNT_PRODUCT, datum.getCount());
        intent.putExtra(NAME_PRODUCT, datum.getTitle());
        startActivityForResult(intent, position);
    }

    @Override
    public void hideProgressBar() {
        pbMain.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        Snackbar.make(clMain, R.string.error_message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showProgressBar() {
        runOnUiThread(() -> pbMain.setVisibility(View.VISIBLE));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.updateProduct(requestCode, Objects.requireNonNull(data)
                .getIntExtra(COUNT_PRODUCT, 0));
    }
}