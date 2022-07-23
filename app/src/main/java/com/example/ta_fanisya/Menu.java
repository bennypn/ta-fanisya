package com.example.ta_fanisya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Menu extends AppCompatActivity {

    private ImageView addCoklat, addCappucino, addKopisusu, minCoklat, minCappucino, minKopisusu;
    private TextView coklatTV, cappucinoTV, kopisusuTV;
    private Button back, next;
    protected static Integer totalOrder, totalPrice, coklatCnt, cappucinoCnt, kopisusuCnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        back = findViewById(R.id.btnBack);
        next = findViewById(R.id.btnNext);
        addCappucino = findViewById(R.id.cappAdd_btn);
        addCoklat = findViewById(R.id.coklatAdd_btn);
        addKopisusu = findViewById(R.id.kopisusuAdd_btn);
        minCappucino = findViewById(R.id.cappMin_btn);
        minCoklat = findViewById(R.id.coklatMin_btn);
        minKopisusu = findViewById(R.id.kopisusuMin_btn);
        coklatTV = findViewById(R.id.coklat_tv);
        cappucinoTV = findViewById(R.id.cappucino_tv);
        kopisusuTV = findViewById(R.id.kopisusu_tv);

        coklatCnt = 0;
        cappucinoCnt = 0;
        kopisusuCnt = 0;

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(Menu.this, MainActivity.class);
                startActivity(i);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("transaksi").child("orderStatus");
                myRef.setValue(1);
                totalOrder = coklatCnt + cappucinoCnt + kopisusuCnt;
                totalPrice = totalOrder * 30000;

                if(coklatCnt > 0){
                    myRef = database.getReference("transaksi").child("coklat").child("orderStatus");
                    myRef.setValue(1);
                    myRef = database.getReference("transaksi").child("coklat").child("orderCount");
                    myRef.setValue(coklatCnt);
                }
                if(cappucinoCnt > 0){
                    myRef = database.getReference("transaksi").child("cappucino").child("orderStatus");
                    myRef.setValue(1);
                    myRef = database.getReference("transaksi").child("cappucino").child("orderCount");
                    myRef.setValue(cappucinoCnt);
                }
                if(kopisusuCnt > 0){
                    myRef = database.getReference("transaksi").child("kopisusu").child("orderStatus");
                    myRef.setValue(1);
                    myRef = database.getReference("transaksi").child("kopisusu").child("orderCount");
                    myRef.setValue(kopisusuCnt);
                }

                Intent i = new Intent(Menu.this, Recap.class);
                startActivity(i);
            }
        });

        addCoklat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coklatCnt += 1;
                coklatTV.setText(coklatCnt.toString());
            }
        });

        addCappucino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cappucinoCnt += 1;
                cappucinoTV.setText(cappucinoCnt.toString());
            }
        });

        addKopisusu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                kopisusuCnt += 1;
                kopisusuTV.setText(kopisusuCnt.toString());
            }
        });

        minCoklat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coklatCnt -= 1;
                if(coklatCnt < 0){
                    Toast.makeText(Menu.this, "gabisa minus hehe, nice try btw", Toast.LENGTH_LONG).show();
                    coklatCnt = 0;
                }
                coklatTV.setText(coklatCnt.toString());
            }
        });

        minCappucino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cappucinoCnt -= 1;
                if(cappucinoCnt < 0){
                    Toast.makeText(Menu.this, "gabisa minus hehe, nice try btw", Toast.LENGTH_LONG).show();
                    cappucinoCnt = 0;
                }
                cappucinoTV.setText(cappucinoCnt.toString());
            }
        });

        minKopisusu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kopisusuCnt -= 1;
                if(kopisusuCnt < 0){
                    Toast.makeText(Menu.this, "gabisa minus hehe, nice try btw", Toast.LENGTH_LONG).show();
                    kopisusuCnt = 0;
                }
                kopisusuTV.setText(kopisusuCnt.toString());
            }
        });
    }
}