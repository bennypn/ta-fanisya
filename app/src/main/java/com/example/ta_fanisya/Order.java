package com.example.ta_fanisya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Order extends AppCompatActivity {

    private Button bNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        bNext = findViewById(R.id.btnNext1);

        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(Order.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}