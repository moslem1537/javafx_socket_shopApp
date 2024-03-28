package com.bondif.clothesshop.core;


import com.bondif.clothesshop.models.Product;
import com.bondif.clothesshop.views.GUITools;

import applicationserver.Response;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ProductDaoImpl  {
	Client cl = new Client();


    
    public Collection<Product> findAll() {
        Collection<Product> products = new LinkedList<>();
        String sql = "SELECT * FROM products";
        Response r = cl.run(sql); 

       
            ArrayList<String> result = r.getValue();

            int columns = 7;
            int rows = result.size() / columns;

            for (int i = 0; i < rows; i++) {
                int index = i * columns;

                long id = Long.parseLong(result.get(index));
                String name = result.get(index + 1);
                String description = result.get(index + 2);
                int qty = Integer.parseInt(result.get(index + 3));
                double price = Double.parseDouble(result.get(index + 4));
                String image = result.get(index + 5);
                String category = result.get(index + 6);
               if(qty>0) {
                Product p = new Product(id, name, description, qty, price, image, category);
                products.add(p);}
            }
 

        return products;
    }




    public Collection<Product> findAll(String filter) {
        Collection<Product> products = new LinkedList<>();
        filter = "%" + filter + "%";
        if (filter.isEmpty()) return findAll();
        String sql = "select * from products p " +
                "where (p.id like '" +filter+ "' " +
                "or p.name like '" +filter+ "')";
        Response r = cl.run(sql); 

        
        ArrayList<String> result = r.getValue();

        int columns = 7;
        int rows = result.size() / columns;

        for (int i = 0; i < rows; i++) {
            int index = i * columns;

            long id = Long.parseLong(result.get(index));
            String name = result.get(index + 1);
            String description = result.get(index + 2);
            int qty = Integer.parseInt(result.get(index + 3));
            double price = Double.parseDouble(result.get(index + 4));
            String image = result.get(index + 5);
            String category = result.get(index + 6);
            if(qty>0) {
            Product p = new Product(id, name, description, qty, price, image, category);
            products.add(p);}
        }


        return products;
    }


    public Product findOne(long id) {
        Product product = null;
        String sql = "select id, name,description, qty, price, image,category  from products p where  p.id = '" +id+ "'";
        Response r = cl.run(sql);
  

        long idp = Long.parseLong(r.getValue().get(0));
        String name = r.getValue().get(1);
        String description = r.getValue().get(2);
        int qty = Integer.parseInt(r.getValue().get(3));
        double price = Double.parseDouble(r.getValue().get(4));
        String image = r.getValue().get(5);
        String category = r.getValue().get(6);

            product = new Product(idp, name,description, qty, price, image, category);

  

        return product;
    }

  




}