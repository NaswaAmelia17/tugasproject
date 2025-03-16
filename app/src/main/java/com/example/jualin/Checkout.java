package com.example.jualin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Typeface;


import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Checkout extends AppCompatActivity {
    private LinearLayout layoutCheckoutList;
    private TextView tvSubtotal, tvDeliveryFee, tvTotal;
    private Button btnCheckout;

    private ArrayList<MenuItem> cartList;
    private final int DELIVERY_FEE = 7000; // Biaya pengiriman tetap

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Inisialisasi UI berdasarkan ID dari checkout.xml
        layoutCheckoutList = findViewById(R.id.layout_checkout_list);
        tvSubtotal = findViewById(R.id.tv_subtotal);
        tvDeliveryFee = findViewById(R.id.tv_delivery_fee);
        tvTotal = findViewById(R.id.tv_total);
        btnCheckout = findViewById(R.id.btn_checkout);

        // Mendapatkan data dari intent
        cartList = (ArrayList<MenuItem>) getIntent().getSerializableExtra("cartList");
        if (cartList == null) cartList = new ArrayList<>();

        displayCartItems();

        // Tombol Checkout
        btnCheckout.setOnClickListener(v -> {
            if (cartList.isEmpty()) {
                Toast.makeText(Checkout.this, "Keranjang kosong!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(Checkout.this, OrderDone.class);
                intent.putExtra("totalPrice", calculateTotal());
                startActivity(intent);
                finish();
            }
        });
    }

    private void displayCartItems() {
        layoutCheckoutList.removeAllViews(); // Hapus tampilan lama
        if (cartList == null || cartList.isEmpty()) {
            return; // Jika keranjang kosong, keluar dari metode
        }

        int subtotal = 0;

        for (MenuItem item : cartList) {
            // Buat layout item makanan secara dinamis
            LinearLayout itemLayout = new LinearLayout(this);
            itemLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            itemLayout.setOrientation(LinearLayout.HORIZONTAL);
            itemLayout.setPadding(8, 8, 8, 8);

            // Buat ImageView untuk gambar makanan
            ImageView foodImage = new ImageView(this);
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(120, 120);
            imageParams.setMargins(0, 0, 16, 0);
            foodImage.setLayoutParams(imageParams);
            foodImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

            // Load gambar makanan (dari drawable atau URL)
            if (item.getImageResId() != 0) {
                foodImage.setImageResource(item.getImageResId());
            } else if (item.getImageUrl() != null && !item.getImageUrl().isEmpty()) {
                Glide.with(this).load(item.getImageUrl()).into(foodImage);
            }

            // Buat layout untuk teks
            LinearLayout textLayout = new LinearLayout(this);
            textLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1
            ));
            textLayout.setOrientation(LinearLayout.VERTICAL);

            // Nama makanan
            TextView foodName = new TextView(this);
            foodName.setText(item.getName());
            foodName.setTextSize(16);
            foodName.setTypeface(null, Typeface.BOLD);
            textLayout.addView(foodName);

            // Harga makanan
            TextView foodPrice = new TextView(this);
            foodPrice.setText(String.format("Rp %,d", item.getPrice() * item.getQuantity()));
            textLayout.addView(foodPrice);

            // Jumlah makanan
            TextView foodQuantity = new TextView(this);
            foodQuantity.setText("Jumlah: " + item.getQuantity());
            textLayout.addView(foodQuantity);

            // Tambahkan elemen ke dalam item layout
            itemLayout.addView(foodImage);
            itemLayout.addView(textLayout);

            // Tambahkan item ke daftar
            layoutCheckoutList.addView(itemLayout);
            subtotal += item.getPrice() * item.getQuantity();
        }

        // Update harga total
        updatePrice();
    }


    private void updatePrice() {
        int subtotal = 0;
        for (MenuItem item : cartList) {
            subtotal += item.getPrice() * item.getQuantity();
        }

        int total = subtotal + DELIVERY_FEE;
        tvSubtotal.setText(String.format("Rp %,d", subtotal));
        tvDeliveryFee.setText(String.format("Rp %,d", DELIVERY_FEE));
        tvTotal.setText(String.format("Rp %,d", total));

        btnCheckout.setVisibility(cartList.isEmpty() ? View.GONE : View.VISIBLE);
    }

    private int calculateTotal() {
        int subtotal = 0;
        for (MenuItem item : cartList) {
            subtotal += item.getPrice() * item.getQuantity();
        }
        return subtotal + DELIVERY_FEE;
    }
}
