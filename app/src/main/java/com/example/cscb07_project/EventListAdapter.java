package com.example.cscb07_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {

    Context context;
    ArrayList<Event> list;
    String user_name;
    String venue_name;
    public static boolean joined= false;
    public EventListAdapter(Context context, ArrayList<Event> list, String user_name, String venue_name) {
        this.context = context;
        this.list = list;
        this.user_name = user_name;
        this.venue_name = venue_name;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.event_card,parent,false);
        return  new ViewHolder(v);
    }

    private void setCreatorName(ViewHolder holder, Event event){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(event.getCreatorID()).child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        holder.creator.setText(String.valueOf(dataSnapshot.getValue()));
                        return;
                    }
                }
                holder.creator.setText("Not specified");
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = list.get(position);
        holder.event_name.setText(event.getEventName());
        setCreatorName(holder, event);
        holder.date.setText(event.getDate().replace('_', ' '));
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

        TextView event_name, creator, date, start_time, end_time, cur_players, max_players;
        Button join_event;
        Event event;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            event_name = itemView.findViewById(R.id.event_name);
            creator = itemView.findViewById(R.id.creator);
            date = itemView.findViewById(R.id.date);
            start_time = itemView.findViewById(R.id.start_time);
            end_time = itemView.findViewById(R.id.end_time);
            cur_players = itemView.findViewById(R.id.cur_players);
            max_players = itemView.findViewById(R.id.max_players);
            join_event = itemView.findViewById(R.id.join_event);


        }
    }

    private void joinEvent(Event event){
        if (event.getNumPlayers() < event.getMaxPlayers()) {
            // add event to events joined under the user
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
            databaseReference.child(user_name).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    User user = new User(task);
                    if (user.getEventsJoined().containsKey(event.getEventID().trim())){ // check if joined event
                        Toast.makeText(context, "You have already joined this event", Toast.LENGTH_SHORT).show();
                        return;
                    }else{ // if not add event to enrolled list
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                        databaseReference.child(user_name).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                User user = new User(task);
                                user.getEventsJoined().put(event.getEventID(), event.getEventID()); // add event to events joined of the user
                                DatabaseReference df = FirebaseDatabase.getInstance().getReference("Users").child(user_name).child("eventsJoined");
                                df.updateChildren(user.getEventsJoined());
                            }
                        });

                        // change current player count + 1
                        HashMap eventData = new HashMap();
                        eventData.put("numPlayers", event.getNumPlayers() + 1); // update number of current players
                        databaseReference = FirebaseDatabase.getInstance().getReference("Venues")
                                .child(venue_name).child("eventList").child(event.getEventID().trim());
                        databaseReference.updateChildren(eventData).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(context, "Successfully joined event", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        // add player to the enrolled list
                        event.getEnrolledPlayers().add(user_name);
                        databaseReference.child("enrolledPlayers").setValue(event.getEnrolledPlayers());
                    }

                    }
            });
        }
        else{
            Toast.makeText(context, "The current event is full", Toast.LENGTH_SHORT).show();
        }
    }

}
