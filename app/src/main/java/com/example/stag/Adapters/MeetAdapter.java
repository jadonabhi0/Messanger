package com.example.stag.Adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stag.Models.Users;
import com.example.stag.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MeetAdapter extends RecyclerView.Adapter<MeetAdapter.viewHolder> {
    ArrayList<Users> list;
    Context context;

    public MeetAdapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.meetlayout,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        Users users = list.get(position);
        Picasso.get().load(users.getProfilepic()).placeholder(R.drawable.usericon).into(holder.userimage);
        holder.username.setText(users.getUsername());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{


        ImageView userimage,phonecall,videocall;
        TextView username;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            userimage = itemView.findViewById(R.id.profileimagecall);
//            phonecall = itemView.findViewById(R.id.audiocall);
//            videocall = itemView.findViewById(R.id.videocall);
            username = itemView.findViewById(R.id.usernamelistcall);




        }
    }

}
