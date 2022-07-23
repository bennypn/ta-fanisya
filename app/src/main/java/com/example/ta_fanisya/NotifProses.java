package com.example.ta_fanisya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotifProses extends AppCompatActivity {

    private Button bHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif_proses);

        bHome = findViewById(R.id.btnHome);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("transaksi");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer status = snapshot.child("orderStatus").getValue(Integer.class);
                Integer orderStatusCap = snapshot.child("cappucino").child("orderStatus").getValue(Integer.class);
                Integer orderStatusKop = snapshot.child("kopisusu").child("orderStatus").getValue(Integer.class);
                Integer orderStatusCok = snapshot.child("coklat").child("orderStatus").getValue(Integer.class);
                if(status == 0){
                    Intent i = new Intent(NotifProses.this, Order.class);
                    startActivity(i);
                } else if (orderStatusCap == 2 || orderStatusKop == 2 || orderStatusCok == 2){
                    Intent i = new Intent(NotifProses.this, WaitScreen.class);
                    startActivity(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(NotifProses.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
}