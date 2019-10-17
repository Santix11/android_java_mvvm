package com.example.haystax.requests.responses;

import com.example.haystax.models.Incidents;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IncidentResponse {

    @SerializedName("incidents")
    @Expose()
    private List<Incidents> incidents;

    public List<Incidents> getIncidents() {
        return incidents;
    }

    @Override
    public String toString() {
        return "IncidentResponse{" +
                "incidents=" + incidents +
                '}';
    }
}
