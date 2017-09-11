package com.lukasz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CategoryList implements Serializable {
    private static final long serialVersionUID = 42L;
    List<Category> categories;


    public CategoryList() {
        this.categories = new ArrayList<>();
    }

    public List<Category> getCategories() {
        return categories;
    }

    public boolean addCategory(Category category) {
        return categories.add(category);
    }

    public boolean loadCategories(CategoryList categories) {
        return this.categories.addAll(categories.getCategories());
    }

    public boolean removeCategory(Category category, SearchedItemList searchedItemList) {
        return categories.remove(category);
    }

    public boolean removeAllCategories(ArrayList<Category> categories) {
        return this.categories.removeAll(categories);
    }
}
