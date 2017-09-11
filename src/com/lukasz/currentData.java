package com.lukasz;

public class currentData {
    private static CategoryList categoryList;
    private static SearchedItemList searchedItemList;


    static {
        categoryList = new CategoryList();
        searchedItemList = new SearchedItemList();
        LoadData.load(categoryList, searchedItemList);
    }

    public static CategoryList getCategoryList() {
        return categoryList;
    }

    public static void setCategoryList(CategoryList catList) {
        categoryList = catList;
    }

    public static SearchedItemList getSearchedItemList() {
        return searchedItemList;
    }

    public static void setSearchedItemList(SearchedItemList sItemList) {
        searchedItemList = sItemList;
    }

    public static void save() {
        SaveData.save(categoryList, searchedItemList);
    }

}
