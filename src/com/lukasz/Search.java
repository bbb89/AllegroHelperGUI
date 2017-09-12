package com.lukasz;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Search {
    private static Predicate<Item> isSearched = item -> {
        for (String tag : TagMap.getTagMap().keySet()) {
            if (item.getName().toUpperCase().contains(tag)) {
                TagMap.getTagMap().get(tag).addPastPrice(item.getNumericPrice());
                if (item.getNumericPrice() <= TagMap.getTagMap().get(tag).getSearchedPrice()) {
                    return true;
                }
            }
        }

        return false;
    };

    private static Predicate<Item> wasCheckedBefore = item -> {
        if (ItemsHistory.getPastItems().contains(item)) {
            return false;
        }else {
            ItemsHistory.addItemToHistory(item);
            return true;
        }
    };

    public static void doSearch(Category category) {
        List<Item> list = ItemsParser.parseItems(category);
        ItemList itemList = new ItemList(list);
        System.out.println("Checking...");

        List<Item> itemsFound = itemList.getItemList()
                .stream()
                .filter( wasCheckedBefore )
                .filter( isSearched )
                .collect(Collectors.toList());

        FoundItems.addFoundItems(itemsFound);

        ListIterator<Item> i = itemsFound.listIterator();
        while (i.hasNext()) {
            Item item = i.next();
            Notification.notify(item.getUrl());
        }
    }

}
