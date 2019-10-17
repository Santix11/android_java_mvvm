package com.example.haystax.adapters;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.haystax.R;


public class DetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    OnIncidentListener onIncidentListener;
    TextView title,date,summary;

    public DetailsViewHolder(@NonNull View itemView, OnIncidentListener onIncidentListener) {
        super(itemView);
        this.onIncidentListener = onIncidentListener;
        title = itemView.findViewById(R.id.details_title);
        date = itemView.findViewById(R.id.details_date);
        summary = itemView.findViewById(R.id.details_summary);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onIncidentListener.onIncidentDetailsClick(title.getText().toString());

    }
}
