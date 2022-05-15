package com.pccommunity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity @IdClass(OrderProductPK.class)
@Table(indexes = {@Index(columnList = "order_id_id_order", unique = false), 
@Index(columnList = "product_id_id_product", unique = false)})
public class Order_Product implements Serializable{
    
    @Id
    @ManyToOne
    Product productId;
    @Id
    @ManyToOne
    Order orderId;

    int uds;

    public Order_Product() {
    }

    public Order_Product(Product p, Order order, int uds) {
        this.productId = p;
        this.orderId = order;
        this.uds = uds;
    }

    @JsonIgnore
    public Product getProductId() {
        return productId;
    }
    public String getProductName(){
        return productId.getName();
    }
    public void setProductId(Product p) {
        this.productId = p;
    }
    @JsonIgnore
    public Order getOrderId() {
        return orderId;
    }

    public void setOrderId(Order order) {
        this.orderId = order;
    }

    public int getUds() {
        return uds;
    }

    public void setUds(int uds) {
        this.uds = uds;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "[" +
                "product=" + productId.getName() +
                ", uds=" + uds +
                ']';
    }
}
