package com.example.haystax.repositories;

import androidx.lifecycle.LiveData;
import com.example.haystax.models.Incidents;
import com.example.haystax.requests.IncidentApiClient;

import java.util.List;

public class IncidentRepository
{
    //singleton pattern
    private static IncidentRepository instance;
    private IncidentApiClient mIncidentApiClient;


    public static IncidentRepository getInstance()
    {
        if(instance == null)
        {
            instance = new IncidentRepository();
        }
        return instance;
    }

    private IncidentRepository() {
        mIncidentApiClient = IncidentApiClient.getInstance();

    }

    public LiveData<List<Incidents>> getIncidents(){
        return mIncidentApiClient.getIncidents();
    }

    public LiveData<Incidents> getIncident(){
        return mIncidentApiClient.getIncident();
    }

    public void searchIncidentById(String incidentId){
        mIncidentApiClient.searchIncidentById(incidentId);

    }

    public void searchIncidentApi(){
        mIncidentApiClient.searchIncidentApi();

    }

    public void cancelRequest(){
        mIncidentApiClient.cancelRequest();
    }
}
