package com.lukasz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;


public class SearchedItem implements Serializable {
    private static final long serialVersionUID = 42L;
    private String name;
    private List<String> tags;
    private List<Double> pastPrices;
    private Category category;
    private double searchedPrice;

    public SearchedItem(String name, String tags, Category category, double searchedPrice) {
        this.name = name;
        this.pastPrices = new ArrayList<>();
        this.category = category;
        this.searchedPrice = searchedPrice;

        String[] splitedTags = tags.split(",");
        this.tags = Arrays.asList(splitedTags);
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public double getSearchedPrice() {
        return searchedPrice;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<Double> getPastPrices() {
        return pastPrices;
    }

    public void addPastPrice(double price) {
        pastPrices.add(price);
    }

    public double getAveragePrice() {
        if (pastPrices.size() == 0 ) return 0;
        if (pastPrices.size() == 1 ) return pastPrices.get(0);

        ListIterator<Double> i = pastPrices.listIterator();
        double sum = 0;
        while (i.hasNext()) {
            sum += i.next();
        }

        return sum / pastPrices.size();
    }

    @Override
    public String toString() {
        return name;
    }
}
