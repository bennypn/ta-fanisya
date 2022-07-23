package com.example.ta_fanisya;

import static com.example.ta_fanisya.LoginActivity.user;
import static com.example.ta_fanisya.LoginActivity.userName;
import static com.example.ta_fanisya.Menu.totalOrder;
import static com.example.ta_fanisya.Menu.totalPrice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

public class Recap extends AppCompatActivity {

    private Button bBack;
    private TextView order,price;
    private ImageView bPesan;
    private Integer money;
    private String formattedDate;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recap);

        bBack = findViewById(R.id.btnBack1);
        bPesan = findViewById(R.id.btnPesan);
        order = findViewById(R.id.totalOrder_tv);
        price = findViewById(R.id.totalPrice_tv);

        order.setText(totalOrder.toString());
        price.setText(totalPrice.toString());

        long epoch = System.currentTimeMillis();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        formattedDate = df.format(c);

        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(Recap.this, Menu.class);
                startActivity(i);
            }
        });

        DatabaseReference myRef = database.getReference("user").child(user);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                money = snapshot.child("saldo").getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(money<=30000){
                    Toast.makeText(getApplicationContext(),
                                    "Saldo Tidak Cukup!!",
                                    Toast.LENGTH_LONG)
                            .show();
                } else {
                    final Integer mny = money - totalPrice;
                    DatabaseReference myRef2 = database.getReference("user").child(user).child("saldo");
                    myRef2.setValue(mny);

                    DatabaseReference myRef3 = database.getReference("transaksi").child("orderStatus");
                    myRef3.setValue(1);
                    DatabaseReference myRef4 = database.getReference("user").child(user).child("history").child("" + epoch);
                    myRef4.child("totalPrice").setValue(totalPrice);
                    myRef4.child("totalOrder").setValue(totalOrder);
                    myRef4.child("tanggal").setValue(formattedDate);

                    DatabaseReference myRef5 = database.getReference("history").child("" + epoch);
                    myRef5.child("totalPrice").setValue(totalPrice);
                    myRef5.child("totalOrder").setValue(totalOrder);
                    myRef5.child("tanggal").setValue(formattedDate);
                    myRef5.child("name").setValue(userName);

                    new SendRequest().execute();


                    Intent i = new Intent(Recap.this, NotifProses.class);
                    startActivity(i);
                }
            }
        });
    }

    public class SendRequest extends AsyncTask<String, Void, String> {


        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try{

                URL url = new URL("https://script.google.com/macros/s/AKfycbxeKXmquihKg9VNTs807IEFvd68YvRLfPjq1N_73KhLsJh8gW4hnNIw4aALI4MF7Z8MtQ/exec");

                JSONObject postDataParams = new JSONObject();

                //int i;
                //for(i=1;i<=70;i++)


                //    String usn = Integer.toString(i);

                //String id= "1b2DtuvL4oA8znIp6EBRCSuXLrL-80aGa9FT28bcXyac";

                postDataParams.put("tanggal",formattedDate);
                postDataParams.put("name",userName);
                postDataParams.put("totalOrder",totalOrder);
                postDataParams.put("totalPrice",totalPrice);
                //postDataParams.put("id",id);


                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result,
                    Toast.LENGTH_LONG).show();

        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
}