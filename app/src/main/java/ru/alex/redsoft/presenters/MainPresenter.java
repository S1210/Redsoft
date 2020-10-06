package ru.alex.redsoft.presenters;

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
import ru.alex.redsoft.models.Product;

public class MainPresenter implements MainContractor.Presenter, Callback<Product> {

    private MainContractor.View mView;
    private MainContractor.Repository mRepository;

    private RecyclerViewMainAdapter adapter;
    private List<Datum> datumList;
    private ApiManager apiManager;

    public MainPresenter(MainContractor.View mView) {
        this.mView = mView;
        apiManager = new ApiManager();
    }


    @Override
    public void loadProducts() {
        apiManager.getCityEntries(this);
    }

    @Override
    public void onResponse(@NonNull Call<Product> call, Response<Product> response) {
        if (response.isSuccessful()) {
            Product product = response.body();
            datumList = Objects.requireNonNull(product).getData();
            RecyclerViewMainAdapter.OnProductClickListener productClickListener = new RecyclerViewMainAdapter.OnProductClickListener() {
                @Override
                public void onProductClick(Datum datum) {

                }
            };
            adapter = new RecyclerViewMainAdapter(datumList, productClickListener);
            mView.setAdapter(adapter);
        }
    }

    @Override
    public void onFailure(@NonNull Call<Product> call, @NonNull Throwable t) {

    }
}
