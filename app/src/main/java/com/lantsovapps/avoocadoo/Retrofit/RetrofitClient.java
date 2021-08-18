package com.lantsovapps.avoocadoo.Retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit client;
    private static final String BASE_URL = "https://api.spoonacular.com/recipes/";

    public static Retrofit getClient(){
        if(client == null){
            client = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return client;
    }

    private RetrofitClient() {
    }
}
