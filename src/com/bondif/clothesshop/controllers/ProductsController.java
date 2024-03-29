package com.bondif.clothesshop.controllers;


import com.bondif.clothesshop.core.OrderDaoImpl;
import com.bondif.clothesshop.core.ProductDaoImpl;
import com.bondif.clothesshop.models.Product;
import com.bondif.clothesshop.views.ActionButtonTableCell;
import com.bondif.clothesshop.views.GUITools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.File;
import java.net.URI;
import java.util.Collection;

public class ProductsController {
    private static ObservableList<Product> productsOl;
    private static ProductDaoImpl productDao;
    private static OrderDaoImpl orderDao;
    private static AdminController admin;

    static {
        productDao = new ProductDaoImpl();
        orderDao = new OrderDaoImpl();
        
    }

    public static VBox getProductsPane() {
        // bring data from the server
        productsOl = getProductsOl();

        VBox vBox = new VBox();
        HBox hBox = new HBox();
        Region region = new Region();

        // vbox config
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(10);

        //hBox config
        hBox.setPadding(new Insets(20));
        hBox.setSpacing(10);

        //region config
        HBox.setHgrow(region, Priority.ALWAYS);



        // Products Table
        TableView<Product> productsTableView = getBasicTableView();


        productsTableView.setItems(productsOl);

        productsTableView.setRowFactory(tv -> {
            TableRow<Product> productTableRow = new TableRow<>();

            productTableRow.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && !productTableRow.isEmpty()) {
                    ProductsController.show(productTableRow.getItem().getId());
                }
            });

            return productTableRow;
        });

        // Search
        //HBox searchContainer = new HBox(20);
        TextField searchTf = new TextField();
        searchTf.setPromptText("Rechercher");
        //searchTf.setMinWidth(300);
        searchTf.getStyleClass().add("searchBar");
        searchTf.setMinWidth(200);
        searchTf.setMinHeight(28);
        searchTf.setAlignment(Pos.CENTER);
        searchTf.getStyleClass().remove("text-field");

        searchTf.textProperty().addListener((observable, oldValue, newValue) -> {
            newValue = newValue.trim();
            oldValue = oldValue.trim();
            if (!newValue.equals(oldValue)) {
                System.out.println("searching...");
                Collection<Product> filterdProducts = new ProductDaoImpl().findAll(newValue);
                productsOl = FXCollections.observableArrayList(filterdProducts);
                productsTableView.setItems(productsOl);
            }
        });



        //hBox config
        hBox.getChildren().addAll(searchTf,  region );

        vBox.getChildren().addAll(hBox, productsTableView);

        return vBox;
    }

 

    public static void show(Long id) {
        Product product = productDao.findOne(id);

        Pane pane = getProductPane(product);

        AppController.getRoot().setCenter(pane);
    }



    public static TableView<Product> getBasicTableView() {
        // Products Table
        TableView<Product> productsTableView = new TableView<>();

        // code column
        TableColumn<Product, Long> codeColumn = new TableColumn<>("Code");
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        codeColumn.prefWidthProperty().bind(productsTableView.widthProperty().divide(100 / 10)); 

        // label column
        TableColumn<Product, String> labelColumn = new TableColumn<>("product name");
        labelColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        labelColumn.prefWidthProperty().bind(productsTableView.widthProperty().divide(100 / 15)); 
        
        TableColumn<Product, String> descriptionColumn = new TableColumn<>("description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.prefWidthProperty().bind(productsTableView.widthProperty().divide(100 / 40));

        // qty column
        TableColumn<Product, Integer> qtyColumn = new TableColumn<>("Quantity available");
        qtyColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));
        qtyColumn.prefWidthProperty().bind(productsTableView.widthProperty().divide(100 / 10));

        // buyPrice column
        TableColumn<Product, Double> PriceColumn = new TableColumn<>("price ");
        PriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        PriceColumn.prefWidthProperty().bind(productsTableView.widthProperty().divide(100 / 10)); 

        // category column
        TableColumn<Product, String> categoryColumn = new TableColumn<>("category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryColumn.prefWidthProperty().bind(productsTableView.widthProperty().divide(100 / 15));

        productsTableView.getColumns().addAll(codeColumn, labelColumn,descriptionColumn, qtyColumn, PriceColumn,  categoryColumn);

        return productsTableView;
    }

    public static ObservableList<Product> getProductsOl() {
        return FXCollections.observableArrayList(productDao.findAll());
    }



    private static Pane getProductPane(Product product) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        Text codeLabelTxt = new Text("Code");
        Text labelLabelTxt = new Text("product name");
        Text descriptionLabelTxt = new Text("description");
        Text qtyLabelTxt = new Text("Quantity available");
        Text PriceLabelTxt = new Text("price ");
        Text categoryLabelTxt = new Text("category");

        ImageView imageView = new ImageView(GUITools.getImage(product.getImage()));
        imageView.setFitWidth(250);
        imageView.setPreserveRatio(true);
        Text codeTxt = new Text(product.getId().toString());
        Text labelTxt = new Text(product.getName());
        Text descriptionTxt = new Text(product.getDescription());
        Text qtyTxt = new Text(product.getQty() + "");
        Text PriceTxt = new Text(product.getPrice() + "");
        TextField qty = new TextField();
        qty.setPromptText("enter quantity to order");
        Text categoryTxt = new Text(product.getCategory());
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(20));
        hBox.setSpacing(10);
        
        
        Button btn = new Button("order a product ");
        btn.setOnAction(event -> {
            if (GUITools.openDialogYesNo("order a product", null, "Are you sure you want to order this product?", Alert.AlertType.CONFIRMATION)) {
                String qtyText = qty.getText().trim(); // Supprime les espaces en début et fin de chaîne

                if (qtyText.isEmpty()) {
                    GUITools.openDialogOk("Erreur", null, "Please enter the chosen quantity", Alert.AlertType.ERROR);
                } else {
                    try {
                        int quantity = Integer.parseInt(qtyText); // Convertit la chaîne en un entier
                        if (quantity > product.getQty()) {
                            GUITools.openDialogOk("Erreur", null, "There is not enough stock", Alert.AlertType.ERROR);
                        } else {
                        	orderDao.order(product, admin.id, quantity);
                            AppController.showProducts();
                        }
                    } catch (NumberFormatException e) {
                        // Gérez l'exception si la chaîne ne peut pas être convertie en un entier
                        GUITools.openDialogOk("Erreur", null, "Please enter a valid quantity", Alert.AlertType.ERROR);
                    }
                }
            }
        });

        hBox.getChildren().addAll(qty,  btn );

        gridPane.add(codeLabelTxt, 0, 1);
        gridPane.add(labelLabelTxt, 0, 2);
        gridPane.add(descriptionLabelTxt, 0, 3);
        gridPane.add(qtyLabelTxt, 0, 4);
        gridPane.add(PriceLabelTxt, 0, 5);
        gridPane.add(categoryLabelTxt, 0, 6);
        gridPane.add(hBox, 0, 0, 3, 1);
        
        gridPane.add(imageView, 1, 2, 3, 3);
        gridPane.add(codeTxt, 1, 1);
        gridPane.add(labelTxt, 1, 2);
        gridPane.add(descriptionTxt, 1, 3);
        gridPane.add(qtyTxt, 1, 4);
        gridPane.add(PriceTxt, 1, 5);
        gridPane.add(categoryTxt, 1, 6);

        GridPane.setHalignment(imageView, HPos.CENTER);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(20);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(60);

        gridPane.getColumnConstraints().addAll(col1, col2);

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        return gridPane;
    }


}