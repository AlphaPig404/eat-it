package com.example.androideatit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androideatit.Common.Common;
import com.example.androideatit.Interface.ItemClickListener;
import com.example.androideatit.Model.Request;
import com.example.androideatit.ViewHolder.OrderStatusHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.zip.Inflater;

public class OrderStatus extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference requests;

    RecyclerView recyclerOrder;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerOrder = findViewById(R.id.recycler_order_status);
        recyclerOrder.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerOrder.setLayoutManager(layoutManager);

        loadOrders(Common.currentUser.getPhone());
    }

    private void loadOrders(String phone) {
        Query query = requests.orderByChild("phone").equalTo(phone);
        FirebaseRecyclerOptions<Request> options = new FirebaseRecyclerOptions.Builder<Request>().setQuery(query, Request.class).build();
        adapter = new FirebaseRecyclerAdapter<Request, OrderStatusHolder>(options){

            @NonNull
            @Override
            public OrderStatusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
                return new OrderStatusHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull OrderStatusHolder orderStatusHolder, int i, @NonNull Request request) {
                orderStatusHolder.phone.setText(request.getPhone());
                orderStatusHolder.address.setText(request.getAddress());
                orderStatusHolder.requestId.setText(adapter.getRef(i).getKey());
                orderStatusHolder.status.setText(convertCodeToStatus(request.getStatus()));

                orderStatusHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int postion, boolean isLongClick) {

                    }
                });
            }
        };

        recyclerOrder.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private String convertCodeToStatus(String status) {

        switch (status){
            case "0":
                return "Placed";
            case "1":
                return "Shopping";
            case "2":
                return "Shopped";
            default:
                return "";
        }
    }
}
