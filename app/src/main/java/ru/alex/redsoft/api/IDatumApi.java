package ru.alex.redsoft.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.alex.redsoft.models.Datum;
import ru.alex.redsoft.models.Product;

public interface IDatumApi {
    @GET("products")
    Call<Product> getCityEntries();
    @GET("product/{id}")
    Call<Datum> getCityEntries(@Path("id") int id);
}
