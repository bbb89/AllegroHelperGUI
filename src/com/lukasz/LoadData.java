package com.lukasz;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class LoadData {
    public static void load(CategoryList categoryList, SearchedItemList searchedItemList) {
        Data loadedData;
        try(ObjectInputStream input = new ObjectInputStream(new BufferedInputStream(new FileInputStream("data.dat")))) {
            loadedData = (Data) input.readObject();
            Blacklist.addWords(loadedData.getBlacklist());;
            ItemsHistory.addItemsToHistory(loadedData.getPastItems());
            FoundItems.addFoundItems(loadedData.getFoundItems());
            categoryList.loadCategories(loadedData.getCategoryList());
            searchedItemList.loadItems(loadedData.getSearchedItemList());
            TagMap.addTags(loadedData.getTagMap());
        }catch (FileNotFoundException e) {
            System.out.println("Brak pliku danych do odczytu");
        }catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
