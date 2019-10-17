package com.example.haystax.requests.responses;

import com.example.haystax.models.Incidents;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IncidentsSearchResponse
{
    @SerializedName("count")
    @Expose()
    private int count;

    @SerializedName("incidents")
    @Expose()
    private List<Incidents> incidents;

    public int getCount() {
        return count;
    }

    public List<Incidents> getIncidents()
    {
        return incidents;
    }

    @Override
    public String toString() {
        return "RecipeSearchResponse{" +
                "count=" + count +
                ", recipes=" + incidents +
                '}';
    }
}
