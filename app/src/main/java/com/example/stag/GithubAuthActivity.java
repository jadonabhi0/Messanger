package com.example.stag;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.stag.Models.Users;
import com.example.stag.databinding.ActivityGithubAuthBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class GithubAuthActivity extends AppCompatActivity {

    ActivityGithubAuthBinding binding;
    FirebaseDatabase database;
    FirebaseUser muser;
    FirebaseAuth auth;
    String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_auth);

        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        binding = ActivityGithubAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.gowithgithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.githubemail.getText().toString();
                if (!email.matches(regex)){
                    Toast.makeText(GithubAuthActivity.this, "Enter valid email", Toast.LENGTH_SHORT).show();
                }
                else{

                    OAuthProvider.Builder provider = OAuthProvider.newBuilder("github.com");
                    // Target specific email with login hint.
                    provider.addCustomParameter("login", email);


                    // Request read access to a user's email addresses.
                    // This must be preconfigured in the app's API permissions.
                    List<String> scopes =
                            new ArrayList<String>() {
                                {
                                    add("user:email");
                                }
                            };
                    provider.setScopes(scopes);




                    Task<AuthResult> pendingResultTask = auth.getPendingAuthResult();
                    if (pendingResultTask != null) {
                        // There's something already here! Finish the sign-in for your user.
                        pendingResultTask
                                .addOnSuccessListener(
                                        new OnSuccessListener<AuthResult>() {
                                            @Override
                                            public void onSuccess(AuthResult authResult) {
                                                // User is signed in.
                                                // IdP data available in
                                                // authResult.getAdditionalUserInfo().getProfile().
                                                // The OAuth access token can also be retrieved:
                                                // authResult.getCredential().getAccessToken().

                                            }
                                        })
                                .addOnFailureListener(
                                        new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(GithubAuthActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                    }
                    else {
                        // There's no pending result so you need to start the sign-in flow.
                        // See below.


                        auth
                                .startActivityForSignInWithProvider(GithubAuthActivity.this, provider.build())
                                .addOnSuccessListener(
                                        new OnSuccessListener<AuthResult>() {
                                            @Override
                                            public void onSuccess(AuthResult authResult) {
                                                FirebaseUser user = auth.getCurrentUser();
                                                Users us = new Users();
                                                us.setProfilepic(user.getPhotoUrl().toString());
                                                us.setUserid(user.getUid());
                                                us.setUsername(user.getDisplayName());

                                                database.getReference().child("User").child(user.getUid()).setValue(us);

                                                openNextActivity();
                                            }
                                        })
                                .addOnFailureListener(
                                        new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(GithubAuthActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });


                    }


                }
            }
        });
    }

    private void openNextActivity() {

        Intent intent = new Intent(GithubAuthActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }
}