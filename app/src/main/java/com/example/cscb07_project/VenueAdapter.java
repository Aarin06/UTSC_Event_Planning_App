package com.example.cscb07_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

//Adapter binds data to layout; adapter retrieves data from something like database query and
//converts each entry into a view that can be added to the layout
public class VenueAdapter extends RecyclerView.Adapter<VenueAdapter.Viewholder> {
    private Context context;
    private ArrayList<Venue> venuesList;
    String venueID; //string/int according to our structure document
    String creatorID;
    String name;
    String location;
    List<String> sportsOffered;
    String openTime;

    List<Event> eventsList;

    @NonNull
    @Override
    //Called when RecyclerView needs a new ViewHolder to represent an item
    //Can create manual view or inflate it from an XML file. //Reduces code essentially
    public VenueAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_regard_and_add_venues_admin, parent, false);
        return new Viewholder(view);
        //a Viewholder describes a view and metadata about its place within the REcyclerView
        //https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.ViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull VenueAdapter.Viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
