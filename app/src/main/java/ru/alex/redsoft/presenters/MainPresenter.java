package ru.alex.redsoft.presenters;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.alex.redsoft.adapters.RecyclerViewMainAdapter;
import ru.alex.redsoft.api.ApiManager;
import ru.alex.redsoft.contractors.MainContractor;
import ru.alex.redsoft.models.Datum;
import ru.alex.redsoft.models.Products;

public class MainPresenter implements MainContractor.Presenter, Callback<Products> {

    private MainContractor.View mView;

    private RecyclerViewMainAdapter adapter;
    private List<Datum> datumList;
    private ApiManager apiManager;

    public MainPresenter(MainContractor.View mView) {
        this.mView = mView;
        apiManager = new ApiManager();
    }


    @Override
    public void loadProducts() {
        mView.showProgressBar();
        apiManager.getProductEntries(this);
    }

    @Override
    public void loadProductsWithFilter(String filter) {
        mView.showProgressBar();
        ((Activity) mView).runOnUiThread(() -> adapter.clearList());
        apiManager.getProductEntriesWithFilter(filter, this);
    }

    @Override
    public void updateProduct(int position, int count) {
        adapter.updateProduct(position, count);
    }

    @Override
    public void onResponse(@NonNull Call<Products> call, Response<Products> response) {
        if (response.isSuccessful()) {
            Products products = response.body();
            RecyclerViewMainAdapter.OnProductClickListener productClickListener = (datum, position)
                    -> mView.openProduct(datum, position);
            if (datumList != null) {
                adapter.addProducts(Objects.requireNonNull(products).getData());
            } else {
                datumList = (Objects.requireNonNull(products).getData());
                adapter = new RecyclerViewMainAdapter(datumList, productClickListener);
                mView.setAdapter(adapter);
            }
            mView.hideProgressBar();
        }
    }

    @Override
    public void onFailure(@NonNull Call<Products> call, @NonNull Throwable t) {
        mView.hideProgressBar();
        mView.showError();
    }
}
