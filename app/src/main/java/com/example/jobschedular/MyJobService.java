package com.example.jobschedular;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.RequiresApi;


public class MyJobService extends JobService{
    
    public static final String TAG = "My job service";
    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob: Job started.");
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    if(jobCancelled){
                        return;
                    }
                    Log.d(TAG,"The value in service is " + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG,"Job finished");
                jobFinished(params,false);
            }
        }).start();
        return true;
    }
    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job cancelled before completion");
        //We are responsible for cancelling the job ourselves
        jobCancelled = true;
        return false;
    }
}
