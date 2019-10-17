package com.example.haystax.requests;

import com.example.haystax.requests.responses.IncidentResponse;
import com.example.haystax.requests.responses.loginResponse;
import com.example.haystax.util.Constants;
import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


//all requests for the api
public interface Api
{


    //JSON Request
    @Headers("Content-Type: application/json")
    @POST("gate/login")
    Call<Post> createPost(@Body Post post);

    //get incidents request
    //@Headers("Content-Type: application/json")
    @GET("api/incidents")
    Call<IncidentResponse> getIncidents(@Header("Cookie") String SESSION_ID);



}
