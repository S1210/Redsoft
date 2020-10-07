package ru.alex.redsoft.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.alex.redsoft.models.Product;
import ru.alex.redsoft.models.Products;

public interface IDatumApi {
    @GET("products")
    Call<Products> getCityEntries();

    @GET("products")
    Call<Products> getCityEntriesWithFilter(@Query(value = "filter[title]") String filter);

    @GET("products/{id}")
    Call<Product> getCityEntries(@Path("id") int id);
}
