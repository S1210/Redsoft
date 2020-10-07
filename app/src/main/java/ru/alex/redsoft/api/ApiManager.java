package ru.alex.redsoft.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.alex.redsoft.models.Product;
import ru.alex.redsoft.models.Products;

public class ApiManager {

    private final IDatumApi datumApi;

    public ApiManager() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rstestapi.redsoftdigital.com/api/v1/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        datumApi = retrofit.create(IDatumApi.class);
    }

    public void getProductEntries(Callback<Products> callback) {
        Call<Products> datumEntries = datumApi.getCityEntries();
        datumEntries.enqueue(callback);
    }

    public void getProductEntriesWithFilter(String filter, Callback<Products> callback) {
        Call<Products> datumEntries = datumApi.getCityEntriesWithFilter(filter);
        datumEntries.enqueue(callback);
    }

    public void getProductEntries(int id, Callback<Product> callback) {
        Call<Product> datumEntries = datumApi.getCityEntries(id);
        datumEntries.enqueue(callback);
    }
}
