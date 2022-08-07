package com.example.cscb07_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventsRecyclerAdapter extends RecyclerView.Adapter<EventsRecyclerAdapter.EventsViewHolder> {
    // Useful variables.
    Context context;
    ArrayList<Event> events;
    String venueName;
    public EventsRecyclerAdapter(Context context, ArrayList<Event> events, String venueName) {
        this.context = context;
        this.events = events;
        this.venueName = venueName;
    }
    // Overrides.
    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new EventsViewHolder(v, this.venueName);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder holder, int position) {
        // Getting the current event from the list.
        Event e = events.get(position);
        // Setting the values of the text views.
        holder.sportName.setText(e.getEventName());
        // Concatenating the string and then setting the text.
        String spotsString = e.getNumPlayers() + "/" + e.getMaxPlayers();
        holder.spotsText.setText(spotsString);
        // Doing the rest.
        holder.venueString.setText(holder.venueName);
        holder.startTime.setText(e.getStartTime());
        holder.endTime.setText(e.getEndTime());
        // Setting the eventID.
        holder.eventID = e.getEventID();
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class EventsViewHolder extends RecyclerView.ViewHolder {
        TextView sportName, spotsText, venueString, startTime, endTime, leaveButton;
        String eventID;
        String venueName;
        public EventsViewHolder(@NonNull View itemView, String venueName) {
            super(itemView);
            // Getting the references.
            sportName = itemView.findViewById(R.id.sport_name_view);
            spotsText = itemView.findViewById(R.id.spots_text);
            venueString = itemView.findViewById(R.id.venue_name_text);
            startTime = itemView.findViewById(R.id.start_time_text);
            endTime = itemView.findViewById(R.id.end_time_text);
            leaveButton = itemView.findViewById(R.id.leave_event_button);
            // Setting the name of the venue.
            this.venueName = venueName;
        }
    }
}
