package com.lukasz;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class modifySearchedItemsController {
    private CategoryList categoryList;
    private SearchedItemList searchedItemList;
    private SearchedItem selectedSearchedItem;
    private Category selectedCategory;

    @FXML
    ListView searchedItems;
    @FXML
    Label searchedItemLabel;
    @FXML
    ComboBox categories;
    @FXML
    TextField nameInput;
    @FXML
    TextField priceInput;
    @FXML
    TextArea tagsInput;
    @FXML
    Button addButton;
    @FXML
    Button editButton;
    @FXML
    Button removeButton;
    @FXML
    Button okButton;


    public void initialize() {
        categoryList = currentData.getCategoryList();
        searchedItemList = currentData.getSearchedItemList();
        searchedItems.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        searchedItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedSearchedItem = (SearchedItem) newValue;
            StringBuilder sb = new StringBuilder();
            if (newValue != null) {
                sb.append("Nazwa: " + selectedSearchedItem.getName());
                sb.append("\nKategoria: " + selectedSearchedItem.getCategory().getName());
                sb.append("\nTagi: " + selectedSearchedItem.getTags().toString());
                sb.append("\nSzukana cena: maksymalnie " + selectedSearchedItem.getSearchedPrice() + " zł");
            }
            searchedItemLabel.setText(sb.toString());
        });

        categories.getItems().setAll(categoryList.getCategories());
        categories.valueProperty().addListener( (observable, oldValue, newValue) -> {
            selectedCategory = (Category) newValue;
        });

        priceInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    priceInput.setText(oldValue);
                }
            }
        });




        refreshList();
    }

    public void addItem() {
        String name = nameInput.getText();
        String price = priceInput.getText();
        String tags = tagsInput.getText();
        StringBuilder sb = new StringBuilder();
        boolean notValid = false;

        if (selectedCategory == null) {
            sb.append("Kategoria nie może być pusta.\n");
            notValid = true;
        }
        if(name.isEmpty()) {
            sb.append("Nazwa przedmiotu nie może być pusta.\n");
            notValid = true;
        }
        if (price.isEmpty()) {
            sb.append("Cena nie może być pusta.\n");
            notValid = true;
        }
        if (tags.isEmpty()) {
            sb.append("Tagi nie mogą być puste.\n");
            notValid = true;
        }

        if(notValid == true) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setHeaderText("Bład dodawania kategorii");
            alert.setContentText(sb.toString());

            alert.showAndWait();
        }else {
            tags = tags.replaceAll(",\\s+", ",");
            searchedItemList.addItem(new SearchedItem(name, tags, selectedCategory, Double.valueOf(price)));
            nameInput.setText("");
            priceInput.setText("");
            tagsInput.setText("");
            refreshList();
        }
    }

    public void removeItem() {
        if (selectedSearchedItem != null) {
            searchedItemList.removeItem(selectedSearchedItem);
            selectedSearchedItem = null;
            refreshList();
        }
    }

    public void editItem() {
        categories.valueProperty().setValue(selectedSearchedItem.getCategory());
        nameInput.setText(selectedSearchedItem.getName());
        String price = String.valueOf(selectedSearchedItem.getSearchedPrice());
        priceInput.setText(price.substring(0, price.indexOf(".")));
        String tags = selectedSearchedItem.getTags().toString();
        tags = tags.substring(1,tags.length() - 1);
        tagsInput.setText(tags);
        searchedItemList.removeItem(selectedSearchedItem);
        refreshList();

    }

    public void saveChanges() {
        currentData.save();
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    public void refreshList() {
        searchedItems.getItems().setAll(searchedItemList.getSearchedItems());
    }

}
