package com.example.cscb07_project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class venueAdapter extends RecyclerView.Adapter<venueAdapter.MyViewHolder> {
    public static final String VENUE = "com.example.cscb07_project.VENUE_ID";
    Context context;
    ArrayList<Venue> list;
    OnNoteListener onNoteListener;
    boolean createEventButton;


    public venueAdapter(Context context, ArrayList<Venue> list, OnNoteListener onNoteListener, boolean createEventButton) {
        this.context = context;
        this.list = list;
        this.onNoteListener = onNoteListener;
        this.createEventButton = createEventButton;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(context).inflate(R.layout.venueitem,parent,false);
        return new MyViewHolder(v,onNoteListener);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Venue venue = list.get(position);
        holder.venueName.setText(venue.getName());
        holder.venueLocation.setText(venue.getLocation());
        holder.sportsList.setText(venue.StringSportsOffered());
        holder.venue = venue;

        if (!createEventButton){
            holder.create_new_event.setClickable(false);
            holder.create_new_event.setAlpha(0f);
        }
        else {
            holder.create_new_event.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("hi");
                    Intent intent = new Intent(context, CreateNewEvent.class);
                    intent.putExtra(VENUE, venue.venueID);
                    context.startActivity(intent);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView venueName,venueLocation,sportsList;
        OnNoteListener onNoteListener;
        Button create_new_event;
        Venue venue;

        public MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            venueName = itemView.findViewById(R.id.tVenueName);
            venueLocation = itemView.findViewById(R.id.tVenueLocation);
            sportsList = itemView.findViewById(R.id.tsportsList);
            create_new_event = itemView.findViewById(R.id.create_new_event);
            itemView.setOnClickListener(this);
            this.onNoteListener = onNoteListener;

        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());

        }

    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }

}