package com.example.ta_fanisya;

import static com.example.ta_fanisya.LoginActivity.user;
import static com.example.ta_fanisya.LoginActivity.userName;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class MainActivity extends AppCompatActivity {

    private Button btnMenu,btnTop,btnHistory,btnLogout;
    private TextView usertv, saldoCnt ;
    protected static Integer saldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMenu = findViewById(R.id.btnQR);
        usertv = findViewById(R.id.name_tv);
        saldoCnt = findViewById(R.id.saldo_tv);
        btnTop = findViewById(R.id.topup_btn);
        btnHistory = findViewById(R.id.his_btn);
        btnLogout = findViewById(R.id.user_logout_btn);

        ScanOptions options = new ScanOptions();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user").child(user).child("name");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userName = snapshot.getValue(String.class);
                usertv.setText(userName + " !");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference totalSaldo = database.getReference("user").child(LoginActivity.user).child("saldo");

        totalSaldo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                saldo = snapshot.getValue(Integer.class);
                saldoCnt.setText(saldo.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalSaldo.setValue(100000);
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                options.setOrientationLocked(false);
                options.setPrompt("Scan a barcode");
                options.setCameraId(0);  // Use a specific camera of the device
                options.setBeepEnabled(true);
                options.setBarcodeImageEnabled(true);
                barcodeLauncher.launch(options);

            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,HistoryUserActivity.class);
                startActivity(i);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(MainActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(MainActivity.this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("transaksi").child("meja");
                    myRef.setValue(result.getContents());
                    Intent i = new Intent(MainActivity.this, Menu.class);
                    startActivity(i);
                }
            });
}