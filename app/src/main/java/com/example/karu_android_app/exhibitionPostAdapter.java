package com.example.karu_android_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class exhibitionPostAdapter extends FirestoreRecyclerAdapter<exhibitionPostInfo,exhibitionPostAdapter.exhibitonHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public exhibitionPostAdapter(@NonNull FirestoreRecyclerOptions<exhibitionPostInfo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull exhibitonHolder holder, int position, @NonNull exhibitionPostInfo model) {
        holder.event_name.setText(model.getEventName());
        holder.event_date_place.setText(model.getEventDate());
        holder.event_price.setText(String.valueOf(model.getTktPrice()));
    }
    @NonNull
    @Override
    public exhibitonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_sample_exhibition,parent,false);
        return new exhibitonHolder(v);
    }

    class exhibitonHolder extends RecyclerView.ViewHolder{
        TextView event_name;
        TextView event_date_place;
       // TextView event_place;
        TextView event_price;

        public exhibitonHolder(@NonNull View itemView) {
            super(itemView);
            event_name = itemView.findViewById(R.id.eventNameText);
            event_date_place = itemView.findViewById(R.id.eventPlaceText);
            event_price = itemView.findViewById(R.id.ticketPrice);
        }
    }
}
