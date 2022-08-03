package com.example.cscb07_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class venueAdapter extends RecyclerView.Adapter<venueAdapter.MyViewHolder> {

    Context context;
    ArrayList<Venue> list;
    OnNoteListener onNoteListener;


    public venueAdapter(Context context, ArrayList<Venue> list, OnNoteListener onNoteListener) {
        this.context = context;
        this.list = list;
        this.onNoteListener = onNoteListener;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(context).inflate(R.layout.venueitem,parent,false);
        return new MyViewHolder(v,onNoteListener);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Venue v = list.get(position);
        holder.venueName.setText(v.getName());
        holder.venueLocation.setText(v.getLocation());
        holder.sportsList.setText(v.StringSportsOffered());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView venueName,venueLocation,sportsList;
        OnNoteListener onNoteListener;

        public MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            venueName = itemView.findViewById(R.id.tVenueName);
            venueLocation = itemView.findViewById(R.id.tVenueLocation);
            sportsList = itemView.findViewById(R.id.tsportsList);
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