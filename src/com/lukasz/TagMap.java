package com.lukasz;

import java.util.HashMap;
import java.util.Map;

public class TagMap {
    private static Map<String, SearchedItem> tagMap = new HashMap<>();

    public static Map<String, SearchedItem> getTagMap() {
        return tagMap;
    }

    public static void addTag(String tag, SearchedItem searchedItem) {
        tagMap.put(tag.toUpperCase(), searchedItem);
    }

    public static void addTags(Map<String, SearchedItem> map) {
        tagMap.putAll(map);
    }

    public static void removeTag(String tag) {
        tagMap.remove(tag.toUpperCase());
    }
}
