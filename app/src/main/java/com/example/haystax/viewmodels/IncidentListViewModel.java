package com.example.haystax.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.haystax.models.Incidents;
import com.example.haystax.repositories.IncidentRepository;

import java.util.List;

//retrieving,holding and displaying the incident values needed
//returns data from the repository
public class IncidentListViewModel extends ViewModel {

    private IncidentRepository mIncidentRepository;
    private boolean mIsViewingIncidents;
    private boolean mIsPerformingQuery;

    public IncidentListViewModel() {
        mIncidentRepository = IncidentRepository.getInstance();
        mIsPerformingQuery = false;

    }

    public LiveData<List<Incidents>> getIncidents(){
        return mIncidentRepository.getIncidents();
    }

    public void searchIncidentApi(){
        mIsViewingIncidents = true;
        mIsPerformingQuery = true;
        mIncidentRepository.searchIncidentApi();

    }

    public boolean isViewingIncidents(){
        return mIsViewingIncidents;
    }

    public void setIsViewingIncidents(boolean isViewingIncidents){
        mIsViewingIncidents = isViewingIncidents;

    }

    public void setIsPerformingQuery(Boolean isPerformingQuery){
        mIsPerformingQuery = isPerformingQuery;
    }

    public boolean isPerformingQuery(){
        return mIsPerformingQuery;
    }

    public boolean isBackPressed(){
        if(mIsPerformingQuery){
            //cancel query
            mIncidentRepository.cancelRequest();
            mIsPerformingQuery = false;
        }
        if(mIsViewingIncidents){
            mIsViewingIncidents = false;
            return false;
        }
        return true;
    }
}
