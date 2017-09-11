package com.lukasz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ListIterator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Controller {
    private CategoryList categoryList;
    private SearchedItemList searchedItemList;
    private Category selectedCategory;
    private Item selectedFoundItem;
    private boolean running;
    Thread searchThread;

    Predicate<SearchedItem> filterItemsByCategory = searchedItem -> searchedItem.getCategory() == selectedCategory;

    @FXML
    ComboBox<Category> categories;
    @FXML
    ListView<SearchedItem> searchedItems;
    @FXML
    ListView<Item> foundItems;
    @FXML
    Label searchedItemLabel;
    @FXML
    Label foundItemLabel;
    @FXML
    Button modifyCategoriesButton;
    @FXML
    Button modifySearchedItems;
    @FXML
    ToggleButton runSearchButton;
    @FXML
    ProgressBar progressBar;
    @FXML
    CheckBox openInBrowserCheckbox;
    @FXML
    Button openInBrowserButton;
    @FXML
    Button removeFoundItemButton;
    @FXML
    Button clearFoundItemsButton;


    public void initialize() {
        categoryList = currentData.getCategoryList();
        searchedItemList = currentData.getSearchedItemList();
        progressBar.setProgress(0);
        System.out.println(TagMap.getTagMap());

        refreshCategories();
        refreshFoundItemsList();

        categories.valueProperty().addListener( (observable, oldValue, newValue) -> {
            selectedCategory = newValue;
            refreshSearchedItemsList();
        });

        searchedItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            StringBuilder sb = new StringBuilder();
            sb.append("Szczegóły szukanego przedmiotu:\n");
            if (newValue != null) {
                sb.append("Nazwa: " + newValue.getName());
                sb.append("\nKategoria: " + newValue.getCategory().getName());
                sb.append("\nSzukana cena: maksymalnie " + newValue.getSearchedPrice() + " zł");
            }
            searchedItemLabel.setText(sb.toString());
        });

        foundItems.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        foundItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedFoundItem = newValue;
            StringBuilder sb = new StringBuilder();
            if (newValue != null) {
                sb.append("Nazwa: " + selectedFoundItem.getName());
                sb.append("\nKategoria: " + selectedFoundItem.getCategory().getName());
                sb.append("\nCena: " + selectedFoundItem.getPrice() + " zł");
            }
            foundItemLabel.setText(sb.toString());
        });

        openInBrowserCheckbox.setSelected(true);
        openInBrowserCheckbox.selectedProperty().addListener( (observable, ch1, ch2) -> {
            Notification.setOpenInBrowser(ch2);
            System.out.println(ch2);
        });


//      2 change listeners listens to focus property of buttons to modify category or searched items.
//      When user backs from other stage, list and ComboBox are refreshing.

        modifyCategoriesButton.focusedProperty().addListener( (observable, t1, t2) -> {
            if (t2 == true) {
                refreshCategories();
                refreshSearchedItemsList();
            }
        });

        modifySearchedItems.focusedProperty().addListener( (observable, t1, t2) -> {
            if (t2 == true) {
                refreshCategories();
                refreshSearchedItemsList();
            }
        });


    }

    public void modifyCategories() throws Exception {
        Stage modifyCategories = new Stage();

        Parent root = FXMLLoader.load(getClass().getResource("modifyCategories.fxml"));
        modifyCategories.setTitle("Kategorie");
        modifyCategories.setScene(new Scene(root, 300, 480));
        modifyCategories.initModality(Modality.APPLICATION_MODAL);
        modifyCategories.show();
        root.requestFocus();
    }

    public void modifySearchedItems() throws Exception {
        Stage modifyCategories = new Stage();

        Parent root = FXMLLoader.load(getClass().getResource("modifySearchedItems.fxml"));
        modifyCategories.setTitle("Szukane przedmioty");
        modifyCategories.setScene(new Scene(root, 700, 480));
        modifyCategories.initModality(Modality.APPLICATION_MODAL);
        modifyCategories.show();
        root.requestFocus();
    }

    public void openFoundItem() {
        Notification.openWebpage(selectedFoundItem.getUrl());
    }

    public void deleteFoundItem() {
        FoundItems.removeFoundItem(selectedFoundItem);
        refreshFoundItemsList();
    }

    public void clearFoundItems() {
        FoundItems.clearFoundItems();
        refreshFoundItemsList();
    }

    public void runSearch() {
        running = !running;
        if (running) {
            searchThread = new Thread( () -> {

                while(true) {
                    System.out.println("Start");
                    ListIterator<Category> i = categoryList.getCategories().listIterator();
                    while (i.hasNext()) {
                        Category currentCategory = i.next();
                        Search.doSearch(currentCategory);
                        refreshFoundItemsList();
//                    System.out.println(test.getPastPrices());
                    }

                    try {
                        Thread.sleep(10000);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            });
            searchThread.start();
            progressBar.setProgress(-1);
        }else {
            searchThread.stop();
            progressBar.setProgress(0);
        }
    }

    public void refreshSearchedItemsList() {
        searchedItems.getItems().setAll(searchedItemList.getSearchedItems()
                .stream()
                .filter(filterItemsByCategory)
                .collect(Collectors.toList()));
    }

    public void refreshCategories() {
        categories.getItems().setAll(categoryList.getCategories());
        if (!categoryList.getCategories().isEmpty()) {
            selectedCategory = categoryList.getCategories().get(0);
            categories.valueProperty().setValue(selectedCategory);
            refreshSearchedItemsList();
        }
    }

    public void refreshFoundItemsList() {
        foundItems.getItems().setAll(FoundItems.getFoundItemList());
    }


}
