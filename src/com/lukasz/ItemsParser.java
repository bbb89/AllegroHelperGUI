package com.lukasz;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ItemsParser {
    private final static String ITEM_LIST_START = "{\"title\":\"lista ofert\"";
    private final static String ITEM_LIST_END = "}}}]}";

    private final static String TITLE_START = "\"title\":{\"text\":\"";
    private final static String TITLE_END = "\",\"bold\":";
    private final static String PRICE_START = "\"price\":{\"normal\":{\"amount\":\"";
    private final static String PRICE_END = "\",\"currency\":";
    private final static String URL_START = ",\"url\":\"";
    private final static String URL_END = "\",\"photos\":";
    private final static String TYPE_START = "\",\"type\":\"";
    private final static String TYPE_END = "\",\"isEnded\"";
    private final static String USED_START = "\"Stan\",\"value\":\"";
    private final static String USED_END = "\",\"highlight\"";
    private final static String SHIPPING_START = "\"deliveryInfo\":[{\"price\":{\"amount\":\"";
    private final static String SHIPPING_END = "\",\"currency\":\"PLN\"},";




    public static List<Item> parseItems(Category category) {
        String page = "";
        String itemTitle = "";
        String itemPrice = "";
        String itemURL = "";
        String itemUsed = "";
        String shippingPrice = "";
        List<Item> items = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(category.getUrl()).get();
            page = doc.toString();
            if (page.contains(ITEM_LIST_START)) {
                page = page.substring(page.indexOf(ITEM_LIST_START), page.indexOf(ITEM_LIST_END));
                String itemsRaw[] = page.split("(?=\"title\")");

                for (int i = 2; i < itemsRaw.length; i++) {
                    String item = itemsRaw[i];
                    if (item.contains("\"title\":\"sponsorowane\"")) break;


                    String type = item.substring((item.indexOf(TYPE_START) + TYPE_START.length()), item.indexOf(TYPE_END));
                    if (type.equals("auction")) continue;

                    itemTitle = item.substring((item.indexOf(TITLE_START) + TITLE_START.length()), item.indexOf(TITLE_END));
                    if (!checkBlacklist(itemTitle)) continue;

                    itemPrice = item.substring(item.indexOf(PRICE_START) + PRICE_START.length(), item.indexOf(PRICE_END));
                    itemURL = item.substring((item.indexOf(URL_START) + URL_START.length()), item.indexOf(URL_END));

                    try {
                        itemUsed = item.substring(item.indexOf(USED_START) + USED_START.length(), item.indexOf(USED_END));
                    }catch (StringIndexOutOfBoundsException e) {
                        itemUsed = "brak danych";
                    }

                    try {
                        shippingPrice = item.substring(item.indexOf(SHIPPING_START) + SHIPPING_START.length(), item.indexOf(SHIPPING_END));
                    }catch (StringIndexOutOfBoundsException e) {
                        shippingPrice = "0";
                    }

                    items.add(new Item(itemTitle, itemPrice, itemURL, category, itemUsed, shippingPrice));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return items;
    }

    public static boolean checkBlacklist(String itemTitle) {
        String[] words = itemTitle.toUpperCase().split(" ");

        for (String word : words) {
            if (Blacklist.getBlacklist().contains(word)) return false;
        }

        return true;
    }
}
