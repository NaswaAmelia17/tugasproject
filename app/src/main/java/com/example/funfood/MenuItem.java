package com.example.funfood;

import java.io.Serializable;

/**
 * Model untuk item makanan dalam aplikasi.
 * Menggunakan Serializable agar bisa dikirim antar Activity.
 */
public class MenuItem implements Serializable {
    private String name;
    private int price;
    private int imageResId;
    private int quantity;
    private String imageUrl;

    /**
     * Konstruktor untuk membuat objek MenuItem.
     *
     * @param name       Nama makanan
     * @param price      Harga makanan
     * @param imageResId ID gambar dari drawable
     */
    public MenuItem(String name, int price, int imageResId) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
        this.quantity = 1;
        this.imageUrl = imageUrl;
    }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    // Getter untuk nama makanan
    public String getName() {
        return name;
    }

    // Getter untuk harga makanan
    public int getPrice() {
        return price;
    }

    // Getter untuk ID gambar makanan dari drawable
    public int getImageResId() {
        return imageResId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Untuk debugging: Mengembalikan informasi MenuItem dalam bentuk string.
     */
    @Override
    public String toString() {
        return "MenuItem{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", imageResId=" + imageResId +
                '}';
    }
}
