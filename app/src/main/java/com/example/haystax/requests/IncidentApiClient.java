package com.example.haystax.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.haystax.AppExecutors;
import com.example.haystax.models.Incidents;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.haystax.util.Constants.COOKIE_VALUE;
import static com.example.haystax.util.Constants.NETWORK_TIMEOUT;

public class IncidentApiClient
{
    private static final String TAG = "IncidentApiClient";

    private static IncidentApiClient instance;
    private RetrieveIncidentsRunnable retrieveIncidentsRunnable;
    private RetrieveIncidentRunnable retrieveIncidentRunnable;


    private MutableLiveData<List<Incidents>> incidents;
    private MutableLiveData<Incidents> incident;


    public static IncidentApiClient getInstance(){
        if(instance == null)
        {
            instance = new IncidentApiClient();
        }
        return instance;
    }

    private IncidentApiClient(){
        incidents = new MutableLiveData<>();
        incident = new MutableLiveData<>();

    }

    public LiveData<List<Incidents>> getIncidents(){
        return incidents;
    }

    public LiveData<Incidents> getIncident(){
        return incident;
    }


    public void searchIncidentApi(){
        if(retrieveIncidentsRunnable != null){
            retrieveIncidentsRunnable = null;
        }
        retrieveIncidentsRunnable = new RetrieveIncidentsRunnable();
        final Future handler = AppExecutors.getInstance().networkIO().submit(retrieveIncidentsRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //let the user know its timed out
                handler.cancel(true);

            }
        },NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);


    }

    public void searchIncidentById(String incidentId){
        if(retrieveIncidentRunnable != null)
        {
            retrieveIncidentRunnable = null;
        }
        retrieveIncidentRunnable = new RetrieveIncidentRunnable(incidentId);
        final Future handler = AppExecutors.getInstance().networkIO().submit(retrieveIncidentRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //let the user know its timed out
                handler.cancel(true);
            }
        },NETWORK_TIMEOUT,TimeUnit.MILLISECONDS);
    }


    //parameters for get query
    private class RetrieveIncidentsRunnable implements Runnable {

        private String email;
        private String password;
        boolean cancelRequest;

        public RetrieveIncidentsRunnable() {
            cancelRequest = false;
        }

        @Override
        public void run() {

            OkHttpClient client = new OkHttpClient();

            client.newCall(request()).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.code() == 200) {
                        //Log.d(TAG, "onResponse: success");

                        String myResponse = response.body().string();
                        JSONObject Jobject = null;
                        try {
                            Jobject = new JSONObject(myResponse);
                            JSONArray Jarray = Jobject.getJSONArray("incidents");
                            //Log.d(TAG, "onResponse: JARRAY: " + Jarray);

                            List<Incidents> list = new ArrayList<>();
                            //Incidents incidents2  = new Incidents();
                            String x = "";
                            for (int i = 0; i < Jarray.length(); i++) {
                                Incidents incidents2 = new Incidents();// <-- fresh instance!
                                //Log.d(TAG, "onResponse: loop: " + Jarray.getJSONObject(i).getJSONObject("overview"));
                                JSONObject overviewObject = Jarray.getJSONObject(i).getJSONObject("overview");
                                x = overviewObject.getString("title");
                                String x2 = overviewObject.getString("date");
                                //Log.d(TAG, "onResponse: overview: " + x + " : " + x2);
                                JSONObject detailsObject = Jarray.getJSONObject(i).getJSONObject("details");
                                String x3 = detailsObject.getString("summary");
                                //Log.d(TAG, "onResponse: summary: " + x3);
                                JSONObject idObject = Jarray.getJSONObject(i);
                                String x4 = idObject.getString("id");
                                //Log.d(TAG, "onResponse: id: " + x4);
                                incidents2.setTitle(x);
                                Calendar cal = Calendar.getInstance();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                Date date = dateFormat.parse(x2);
                                cal.setTime(date);
                                incidents2.setDate(String.valueOf(date));
                                incidents2.setSummary(x3);
                                incidents2.setId(x4);
                                list.add(incidents2);
                            }

                            //2019-09-18T20:55:37+00:00
                            //list.add(incidents2);
                            //Log.d(TAG, "onResponse: list: " + list);

                            incidents.postValue(list);



                        } catch (JSONException e) {
                            e.printStackTrace();
                            incidents.postValue(null);
                        }catch (ParseException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Log.e(TAG, "onResponse: error" + response.body().string());
                        incidents.postValue(null);
                    }

                }
            });


        }

        private Request request() {
            String url = "https://app.haystax.com/server/api/incidents";

            return new Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Cookie", COOKIE_VALUE)
                    .build();

        }

        private void cancelRequest(){
            Log.d(TAG, "cancelRequest: canceling search request");
            cancelRequest = true;
        }


    }


        private class RetrieveIncidentRunnable implements Runnable{

            private String incidentId;
            boolean cancelRequest;

            public RetrieveIncidentRunnable(String incidentId) {
                this.incidentId = incidentId;
                cancelRequest = false;
            }

            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();

                client.newCall(requestImage(incidentId)).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if(response.code() == 200)
                        {
                            //Log.d(TAG, "onResponse: success");
                            String myResponse = response.body().string();
                            JSONObject Jobject = null;
                            try {
                                Jobject = new JSONObject(myResponse);
                                JSONArray Jarray = Jobject.getJSONArray("files");
                                //Log.d(TAG, "onResponse: JARRAY: " +Jarray);

                                List<Incidents> list = new ArrayList<>();
                                //Incidents incidents2  = new Incidents();
                                for (int i = 0; i < Jarray.length(); i++) {
                                    Incidents incidents2  = new Incidents();// <-- fresh instance!
                                    JSONObject idObject = Jarray.getJSONObject(i);
                                    String encodedString  = idObject.getString("file_small");
                                    String pureBase64Encoded = encodedString.substring(encodedString .indexOf(",")  + 1);
                                    //Log.d(TAG, "onResponse: id: "+pureBase64Encoded);
                                    incidents2.setEncodedString(pureBase64Encoded);
                                    //list.add(incidents2);
                                    incident.postValue(incidents2);

                                }

                                //incident.postValue(list);



                            } catch (JSONException e) {
                                e.printStackTrace();
                                incident.postValue(null);
                            }

                        }else
                        {
                            Log.e(TAG, "onResponse: error"+ response.body().string() );
                            incident.postValue(null);
                        }

                    }
                });
                if(cancelRequest){
                    return;
                }

            }

            private Request requestImage(String incidentId){
                String id = "5d838b71b0a9700a604ceeab";
                String url = "https://app.haystax.com/server/api/links?from="+incidentId+"&type=attached_files";

                return new Request.Builder()
                        .url(url)
                        .addHeader("Content-Type","application/json")
                        .addHeader("Cookie",COOKIE_VALUE)
                        .build();

            }


            private void cancelRequest(){
                Log.d(TAG, "cancelRequest: canceling search request");
                cancelRequest = true;
            }

        }


    public void cancelRequest(){
        Log.d(TAG, "cancelRequest: canceling search request");
        //cancelRequest = true;
        if(retrieveIncidentsRunnable != null)
        {
            retrieveIncidentsRunnable.cancelRequest();
        }
        if(retrieveIncidentRunnable != null)
        {
            retrieveIncidentRunnable.cancelRequest();
        }

    }






}
