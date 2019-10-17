package com.example.haystax;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

//execute runnable tasks
public class AppExecutors {

    private static AppExecutors instance;

    public static AppExecutors getInstance(){
        if(instance == null){
            instance = new AppExecutors();

        }
        return instance;
    }

    //scheduled commands to run after a given delay
    private final ScheduledExecutorService mNetworkIO = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService networkIO(){
        return mNetworkIO;
    }



}
