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

public class ApproveEventListAdapter extends RecyclerView.Adapter<ApproveEventListAdapter.ViewHolder> {

    Context context;
    ArrayList<Event> list;
    private DatabaseReference fire;
    private String venueName;


    public ApproveEventListAdapter(Context context, ArrayList<Event> list, String venueName) {
        this.context = context;
        this.list = list;
        this.venueName = venueName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.event_approval,parent,false);
        return  new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = list.get(position);
        holder.event_name.setText(event.getEventName());
        holder.start_time.setText(event.getStartTime());
        holder.end_time.setText(event.getEndTime());
        holder.event = event;

        holder.accept.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                acceptEvent(holder.event);
            }
        });

        holder.deny.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                denyEvent(holder.event);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView event_name, start_time, end_time;
        Button accept, deny;
        Event event;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            event_name = itemView.findViewById(R.id.event_name);
            start_time = itemView.findViewById(R.id.start_time);
            end_time = itemView.findViewById(R.id.end_time);
            accept = itemView.findViewById(R.id.accept);
            deny = itemView.findViewById(R.id.deny);


        }
    }

    private void acceptEvent(Event event){
        fire = FirebaseDatabase.getInstance().getReference();
        fire.child("Venues").child(venueName).child("eventList").child(event.getEventID()).child("eventApproved").setValue(true);
        list.remove(event);
        notifyDataSetChanged();
        Toast.makeText(context, "Event Approved", Toast.LENGTH_SHORT).show();
    }
    private void denyEvent(Event event){
        fire = FirebaseDatabase.getInstance().getReference();
        fire.child("Venues").child(venueName).child("eventList").child(event.getEventID()).removeValue();
        list.remove(event);
        notifyDataSetChanged();
        Toast.makeText(context, "Event Denied", Toast.LENGTH_SHORT).show();
    }

}
