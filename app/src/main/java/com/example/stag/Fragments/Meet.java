package com.example.stag.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stag.Adapters.MeetAdapter;
import com.example.stag.Models.Users;
import com.example.stag.R;
import com.example.stag.databinding.FragmentMeetBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Meet extends Fragment {

    FirebaseDatabase database;
    FragmentMeetBinding binding;
    ArrayList<Users> list = new ArrayList<>();


    public Meet() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMeetBinding.inflate(inflater,container,false);

        database = FirebaseDatabase.getInstance();
        MeetAdapter meetAdapter = new MeetAdapter(list,getContext());
        binding.meetrecyclerview.setAdapter(meetAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.meetrecyclerview.setLayoutManager(linearLayoutManager);



        database.getReference().child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                for (DataSnapshot datasnapshots: snapshot.getChildren()) {
                    Users users = datasnapshots.getValue(Users.class);
                    users.setUserid(datasnapshots.getKey());
                    if (!users.getUserid().equals(FirebaseAuth.getInstance().getUid())){
                        list.add(users);
                    }

                }

                meetAdapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return binding.getRoot();
    }
}