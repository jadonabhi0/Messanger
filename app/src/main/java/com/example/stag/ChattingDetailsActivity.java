package com.example.stag;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.stag.Adapters.Chatdapter;
import com.example.stag.Models.Messages;
import com.example.stag.databinding.ActivityChattingDetailsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ChattingDetailsActivity extends AppCompatActivity {

    FirebaseDatabase database;
    FirebaseAuth auth;
    ActivityChattingDetailsBinding binding;
    FirebaseStorage storage;
    ProgressDialog dialog;

      String senderRooms;
      String reciverRooms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_details);
        binding = ActivityChattingDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();


        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("uploading image");


        Intent intent = getIntent();
        final String senderid = auth.getUid();
        String reciveid = intent.getStringExtra("userId");
        String username = intent.getStringExtra("username");
        String userprofile  = intent.getStringExtra("userprofile");



        binding.usernamechattingdetails.setText(username);
        Picasso.get().load(userprofile).placeholder(R.drawable.usericon).into(binding.profilepic);

        binding.backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChattingDetailsActivity.this,MainActivity.class));
            }
        });


        final ArrayList<Messages> messagemodel = new ArrayList<>();
        final Chatdapter chatdapter = new Chatdapter(messagemodel,this,reciveid);
        binding.chatrecyclerview.setAdapter(chatdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.chatrecyclerview.setLayoutManager(linearLayoutManager);




          senderRooms = senderid+reciveid;
          reciverRooms = reciveid+senderid;



        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg =  binding.etmessage.getText().toString();
                final Messages model = new Messages(senderid,msg);
                model.setTime(new Date().getTime());
                binding.etmessage.setText("");





                database.getReference().child("Chats").child(senderRooms).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        database.getReference().child("Chats").child(reciverRooms).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
                    }
                });







            }
        });


        database.getReference().child("Chats").child(senderRooms).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagemodel.clear();
                for (DataSnapshot snapshot1: snapshot.getChildren()
                ) {
                    Messages model = snapshot1.getValue(Messages.class);
                    model.setMessageid(snapshot1.getKey());
                    messagemodel.add(model);
                }
                chatdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        // image send

        binding.imageattachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                intent1.setType("image/*");
                startActivityForResult(intent1,25);
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Calendar calendar = Calendar.getInstance();
        Messages model = new Messages();
        if (requestCode==25){
            if (data!= null){
                if (data.getData() != null){
                    Uri selectedimage = data.getData();
                    StorageReference reference = storage.getReference().child("chats").child(calendar.getTimeInMillis()+"");
                    dialog.show();
                    reference.putFile(selectedimage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            dialog.dismiss();
                            if (task.isSuccessful()){
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String  filepath = uri.toString();
                                        model.setImageurl(filepath);
                                        model.setMessage("photo");
//                                        model.setuId;

//                                        database.getReference().child("Chats").child(senderRooms).push().setValue(model);
//                                        database.getReference().child("Chats").child(reciverRooms).push().setValue(model) ;

                                        Toast.makeText(ChattingDetailsActivity.this, filepath, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        }


    }



}