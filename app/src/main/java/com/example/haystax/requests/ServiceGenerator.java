package com.example.haystax.requests;

import com.example.haystax.util.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//instantiate retrofit instance
public class ServiceGenerator
{
    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();
    private static Api api = retrofit.create(Api.class);


    public static Api getApi()
    {
        return api;
    }
}
