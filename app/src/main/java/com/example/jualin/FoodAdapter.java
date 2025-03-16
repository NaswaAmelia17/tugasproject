package com.example.jualin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.ArrayList;


public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private Context context;
    private List<MenuItem> foodList;
    private OnItemClickListener listener;

    public FoodAdapter(Context context, List<MenuItem> foodList, OnItemClickListener listener) {
        this.context = context;
        this.foodList = foodList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food_card, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        MenuItem item = foodList.get(position);
        holder.tvFoodName.setText(item.getName());
        holder.tvFoodPrice.setText(String.format("Rp %,d", item.getPrice()));
        holder.imgFood.setImageResource(item.getImageResId());

        holder.btnBuy.setOnClickListener(v -> listener.onItemClick(item));
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView tvFoodName, tvFoodPrice;
        ImageView imgFood;
        Button btnBuy;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodName = itemView.findViewById(R.id.tv_food_name);
            tvFoodPrice = itemView.findViewById(R.id.tv_food_price);
            imgFood = itemView.findViewById(R.id.iv_food);
            btnBuy = itemView.findViewById(R.id.btn_buy);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(MenuItem item);
    }

    public void updateList(List<MenuItem> newList) {
        this.foodList = new ArrayList<>(newList); // Salin list agar data tetap terjaga
        notifyDataSetChanged(); // Perbarui tampilan RecyclerView
    }

}
