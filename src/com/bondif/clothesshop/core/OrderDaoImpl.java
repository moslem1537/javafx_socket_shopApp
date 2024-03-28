package com.bondif.clothesshop.core;


import com.bondif.clothesshop.models.Order;
import com.bondif.clothesshop.models.Product;

import applicationserver.Response;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class OrderDaoImpl  {
	ProductDaoImpl productdao  =new ProductDaoImpl();
	Client cl = new Client();

 
   
    public Collection<Order> findAll(long id) {
        Collection<Order> orders = new LinkedList<>();
        String sql = "select * from orders o WHERE admin_id = '" +id+ "'";
        Response r = cl.run(sql);
        
        ArrayList<String> result = r.getValue();

        int columns = 5;
        int rows = result.size() / columns;
        for (int i = 0; i < rows; i++) {
            int index = i * columns;
            
            
            long ido = Long.parseLong(result.get(index));
            int totalqty = Integer.parseInt(result.get(index + 1));
            double price = Double.parseDouble(result.get(index + 2));
            long admin_id = Long.parseLong(result.get(index+3));
            long product_id = Long.parseLong(result.get(index+4));
            
    
       

                Order order = new Order(ido, totalqty, price, admin_id, product_id);
                

                orders.add(order);
        }
          


        return orders;
    }

   
 

  
    public void cancelorder(Order entity) {
    	Product product=new Product();
    	product=productdao.findOne(entity.getProduct_id());
    	product.setQty(product.getQty()+entity.getTotalqty());
    	
    	
   

        String sql = "delete from orders where id = '" + entity.getId() + "'";
        String sqlproduct = "update products set qty = '" + product.getQty() + "' where id = '" + product.getId() + "'";
        cl.run(sql);
        cl.run(sqlproduct);
  
  

    }
    public void order(Product product,long id,int qty) {
    product.setQty(product.getQty()-qty);
    Order order=new Order();
    order.setAdmin_id(id);
    order.setProduct_id(product.getId());
    order.setTotalprice(qty*product.getPrice());
    order.setTotalqty(qty);
    
    PreparedStatement pstmt;

    String sql = "update products set qty = '" +product.getQty()+ "' where id = '" +product.getId()+ "'";
    String sqlorder="INSERT INTO Orders VALUES (NULL, '" + order.getTotalqty() + "', '" + order.getTotalprice() + "', '" + order.getAdmin_id() + "', '" + order.getProduct_id() + "')";
    cl.run(sql);
    cl.run(sqlorder);
    
    

    }

   }
