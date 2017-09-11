package com.lukasz;

import java.util.ArrayList;
import java.util.List;

public class Blacklist {
    private static List<String> blacklist = new ArrayList<>();

    public static List<String> getBlacklist() {
        return blacklist;
    }

    public static boolean addWord(String word) {
        return blacklist.add(word);
    }

    public static boolean addWords(List<String> words) {
        return blacklist.addAll(words);
    }

    public static boolean removeWord(String word) {
        return blacklist.remove(word);
    }


}
