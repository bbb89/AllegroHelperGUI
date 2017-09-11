package com.lukasz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class SearchedItemList implements Serializable {
    private static final long serialVersionUID = 42L;
    private List<SearchedItem> searchedItems;

    public SearchedItemList() {
        this.searchedItems = new ArrayList<>();
    }

    public List<SearchedItem> getSearchedItems() {
        return searchedItems;
    }

    public boolean addItem(SearchedItem item) {

        boolean result = searchedItems.add(item);

        ListIterator<String> i = item.getTags().listIterator();
        while (i.hasNext()) {
            TagMap.getTagMap().put(i.next().toUpperCase(), item);
        }
//        System.out.println(TagMap.getTagMap());

        return result;
    }

    public boolean loadItems(SearchedItemList items) {
        return this.searchedItems.addAll(items.getSearchedItems());
    }

    public boolean removeItem(SearchedItem item) {
        List<String> tagsToRemove = item.getTags();
        boolean result = searchedItems.remove(item);
        tagsToRemove.forEach( tag -> TagMap.removeTag(tag) );
//        System.out.println(TagMap.getTagMap());


//        ListIterator<String> i = item.getTags().listIterator();
//        while (i.hasNext()) {
//
//            String tagToDelete = i.next().toUpperCase();
//            if (TagMap.getTagMap().get(tagToDelete) == item) {
//                TagMap.getTagMap().put(i.next(), item);
//            }
//
//        }

        return result;
    }


}
