package com.bondif.clothesshop.controllers;

import java.util.Collection;

import com.bondif.clothesshop.core.OrderDaoImpl;
import com.bondif.clothesshop.core.ProductDaoImpl;
import com.bondif.clothesshop.models.Order;
import com.bondif.clothesshop.models.Product;
import com.bondif.clothesshop.views.ActionButtonTableCell;
import com.bondif.clothesshop.views.GUITools;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class OrderController {
    private static ObservableList<Order> ordersOl;
    private static OrderDaoImpl orderDao;
    private static AdminController admin;

    static {
        orderDao = new OrderDaoImpl();
    }
	public static VBox getOrdrePane() {
		 ordersOl = getOrdersOl();
		
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
    TableView<Order> orderTableView = getBasicTableView();
    
    TableColumn cancelorder = new TableColumn<>("cancel the order");
    cancelorder.setCellFactory(ActionButtonTableCell.forTableColumn("cancel the order", (Order p) -> {
    	OrderController.cancelorderHandler(p);
        return p;
    }));
    cancelorder.prefWidthProperty().bind(orderTableView.widthProperty().divide(100 / 30));
    orderTableView.getColumns().addAll(cancelorder);

    orderTableView.setItems(ordersOl);

    orderTableView.setRowFactory(tv -> {
        TableRow<Order> orderTableRow = new TableRow<>();



        return orderTableRow;
    });

    hBox.getChildren().addAll(  region );

    vBox.getChildren().addAll(hBox, orderTableView);

    return vBox;
}
	
   public static void cancelorderHandler(Order order) {
        if(GUITools.openDialogYesNo("cancel the order", null, "Are you sure you want to cancel this order?", Alert.AlertType.WARNING)) {
        	orderDao.cancelorder(order);
        }
        AppController.showOders();
    }
	
	
    public static TableView<Order> getBasicTableView() {
        // order Table
        TableView<Order> orderTableView = new TableView<>();

        // code column
        TableColumn<Order, Long> codeColumn = new TableColumn<>("Code product");
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        codeColumn.prefWidthProperty().bind(orderTableView.widthProperty().divide(100 / 20)); 

        // label column
        TableColumn<Order,Integer> labelColumn = new TableColumn<>("totalqty");
        labelColumn.setCellValueFactory(new PropertyValueFactory<>("totalqty"));
        labelColumn.prefWidthProperty().bind(orderTableView.widthProperty().divide(100 / 18)); 
        
        TableColumn<Order,Double> descriptionColumn = new TableColumn<>("totalprice");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("totalprice"));
        descriptionColumn.prefWidthProperty().bind(orderTableView.widthProperty().divide(100 / 18));

 

        orderTableView.getColumns().addAll(codeColumn, labelColumn,descriptionColumn);

        return orderTableView;
    }
    
    
    public static ObservableList<Order> getOrdersOl() {
        return FXCollections.observableArrayList(orderDao.findAll(admin.id));
    }
	
}
