package com.example.haystax.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.haystax.R;
import com.example.haystax.models.Incidents;
import java.util.ArrayList;
import java.util.List;

public class IncidentRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int INCIDENT_TYPE = 1;
    private static final int DETAILS_TYPE = 2;
    private OnIncidentListener onIncidentListener;
    private List<Incidents> mIncidents;

    public IncidentRecyclerAdapter(OnIncidentListener onIncidentListener) {
        this.onIncidentListener = onIncidentListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = null;
        switch (viewType)
        {
            case INCIDENT_TYPE:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.incident_list_item,viewGroup,false);
                return new IncidentViewHolder(view,onIncidentListener);
            }

            case DETAILS_TYPE:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_details_list_item,viewGroup,false);
                return new DetailsViewHolder(view,onIncidentListener);

            }
            default:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.incident_list_item,viewGroup,false);
                return new IncidentViewHolder(view,onIncidentListener);
            }
        }



    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        int itemViewType = getItemViewType(position);
        if(itemViewType == INCIDENT_TYPE)
        {
            ((IncidentViewHolder)viewHolder).title.setText(mIncidents.get(position).getTitle());
            ((IncidentViewHolder)viewHolder).date.setText(mIncidents.get(position).getDate());

        }

    }

    @Override
    public int getItemViewType(int position) {
        return INCIDENT_TYPE;
    }

    public void displayDetails()
    {
        List<Incidents> details = new ArrayList<>();
        /*for(int i = 0; i < Constants.DEFAULT_SEARCH_CATEGORIES.length;i++)
        {
            Incidents incidents1 = new Incidents();
            incidents1.setTitle(Constants.DEFAULT_SEARCH_CATEGORIES[i]);
            incidents1.setEncodedString(Constants.DEFAULT_SEARCH_CATEGORIES[i]);
            //need a marker to show details viewholder. maybe boolean
            details.add(incidents1);
        }*/

        mIncidents = details;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mIncidents != null)
        {
            return mIncidents.size();
        }
        return 0;
    }

    public void setIncidents(List<Incidents> incidents){
        mIncidents = incidents;
        notifyDataSetChanged();

    }

    public Incidents getSelectedIncident(int position){
        if(mIncidents != null){
            if(mIncidents.size() > 0){
                return mIncidents.get(position);
            }
        }

        return null;
    }
}
