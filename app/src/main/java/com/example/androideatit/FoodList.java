package com.example.androideatit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.androideatit.Interface.ItemClickListener;
import com.example.androideatit.Model.Food;
import com.example.androideatit.ViewHolder.FoodListHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class FoodList extends AppCompatActivity {

    private DatabaseReference foods;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private String menuId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        foods = database.getReference("Foods");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if(getIntent() != null){
            menuId = getIntent().getStringExtra("menuId");
        }
        System.out.println(menuId);
        if(!menuId.isEmpty() && menuId != null){
            loadFoodList(menuId);
        }

    }

    private void loadFoodList(String menuId) {
        Query list = foods.orderByChild("menuId").equalTo(menuId);
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Food>().setQuery(list, Food.class).build();
        adapter = new FirebaseRecyclerAdapter<Food, FoodListHolder>(options) {

            @NonNull
            @Override
            public FoodListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
                return new FoodListHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull FoodListHolder foodListHolder, int i, @NonNull Food food) {
                foodListHolder.txtName.setText(food.getName());
                Picasso.get().load(food.getImage()).into(foodListHolder.imageView);

                final Food clickItem = food;
                foodListHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int postion, boolean isLongClick) {
                        Intent intent = new Intent(FoodList.this, FoodDetail.class);
                        intent.putExtra("foodId", adapter.getRef(postion).getKey());
                        startActivity(intent);
                    }
                });

            }
        };
        recyclerView.setAdapter(adapter);
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

}
