package com.example.androideatit.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androideatit.Interface.ItemClickListener;
import com.example.androideatit.R;

public class FoodListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtName;
    public ImageView imageView;
    public ItemClickListener itemClickListener;

    public FoodListHolder(View itemView){
        super(itemView);

        txtName = (TextView) itemView.findViewById(R.id.food_name);
        imageView = (ImageView) itemView.findViewById(R.id.food_image);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
