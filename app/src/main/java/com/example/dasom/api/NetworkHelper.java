package com.example.dasom.api;

import java.net.NetworkInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkHelper {

    final static String url = "http://unitaemin.run.goorm.io/dasom/";
    //final static int port = 8000;


    private static Retrofit retrofit;


    public static ApiService getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);

    }
}
