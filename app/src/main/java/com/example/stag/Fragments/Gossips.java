package com.example.stag.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stag.Adapters.UserAdapter;
import com.example.stag.Models.Users;
import com.example.stag.R;
import com.example.stag.databinding.FragmentGossipsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Gossips extends Fragment {

    FragmentGossipsBinding binding;
    FirebaseDatabase database;
    ArrayList<Users> list = new ArrayList<Users>();



    public Gossips() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGossipsBinding.inflate(inflater, container, false);

        database = FirebaseDatabase.getInstance();
        UserAdapter adapter = new UserAdapter(list,getContext());
        binding.chatrecyclerview.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.chatrecyclerview.setLayoutManager(linearLayoutManager);



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

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


            return binding.getRoot();
    }
}