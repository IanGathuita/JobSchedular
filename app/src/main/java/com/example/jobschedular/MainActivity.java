package com.example.jobschedular;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnScheduleJob,btnCancelJob;
    public static final String TAG = "My main activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnScheduleJob = findViewById(R.id.btnScheduleJob);
        btnCancelJob = findViewById(R.id.btnCancelJob);

        btnScheduleJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComponentName componentName = new ComponentName(MainActivity.this,MyJobService.class);
                JobInfo.Builder builder= new JobInfo.Builder(123,componentName)
                        .setRequiresCharging(true);
                JobInfo jobInfo = builder.build();

                JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                int resultCode = jobScheduler.schedule(jobInfo);

                if(resultCode == JobScheduler.RESULT_SUCCESS){
                    Toast.makeText(MainActivity.this, "Job successfully scheduled",Toast.LENGTH_SHORT)
                            .show();
                }
                else if(resultCode == JobScheduler.RESULT_FAILURE){
                    Toast.makeText(MainActivity.this, "Sorry, couldn't schedule",Toast.LENGTH_SHORT)
                            .show();

                }

            }
        });

        btnCancelJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                jobScheduler.cancel(123);
                Log.d(TAG, "Job cancelled.");
            }
        });
    }
}