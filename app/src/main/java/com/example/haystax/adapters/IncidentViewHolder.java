package com.example.haystax.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haystax.R;

public class IncidentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView title,date;
    OnIncidentListener onIncidentListener;


    public IncidentViewHolder(@NonNull View itemView, OnIncidentListener onIncidentListener) {
        super(itemView);
        this.onIncidentListener = onIncidentListener;
        title = itemView.findViewById(R.id.incident_title);
        date = itemView.findViewById(R.id.incident_date);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onIncidentListener.OnIncidentClick(getAdapterPosition());

    }
}
