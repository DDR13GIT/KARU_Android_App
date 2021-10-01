package com.example.karu_android_app;

public class cartDataModel {
    private String title;
    private int count;
    private double price;
    private String imageUrl;

    public cartDataModel() {
    }

    public cartDataModel(String title, int count, double price, String imageUrl) {
        this.title = title;
        this.count = count;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public int getCount() {
        return count;
    }

    public double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
