package com.example.android.firebaseintro;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import static android.os.Build.VERSION_CODES.M;

public class HomeActivity extends AppCompatActivity {

    private Button logoutBut;
    private Button newPostBut;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null)
                {
                    startActivity(new Intent(HomeActivity.this, MainActivity.class));
                }
            }
        };

        newPostBut = (Button) findViewById(R.id.newEntry);
        logoutBut = (Button) findViewById(R.id.logBut);

        logoutBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();

            }
        });

        newPostBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, PostActivity.class));
            }
        });
    }

    public void viewJournal(View view)
    {
        startActivity(new Intent(HomeActivity.this, JournalView.class));
        finish();
    }

    public void newPost(View v)
    {
        startActivity(new Intent(HomeActivity.this, PostActivity.class));
        finish();

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(mAuthListener !=null)
        {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
