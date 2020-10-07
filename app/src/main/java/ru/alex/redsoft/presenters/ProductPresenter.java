package ru.alex.redsoft.presenters;

import androidx.annotation.NonNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.alex.redsoft.api.ApiManager;
import ru.alex.redsoft.contractors.ProductContractor;
import ru.alex.redsoft.models.Datum;
import ru.alex.redsoft.models.Product;
import ru.alex.redsoft.models.Products;

public class ProductPresenter implements ProductContractor.Presenter, Callback<Product> {

    private ProductContractor.View pView;

    private ApiManager apiManager;
    private Datum datum;


    public ProductPresenter(ProductContractor.View pView) {
        this.pView = pView;
        apiManager = new ApiManager();
    }

    @Override
    public void loadContent(int id) {
        apiManager.getProductEntries(id, this);
    }

    @Override
    public void onResponse(@NonNull Call<Product> call, @NonNull Response<Product> response) {
        if (response.isSuccessful()) {
            datum = (Objects.requireNonNull(response.body())).getDatum();
            pView.setValue(datum);
            pView.hideProgressBar();

        }
    }

    @Override
    public void onFailure(@NonNull Call<Product> call, @NonNull Throwable t) {
        pView.hideProgressBar();
        pView.showError();
    }
}
