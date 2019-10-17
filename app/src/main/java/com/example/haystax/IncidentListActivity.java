package com.example.haystax;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;

import com.example.haystax.adapters.IncidentRecyclerAdapter;
import com.example.haystax.adapters.OnIncidentListener;
import com.example.haystax.models.Incidents;
import com.example.haystax.requests.Api;
import com.example.haystax.requests.Post;
import com.example.haystax.requests.ServiceGenerator;
import com.example.haystax.requests.responses.IncidentResponse;
import com.example.haystax.util.Testing;
import com.example.haystax.util.VerticalSpacingItemDecorator;
import com.example.haystax.viewmodels.IncidentListViewModel;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.http.POST;

import static com.example.haystax.util.Constants.COOKIE_VALUE;

//to use retrofit call
//import retrofit2.Call;


public class IncidentListActivity extends BaseActivity implements OnIncidentListener  {

    private Api apiTest;

    public static OkHttpClient client;

    private String cookieValue = "";

    private IncidentListViewModel mIncidentListViewModel;

    ArrayList<HashMap<String, String>> contactList;



    private ArrayList<String> listDataTitle = new ArrayList<String>();

    private RecyclerView recyclerView;
    private IncidentRecyclerAdapter mAdapter;

    public String TAG = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.incident_list);
        client = new OkHttpClient();
        postRequest();



    }

    public void init(){
        mIncidentListViewModel = ViewModelProviders.of(this).get(IncidentListViewModel.class);
        initRecyclerView();
        subscribeObservers();
        getIncidents();
        if(!mIncidentListViewModel.isViewingIncidents())
        {
            displayDetails();
        }
    }

    private void initRecyclerView(){
        mAdapter = new IncidentRecyclerAdapter(this);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(30);
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void subscribeObservers(){
        mIncidentListViewModel.getIncidents().observe(this, new Observer<List<Incidents>>() {
            @Override
            public void onChanged(List<Incidents> incidents) {
                //will get triggered if anything is updated, deleted
                if (incidents != null) {
                    //when app is closed it starts back again in the list
                    if(mIncidentListViewModel.isViewingIncidents()){
                        Testing.printIncidents(incidents,"testing incidents list");
                        mIncidentListViewModel.setIsPerformingQuery(false);
                        mAdapter.setIncidents(incidents);
                    }

                }

            }
        });

    }

    private void searchIncidentsApi(){
        mIncidentListViewModel.searchIncidentApi();

    }

    //display incidents
    private void displayDetails(){
        mIncidentListViewModel.setIsViewingIncidents(false);
        mAdapter.displayDetails();
    }

    @Override
    public void OnIncidentClick(int position) {
        Intent intent = new Intent(this, IncidentActivity.class);
        intent.putExtra("incident",mAdapter.getSelectedIncident(position));
        startActivity(intent);
    }

    @Override
    public void onIncidentDetailsClick(String incident) {
        mAdapter.displayDetails();
        mIncidentListViewModel.searchIncidentApi();

    }

    @Override
    public void onBackPressed() {
        if(mIncidentListViewModel.isBackPressed()){
            super.onBackPressed();
        }
        else {
            //go back to incidents list
            //displayDetails();
            super.onBackPressed();
        }

    }



    public void postRequest()
    {
        String url = "https://app.haystax.com/server/gate/login";

        MediaType JSON = MediaType.parse("application/json");
        JSONObject data = new JSONObject();
        try {
            data.put("email","mobilehaystax@gmail.com");
            data.put("password","haystax123!");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON,data.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {
                if(response.isSuccessful())
                {
                    //String myRepsponse = response.body().string();
                    //Log.d("test", "onResponse: " + myRepsponse);
                    String[] cookie = response.headers("Set-Cookie").get(0).split(" ");
                    COOKIE_VALUE = cookie[0];
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            init();
                        }
                    });

                }
            }
        });





    }


    public void getIncidents()
    {
        searchIncidentsApi();
    }





    /*public void retrofit(){
        Api api = ServiceGenerator.getApi();

        //login call

        Post post = new Post("mobilehaystax@gmail.com","haystax123!");
        Call<Post> loginCall = api.createPost(post);


        loginCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Log.d("testing reponse", "onResponse: server response "+ response.toString());
                if(response.code() == 200)
                {
                    Log.d("testing reponse ", "onResponse: " + response.body().toString());
                    Post postResponse = response.body();
                    Log.d("testing reponse recipe", "onResponse: retrieved recipe" + postResponse.toString());



                    //String[] cookie = response.headers().get("Set-Cookie").split(";");
                    String cookie = response.headers().get("Set-Cookie");
                    cookieValue = cookie;
                    Log.d("test", "cookie: " + cookie);
                }else{
                    try {
                        Log.d("testing reponse else ", "onResponse: " + response.errorBody().string());
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });




    }

    public void retrofitGetIncidents(){
        Api api = ServiceGenerator.getApi();

        //get incidents
        Call<IncidentResponse> incidentCall = api
                .getIncidents(cookieValue
        );

        incidentCall.enqueue(new Callback<IncidentResponse>() {
            @Override
            public void onResponse(Call<IncidentResponse> call, Response<IncidentResponse> response) {

                Log.d("testing reponse", "onResponse: server response "+ response.toString());
                if(response.code() == 200)
                {
                    Log.d("testing reponse ", "onResponse: " + response.body().toString());
                    IncidentResponse postResponse = response.body();
                    Log.d("testing reponse recipe", "onResponse: retrieved recipe" + postResponse.toString());
                }else{
                    try {
                        Log.d("testing reponse else ", "onResponse: " + response.errorBody().string());
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<IncidentResponse> call, Throwable t) {

            }
        });

    }*/



}
