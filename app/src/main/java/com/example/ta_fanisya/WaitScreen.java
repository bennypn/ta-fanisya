package com.example.ta_fanisya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WaitScreen extends AppCompatActivity {
    private Button nextScrn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_screen);

        nextScrn = findViewById(R.id.wsNext_btn);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("transaksi");
        nextScrn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child("cappucino").child("orderStatus").setValue(1);
                myRef.child("kopisusu").child("orderStatus").setValue(1);
                myRef.child("coklat").child("orderStatus").setValue(1);
                Intent i = new Intent(WaitScreen.this,NotifProses.class);
                startActivity(i);
            }
        });
    }
}