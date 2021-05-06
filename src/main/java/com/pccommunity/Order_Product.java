package com.pccommunity;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity 
public class Order_Product implements Serializable{

    @EmbeddedId OrderProductPK idOP;

    @ManyToOne
    Product productId;

    @ManyToOne
    Order orderId;

    int uds;

    public Order_Product() {
    }

    public Order_Product(Product p, Order order, int uds) {
        this.productId = p;
        this.orderId = order;
        this.uds = uds;
        this.idOP = new OrderProductPK(this.productId, this.orderId);
    }

    public void setIdOP(OrderProductPK pk){
        System.out.println("Aqui 1");
        this.idOP = pk;
    }

    public OrderProductPK getIdOP(){
        return idOP;
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
