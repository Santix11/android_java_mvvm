package com.example.haystax.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.haystax.models.Incidents;
import com.example.haystax.repositories.IncidentRepository;

public class IncidentViewModel extends ViewModel
{
    private IncidentRepository incidentRepository;
    private String mIncidentId;

    public IncidentViewModel() {
        incidentRepository = IncidentRepository.getInstance();
    }

    public LiveData<Incidents> getIncident(){
        return incidentRepository.getIncident();
    }

    public void searchIncidentById(String incidentId){
        mIncidentId = incidentId;
        incidentRepository.searchIncidentById(incidentId);

    }

    public String getIncidentId() {
        return mIncidentId;
    }
}
