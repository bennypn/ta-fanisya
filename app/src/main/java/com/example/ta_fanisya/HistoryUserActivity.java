package com.example.ta_fanisya;

import static com.example.ta_fanisya.LoginActivity.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ta_fanisya.adapter.historyUserAdapter;
import com.example.ta_fanisya.model.History;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HistoryUserActivity extends AppCompatActivity {

    private Button btnBack;
    private RecyclerView recyclerView;
    private historyUserAdapter adapter; // Create Object of the Adapter class
    private DatabaseReference mbase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_user);

        // Create a instance of the database and get
        // its reference
        mbase = FirebaseDatabase.getInstance().getReference("user").child(user).child("history");

        recyclerView = findViewById(R.id.historyUser_rc);
        btnBack = findViewById(R.id.hisUser_back_btn);
        // To display the Recycler view linearly

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<History> options
                = new FirebaseRecyclerOptions.Builder<History>()
                .setQuery(mbase, History.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new historyUserAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        recyclerView.setAdapter(adapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HistoryUserActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }

}