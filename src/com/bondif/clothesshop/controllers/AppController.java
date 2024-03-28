package com.bondif.clothesshop.controllers;

import com.bondif.clothesshop.views.GUIGenerator;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;

public class AppController {
    private static BorderPane root;
    private static Stage stage;
    private static Scene scene;

    static {
        System.out.println("launched");
        root = new BorderPane();
        root.setTop(GUIGenerator.getTopBar());
        root.getTop().getStyleClass().add("top");
        root.getStyleClass().add("root");
    }

    public static Stage getStage(){
        return stage;
    }

    public static Scene getScene() { return scene; }

    public static void setScene(Region pane, float width, float height) {
        scene = new Scene(pane, width, height);
    }

    public static void launch(Stage stage) {
        AppController.stage = stage;
        AppController.stage.setTitle("Clothes Shop");
        setScene(AdminController.getLoginInterface(), 450, 620);
        scene.getStylesheets().add("decorateForm.css");
        AppController.stage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        AppController.stage.initStyle(StageStyle.TRANSPARENT);
        AppController.stage.show();
    }

    public static void showDashboard(){
        Pane pane = DashboardController.getDashboardPane();
        root.setCenter(pane);
    }

    public static void showProducts(){
        Pane pane = ProductsController.getProductsPane();
        root.setCenter(pane);
    }
    public static void showOders(){
        Pane pane = OrderController.getOrdrePane();
        root.setCenter(pane);
    }



    public static BorderPane getRoot() {
        return root;
    }

    public static String chooseProductImageHandler() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose product image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("images", "*.jpeg; *.png; *.jpg")
        );
        File f = fileChooser.showOpenDialog(stage);

        if(f != null) {
            return f.toPath().toString();
        }

        return null;
    }


}
