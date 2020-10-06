package ru.alex.redsoft.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ru.alex.redsoft.R;
import ru.alex.redsoft.contractors.ProductContractor;
import ru.alex.redsoft.presenters.ProductPresenter;

public class ProductActivity extends AppCompatActivity implements ProductContractor.View {

    private ProductContractor.Presenter pPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        pPresenter = new ProductPresenter(this);
        init();
    }

    private void init() {

    }
}