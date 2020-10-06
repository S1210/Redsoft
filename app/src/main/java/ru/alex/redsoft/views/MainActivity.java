package ru.alex.redsoft.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import ru.alex.redsoft.R;
import ru.alex.redsoft.adapters.RecyclerViewMainAdapter;
import ru.alex.redsoft.contractors.MainContractor;
import ru.alex.redsoft.presenters.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainContractor.View {

    private MainContractor.Presenter mPresenter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainPresenter(this);
        init();
    }

    private void init() {
        recyclerView = findViewById(R.id.rv_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mPresenter.loadProducts();
    }

    @Override
    public void setAdapter(RecyclerViewMainAdapter adapter) {
        recyclerView.setAdapter(adapter);
    }
}