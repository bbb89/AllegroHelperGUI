package com.lukasz;

import java.io.*;

public class SaveData {
    public static void save(CategoryList categoryList, SearchedItemList searchedItemList) {
        Data data = new Data(Blacklist.getBlacklist(),
                ItemsHistory.getPastItems(),
                FoundItems.getFoundItemList(),
                TagMap.getTagMap(),
                categoryList,
                searchedItemList);

        try(ObjectOutputStream output = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("data.dat")))) {
            output.writeObject(data);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}
