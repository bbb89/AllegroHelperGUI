package com.lukasz;


import java.util.ArrayList;
import java.util.List;

public class ItemsHistory {
    private static List<Item> pastItems = new ArrayList<>();

    public static List<Item> getPastItems() {
        return pastItems;
    }

    public static boolean addItemToHistory(Item item) {
        return pastItems.add(item);
    }

    public static boolean addItemsToHistory(List<Item> items) {
        return pastItems.addAll(items);
    }

    public static void removeItemFromHistory(Item item) {
        pastItems.add(item);
    }
}
