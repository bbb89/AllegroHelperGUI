package com.lukasz;

import java.util.ArrayList;
import java.util.List;

public class FoundItems {
    private static List<Item> foundItemList = new ArrayList<>();

    public static List<Item> getFoundItemList() {
        return foundItemList;
    }

    public static boolean addFoundItems(List<Item> items) {
        return foundItemList.addAll(items);
    }

    public static void removeFoundItem(Item item) {
        foundItemList.remove(item);
    }

    public static void clearFoundItems() {
        foundItemList.removeAll(foundItemList);
    }
}
