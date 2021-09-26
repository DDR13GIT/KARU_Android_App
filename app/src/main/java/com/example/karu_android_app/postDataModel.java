package com.example.karu_android_app;

public class postDataModel {
    private String title;
    private int size;
    private String description;
    private String category;
    private double price;
    private String imageUrl;

    public postDataModel() {
    }

    public postDataModel(String title, int size, String description, String category, double price, String imageUrl) {
        this.title = title;
        this.size = size;
        this.description = description;
        this.category = category;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public int getSize() {
        return size;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }
}
