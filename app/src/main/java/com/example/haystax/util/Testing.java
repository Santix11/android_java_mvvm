package com.example.haystax.util;

import android.util.Log;

import com.example.haystax.models.Incidents;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class Testing {

    public static void printIncidents(List<Incidents> list, String tag){
        try {
            //Log.d(tag, "printIncidents: list" +list.toString());
            /*JSONArray Jarray = null;
            for (Incidents value : list) {
                //JSONObject Jobject = new JSONObject(Arrays.toString(value.getOverview()));
                Jarray = new JSONArray(value.getOverview());
            }

            Log.d(tag, "printIncidents: array: " + Jarray);


            String x = Jarray.toString().replace("\\","");
            String s1 = x.substring(x.indexOf("[")+2);
            s1.trim();
            String s2 = s1.substring(0,s1.length() -2);
            s2.trim();
            String s3 = "[" +s2 + "]";
            String s4 = s3.replace("}\",\"{","},{");
            JSONArray jsonArray = new JSONArray(s4);

            Log.d(tag, "printIncidents: trim: " + jsonArray);

            String title;
            String date;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject row = jsonArray.getJSONObject(i);
                title = row.getString("title");
                date = row.getString("date");
                Log.d(tag, "printIncidents: x: "+ title + " date: " + date);
            }*/


        } /*catch (JSONException e) {
            e.printStackTrace();
        }*/
        catch (Exception e)
        {

        }

    }
}
