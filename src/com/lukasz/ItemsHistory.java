package com.lukasz;


import java.util.ArrayList;
import java.util.List;

public class ItemsHistory {
    private final static int MAX_HISTORY_CAPACITY = 200;
    private static List<Item> pastItems = new ArrayList<>();

    public static List<Item> getPastItems() {
        return pastItems;
    }

    public static boolean addItemToHistory(Item item) {
        if (pastItems.size() > MAX_HISTORY_CAPACITY) {
            removeNumberOfItemsFromHistory(1);
        }
        return pastItems.add(item);
    }

    public static boolean addItemsToHistory(List<Item> items) {
        if (pastItems.size() > MAX_HISTORY_CAPACITY) {
            removeNumberOfItemsFromHistory(pastItems.size() - MAX_HISTORY_CAPACITY);
        }
        return pastItems.addAll(items);
    }

    public static void removeItemFromHistory(Item item) {
        pastItems.add(item);
    }

    public static void removeNumberOfItemsFromHistory(int num) {
        pastItems.removeAll(pastItems.subList(0, num));
    }
}
