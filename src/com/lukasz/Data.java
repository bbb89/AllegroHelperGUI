package com.lukasz;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Data implements Serializable {
    private static final long serialVersionUID = 42L;
    private List<String> blacklist;
    private List<Item> pastItems;
    private List<Item> foundItems;
    private Map<String, SearchedItem> tagMap;
    private CategoryList categoryList;
    private SearchedItemList searchedItemList;

    public Data(List<String> blacklist, List<Item> pastItems, List<Item> foundItems, Map<String, SearchedItem> tagMap, CategoryList categoryList, SearchedItemList searchedItemList) {
        this.blacklist = blacklist;
        this.pastItems = pastItems;
        this.foundItems = foundItems;
        this.tagMap = tagMap;
        this.categoryList = categoryList;
        this.searchedItemList = searchedItemList;
    }

    public List<String> getBlacklist() {
        return blacklist;
    }

    public List<Item> getPastItems() {
        return pastItems;
    }

    public List<Item> getFoundItems() {
        return foundItems;
    }

    public Map<String, SearchedItem> getTagMap() {
        return tagMap;
    }

    public CategoryList getCategoryList() {
        return categoryList;
    }

    public SearchedItemList getSearchedItemList() {
        return searchedItemList;
    }
}
