package com.bondif.clothesshop.models;

public class Product {
    private Long id;
    private String name;
    private String description;
    private int qty;
    private double price;
    private String image;
    private String category;

    public Product(Long id, String name,String description, int qty, double price , String image, String category) {
        this.id = id;
        this.name = name;
        this.description =description;
        this.qty = qty;
        this.price = price;
        this.image = image;
        this.category = category;
    }

    public Product() {
        this.id = 0L;
        this.name = "";
        this.description="";
        this.qty = 0;
        this.price = 0.0;
        this.image = "";
        this.category = null;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

  

    public String getImage() {
        return image;
    }

    public String getCategory() {
        return category;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }



    public void setImage(String image) {
        this.image = image;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return name;
    }
}
