package com.lukasz;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class modifyCategoriesController {
    private CategoryList categoryList;
    private SearchedItemList searchedItemList;

    @FXML
    ListView<Category> categories;
    @FXML
    Button addButton;
    @FXML
    Button deleteButton;
    @FXML
    TextField nameInput;
    @FXML
    TextArea urlInput;
    @FXML
    Button okButton;

    public void initialize() {
        categoryList = currentData.getCategoryList();
        searchedItemList = currentData.getSearchedItemList();
        categories.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        refreshList();
    }

    public void deleteCategories() {
        List<Category> selected = categories.getSelectionModel().getSelectedItems();
        categoryList.removeAllCategories(new ArrayList<>(selected));

        ListIterator<SearchedItem> i = searchedItemList.getSearchedItems().listIterator();
        List<SearchedItem> itemsToRemove = new ArrayList<>();
        while (i.hasNext()) {
            SearchedItem currentItem = i.next();
            if (!categoryList.getCategories().contains(currentItem.getCategory())) {
                itemsToRemove.add(currentItem);
            }
        }
        i = itemsToRemove.listIterator();
        while (i.hasNext()) {
            searchedItemList.removeItem(i.next());
        }
        refreshList();
    }

    public void addCategory() {
        String name = nameInput.getText();
        String url = urlInput.getText();
        StringBuilder sb = new StringBuilder();
        boolean notValid = false;

        if(name.isEmpty()) {
            sb.append("Nazwa przedmiotu nie może być pusta.\n");
            notValid = true;
        }
        if (url.isEmpty()) {
            sb.append("Adres url nie może być pusty.\n");
            notValid = true;
        }
        if (!url.contains("https://allegro.pl/")) {
            sb.append("Podaj adres do wyszukiwania lub kategorii na Allegro.\n");
            notValid = true;
        }
        if (!url.contains("?startingTime=1") || !url.contains("&startingTime=1")) {
            url += "&startingTime=1";
        }

        if(notValid == true) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText("Bład dodawania kategorii");
            alert.setContentText(sb.toString());

            alert.showAndWait();
        }else {
            categoryList.addCategory(new Category(name, url));
            nameInput.setText("");
            urlInput.setText("");
            refreshList();
        }

    }

    public void saveChanges() {
        currentData.save();
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    public void refreshList() {
        categories.getItems().setAll(categoryList.getCategories());
    }
}
