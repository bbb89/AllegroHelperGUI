package com.lukasz;

import java.io.Serializable;

public class Item implements Serializable {
    private static final long serialVersionUID = 42L;
    private String name;
    private String price;
    private double numericPrice;
    private String itemUrl;
    private Category category;
    private String used;
    private String shippingPrice;

    public Item(String name, String price, String itemUrl, Category category, String used, String shippingPrice) {
        this.name = name;
        this.price = price;
        this.itemUrl = itemUrl;
        numericPrice = Double.valueOf(price);
        this.category = category;
        this.used = used;
        this.shippingPrice = shippingPrice;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public double getNumericPrice() {
        return numericPrice;
    }

    public String getUrl() {
        return itemUrl;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return name + " :: " + price;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        return this.name.equals( ((Item) obj).getName() );
    }
}
