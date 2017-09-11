package com.lukasz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view.fxml"));
        Parent root = loader.load();
//        Controller controller = loader.getController();
        primaryStage.setTitle("Allegro Helper");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
        root.requestFocus();

    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void initSetup() {

    }

    @Override
    public void stop() throws Exception {
        currentData.save();
        super.stop();
    }
}
