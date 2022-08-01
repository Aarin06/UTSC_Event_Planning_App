package com.example.cscb07_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {

    Context context;
    ArrayList<Event> list;


    public EventListAdapter(Context context, ArrayList<Event> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.event_card,parent,false);
        return  new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = list.get(position);
        holder.event_name.setText(event.getEventName());
        holder.creator.setText(event.getCreatorID());
        holder.start_time.setText(event.getStartTime());
        holder.end_time.setText(event.getEndTime());
        holder.cur_players.setText(Long.toString(event.getNumPlayers()));
        holder.max_players.setText(Long.toString(event.getMaxPlayers()));
        holder.event = event;

        holder.join_event.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                System.out.println(event.eventID);
                joinEvent(event);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView event_name, creator, start_time, end_time, cur_players, max_players;
        Button join_event;
        Event event;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            event_name = itemView.findViewById(R.id.event_name);
            creator = itemView.findViewById(R.id.creator);
            start_time = itemView.findViewById(R.id.start_time);
            end_time = itemView.findViewById(R.id.end_time);
            cur_players = itemView.findViewById(R.id.cur_players);
            max_players = itemView.findViewById(R.id.max_players);
            join_event = itemView.findViewById(R.id.join_event);


        }
    }

    private void joinEvent(Event event){

        if (event.getNumPlayers() < event.getMaxPlayers()) {
            HashMap eventData = new HashMap();
            System.out.println("YES");
            eventData.put("numPlayers", event.getNumPlayers() + 1); // update number of current players
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Venue");
            databaseReference.child("Pan am").child(event.getEventID().trim()).updateChildren(eventData).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()){
                        Toast.makeText(context, "Successfully joined event", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        System.out.println("Something went wrong");
                    }
                }
            });
            String key = "userID" + Long.toString(event.getNumPlayers()+1); // update newly added player and id
            event.getEnrolledPlayers().put(key, "qJpHJLm3vvaI6qaSynYOHj4clus2");
            databaseReference.child("Pan am").child(event.getEventID().trim()).child("enrolledPlayers").updateChildren(event.getEnrolledPlayers());
        }
        else{
            Toast.makeText(context, "The current event is full", Toast.LENGTH_SHORT).show();
        }
    }

}
