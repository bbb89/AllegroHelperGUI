package com.lukasz;

import java.util.ArrayList;
import java.util.List;

public class ItemList {
    List<Item> itemList;

    public ItemList() {
        this.itemList = new ArrayList<>();
    }

    public ItemList(List<Item> list) {
        this.itemList = list;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public boolean addItem(Item item) {
        return itemList.add(item);
    }

    public boolean removeItem(Item item) {
        return itemList.remove(item);
    }



    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(int i = 1; i <= itemList.size(); i++) {
            builder.append(i);
            builder.append(". ");
            builder.append(itemList.get(i - 1));
            builder.append("\n");
        }
        return builder.toString();
    }
}
