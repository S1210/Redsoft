package ru.alex.redsoft.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import ru.alex.redsoft.R;
import ru.alex.redsoft.contractors.ProductContractor;
import ru.alex.redsoft.models.Category;
import ru.alex.redsoft.models.Datum;
import ru.alex.redsoft.presenters.ProductPresenter;

public class ProductActivity extends AppCompatActivity implements ProductContractor.View {

    private ProductContractor.Presenter pPresenter;

    private CoordinatorLayout clProduct;
    private LinearLayout llContent;
    private ProgressBar pbProduct;
    private Toolbar toolbarProduct;
    private ImageView ivProduct;
    private TextView tvCategory;
    private TextView tvNameProduct;
    private TextView tvManufacturer;
    private TextView tvPrice;
    private TextView tvCountProduct;
    private TextView tvDescription;
    private LinearLayout llBtnCount;
    private Button btnAddCart;
    private ImageButton ibCountIncrease;
    private ImageButton ibCountDecrease;
    private Intent intent;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        pPresenter = new ProductPresenter(this);
        intent = getIntent();
        init();
        count = intent.getIntExtra(MainActivity.COUNT_PRODUCT, 0);
        pPresenter.loadContent(intent.getIntExtra(MainActivity.ID_PRODUCT, 0));
    }

    private void init() {
        clProduct = findViewById(R.id.cl_product);
        llContent = findViewById(R.id.ll_content);
        pbProduct = findViewById(R.id.pb_product);
        ivProduct = findViewById(R.id.iv_product);
        tvCategory = findViewById(R.id.tv_category);
        tvNameProduct = findViewById(R.id.tv_name_product);
        tvManufacturer = findViewById(R.id.tv_manufacturer);
        tvPrice = findViewById(R.id.tv_price);
        tvCountProduct = findViewById(R.id.tv_count_product);
        tvDescription = findViewById(R.id.tv_description);
        llBtnCount = findViewById(R.id.ll_btn_count);
        btnAddCart = findViewById(R.id.btn_add_cart);
        ibCountIncrease = findViewById(R.id.ib_count_increase);
        ibCountDecrease = findViewById(R.id.ib_count_decrease);
        initToolbar();
        setClickListener();
    }

    private void setClickListener() {
        btnAddCart.setOnClickListener(v -> {
            count++;
            btnAddCart.setVisibility(View.GONE);
            tvCountProduct.setText(String.valueOf(count));
            llBtnCount.setVisibility(View.VISIBLE);
        });
        ibCountIncrease.setOnClickListener(v -> {
            count++;
            tvCountProduct.setText(String.valueOf(count));
        });
        ibCountDecrease.setOnClickListener(v -> {
            count--;
            if (count == 0) {
                llBtnCount.setVisibility(View.GONE);
                btnAddCart.setVisibility(View.VISIBLE);
            } else {
                tvCountProduct.setText(String.valueOf(count));
            }
        });
    }

    @SuppressLint("RestrictedApi")
    private void initToolbar() {
        toolbarProduct = findViewById(R.id.toolbar_product);
        toolbarProduct.setTitle(intent.getStringExtra(MainActivity.NAME_PRODUCT));
        setSupportActionBar(toolbarProduct);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent();
            intent.putExtra(MainActivity.COUNT_PRODUCT, count);
            setResult(RESULT_OK, intent);
            this.finish();
        }
        return true;
    }

    @Override
    public void hideProgressBar() {
        pbProduct.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        Snackbar.make(clProduct, R.string.error_message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setValue(Datum datum) {
        tvNameProduct.setText(datum.getTitle());
        tvManufacturer.setText(datum.getProducer());
        Picasso.get().load(datum.getImageUrl()).into(ivProduct);
        tvDescription.setText(datum.getShortDescription());
        StringBuilder builder = new StringBuilder();
        if (datum.getCategories().size() > 0) {
            for (Category category : datum.getCategories()) {
                builder.append(category.getTitle()).append("\n\n");
            }
            tvCategory.setText(builder.toString());
        }
        tvPrice.setText(String.valueOf(datum.getPrice()));
        if (count > 0) {
            btnAddCart.setVisibility(View.GONE);
            tvCountProduct.setText(String.valueOf(count));
            llBtnCount.setVisibility(View.VISIBLE);
        }
        llContent.setVisibility(View.VISIBLE);
    }
}