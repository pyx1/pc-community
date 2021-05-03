package com.pccommunity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Order_Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProductOrder;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name="idProduct")
    Product productId;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name="idOrder")
    Order orderId;

    int uds;

    public Order_Product() {
    }

    public Order_Product(Product p, Order order, int uds) {
        this.productId = p;
        this.orderId = order;
        this.uds = uds;
    }

    public long getIdProductOrder() {
        return idProductOrder;
    }

    public void setIdProductOrder(long idProductOrder) {
        this.idProductOrder = idProductOrder;
    }

    public Product getProduct() {
        return productId;
    }

    public void setProduct(Product p) {
        this.productId = p;
    }

    public Order getOrder() {
        return orderId;
    }

    public void setOrder(Order order) {
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
