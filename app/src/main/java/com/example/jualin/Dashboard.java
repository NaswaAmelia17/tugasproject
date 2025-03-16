package com.example.jualin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {
    private EditText etSearch;
    private RecyclerView recyclerFood;
    private TextView tvTotalItems, tvTotalPrice;
    private LinearLayout barTotal;

    private List<MenuItem> foodList = new ArrayList<>();
    private List<MenuItem> cartList = new ArrayList<>();
    private int totalItems = 0;
    private int totalPrice = 0;
    private FoodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Inisialisasi UI
        etSearch = findViewById(R.id.et_search);
        recyclerFood = findViewById(R.id.recyclerFood);
        tvTotalItems = findViewById(R.id.tv_total_items);
        tvTotalPrice = findViewById(R.id.tv_total_price);
        barTotal = findViewById(R.id.bar_total);

        // Konfigurasi RecyclerView dengan GridLayoutManager (2 kolom)
        recyclerFood.setLayoutManager(new GridLayoutManager(this, 2));

        // Inisialisasi data makanan
        initFoodList();

        // Set adapter setelah data di-load
        adapter = new FoodAdapter(this, foodList, this::addToCart);
        recyclerFood.setAdapter(adapter);

        // Cek apakah data makanan berhasil di-load
        Log.d("Dashboard", "Total items in RecyclerView: " + foodList.size());

        // Search Bar Functionality
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterFoodList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Aksi ketika total bar diklik (Menuju ke halaman Checkout)
        barTotal.setOnClickListener(v -> {
            if (cartList.isEmpty()) {
                Toast.makeText(Dashboard.this, "Keranjang masih kosong!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(Dashboard.this, Checkout.class);
                intent.putExtra("cartList", new ArrayList<>(cartList)); // Kirim daftar item ke Checkout
                intent.putExtra("totalPrice", totalPrice);
                startActivity(intent);
            }
        });

        updateCartUI();
    }

    // Inisialisasi daftar makanan
    private void initFoodList() {
        foodList.clear();
        foodList.add(new MenuItem("Micellar Water", 45000, R.drawable.pict1));
        foodList.add(new MenuItem("Serum Wajah", 35000, R.drawable.pict2));
        foodList.add(new MenuItem("Moisturizer", 38000, R.drawable.pict3));
        foodList.add(new MenuItem("Hand Cream", 35000, R.drawable.pict4));
        foodList.add(new MenuItem("Foam Wash", 20000, R.drawable.pict5));
        foodList.add(new MenuItem("Hair Spray", 10000, R.drawable.pict6));
    }

    // Filter Makanan Berdasarkan Input Search
    private void filterFoodList(String query) {
        List<MenuItem> filteredList = new ArrayList<>();
        for (MenuItem item : foodList) {
            if (item.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }

        if (adapter != null) {
            adapter.updateList(filteredList); // Pastikan updateList() tersedia di FoodAdapter
        } else {
            Log.e("Dashboard", "Adapter is null!"); // Debugging jika ada error
        }
    }

    // Menambahkan Item ke Keranjang
    private void addToCart(MenuItem item) {
        cartList.add(item);
        totalItems++;
        totalPrice += item.getPrice();
        updateCartUI();
    }

    // Update Tampilan Keranjang
    private void updateCartUI() {
        if (totalItems > 0) {
            tvTotalItems.setText(totalItems + " items");
            tvTotalPrice.setText(String.format("Rp %,d", totalPrice)); // Format harga
            barTotal.setVisibility(View.VISIBLE);
        } else {
            barTotal.setVisibility(View.GONE);
        }
    }
}
