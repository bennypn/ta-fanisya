package com.example.ta_fanisya.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ta_fanisya.R;
import com.example.ta_fanisya.model.History;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class historyUserAdapter extends FirebaseRecyclerAdapter<History,historyUserAdapter.historyViewholder> {

    //private String name;

    public historyUserAdapter(
            @NonNull FirebaseRecyclerOptions<History> options)
    {
        super(options);
    }

    @Override
    protected void
    onBindViewHolder(@NonNull historyViewholder holder,
                     int position, @NonNull History model)
    {
        String tanggal = model.getTanggal();
        //name = model.getName();
        Integer totalOrder = model.getTotalOrder();
        Integer totalPrice = model.getTotalPrice();


        holder.tanggaltv.setText(tanggal);
        //holder.nametv.setText(name);
        holder.totalordertv.setText(totalOrder.toString());
        holder.totalpricetv.setText(totalPrice.toString());


    }

    // Function to tell the class about the Card view (here
    // "History.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public historyViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new historyUserAdapter.historyViewholder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "History.xml")
    class historyViewholder
            extends RecyclerView.ViewHolder {
        TextView tanggaltv, nametv,totalpricetv,totalordertv;
        public historyViewholder(@NonNull View itemView)
        {
            super(itemView);

            tanggaltv = itemView.findViewById(R.id.hisUser_tanggal_tv);
            totalordertv = itemView.findViewById(R.id.hisUser_totalorder_tv);
            //nametv = itemView.findViewById(R.id.hisUser_name_tv);
            totalpricetv = itemView.findViewById(R.id.hisUser_price_tv);
        }
    }


}
