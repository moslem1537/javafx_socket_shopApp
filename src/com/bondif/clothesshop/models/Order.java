package com.bondif.clothesshop.models;

import java.time.LocalDateTime;
import java.util.Collection;

public class Order {
    private long id;
    private int totalqty;
    private double totalprice;
    private long admin_id;
    private long product_id;
    
    
    
    
    public Order(long id,int totalqty,double totalprice, long admin_id, long product_id) {
        this.id = id;
        this.admin_id = admin_id;
        this.product_id = product_id;
        this.totalqty=totalqty;
        this.totalprice=totalprice;
        
    }

    public Order(long id, long admin_id, long product_id) {
        this.id = id;
        this.admin_id = admin_id;
        this.product_id = product_id;
        
    }



    public Order() {
        this.id = 0;
        this.admin_id = 0;
        this.totalqty=0;
        this.product_id = 0;
        this.totalprice=0;
       
        
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(long admin_id) {
        this.admin_id = admin_id;
    }
    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }
    public int getTotalqty() {
        return totalqty;
    }

    public void setTotalqty(int totalqty) {
        this.totalqty = totalqty;
    }
    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }


}
