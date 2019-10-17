package com.example.haystax;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.haystax.models.Incidents;
import com.example.haystax.viewmodels.IncidentViewModel;

public class IncidentActivity extends BaseActivity
{
    private static final String TAG = "IncidentActivity";
    private ImageView detailsImage;
    private TextView title,date,summary;
    private ScrollView scrollView;

    private IncidentViewModel incidentViewModel;

    private String titleIntent;
    private String dateIntent;
    private String summaryIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_details_list_item);
        detailsImage = findViewById(R.id.detail_image);
        title = findViewById(R.id.details_title);
        date = findViewById(R.id.details_date);
        summary = findViewById(R.id.details_summary);

        //instantiate our view model
        incidentViewModel = ViewModelProviders.of(this).get(IncidentViewModel.class);
        subscribeObservers();
        getIncomingIntent();
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("incident")){
            Incidents incident = getIntent().getParcelableExtra("incident");
            Log.d("test", "getIncomingIntent: " + incident.getTitle() + incident.getDate() + incident.getSummary());
            titleIntent = incident.getTitle();
            dateIntent = incident.getDate();
            summaryIntent = incident.getSummary();
            incidentViewModel.searchIncidentById(incident.getId());
        }
    }

    private void subscribeObservers(){
        incidentViewModel.getIncident().observe(this, new Observer<Incidents>() {
            @Override
            public void onChanged(Incidents incidents) {
                if(incidents != null){
                    Log.d(TAG, "onChanged: " + incidents.getTitle());
                    setIncidentProperties(incidents);

                }
            }
        });
    }

    private void setIncidentProperties(Incidents incident){
        if(incident != null){
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);

            byte[] decodedBytes = Base64.decode(incident.getEncodedString(), Base64.DEFAULT);
            Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .load(decodedBytes)
                    .into(detailsImage);

            title.setText(titleIntent);
            date.setText(dateIntent);
            summary.setText(summaryIntent);
        }
    }
}

