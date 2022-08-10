package com.example.cscb07_project;

import android.content.Context;
import android.graphics.Color;
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
    public EventsRecyclerAdapter(Context context, ArrayList<Event> events) {
        this.context = context;
        this.events = events;
    }
    // Overrides.
    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new EventsViewHolder(v);
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
        holder.venueString.setText(e.venueName);
        holder.startTime.setText(e.getStartTime());
        holder.endTime.setText(e.getEndTime());
        holder.dateView.setText(e.getDate().replace('_', ' '));
        // Setting the eventID.
        holder.eventID = e.getEventID();
        // Changing the Button text.
        if (e.isEventApproved()) {
            holder.statusView.setText("Approved");
            holder.statusView.setBackgroundColor(Color.parseColor("#FF31C127"));
        }
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class EventsViewHolder extends RecyclerView.ViewHolder {
        TextView sportName, spotsText, venueString, startTime, endTime, statusView, dateView;
        String eventID;
        String venueName;
        public EventsViewHolder(@NonNull View itemView) {
            super(itemView);
            // Getting the references.
            sportName = itemView.findViewById(R.id.sport_name_view);
            spotsText = itemView.findViewById(R.id.spots_text);
            venueString = itemView.findViewById(R.id.venue_name_text);
            startTime = itemView.findViewById(R.id.start_time_text);
            endTime = itemView.findViewById(R.id.end_time_text);
            statusView = itemView.findViewById(R.id.status_event_button);
            dateView = itemView.findViewById(R.id.event_date_text);
        }
    }
}
