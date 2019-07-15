package com.example.androideatit.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androideatit.Interface.ItemClickListener;
import com.example.androideatit.R;

public class OrderStatusHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView requestId, status, phone, address;
    public ItemClickListener itemClickListener;

    public OrderStatusHolder(@NonNull View itemView) {
        super(itemView);

        requestId = (TextView) itemView.findViewById(R.id.order_id);
        status = (TextView) itemView.findViewById(R.id.order_status);
        phone = (TextView) itemView.findViewById(R.id.order_phone);
        address = (TextView) itemView.findViewById(R.id.order_address);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
