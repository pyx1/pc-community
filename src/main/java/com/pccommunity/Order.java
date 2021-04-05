package com.pccommunity;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Order {
    private long idOrder;
    private String paymentMethod;
    private String state;
    private String date;
    private String details;
    private Customer client = new Customer(); //By id
    private Map<Product, Integer> prods = new ConcurrentHashMap<>(); //Map products and units



    public Order() {
    }

    public Order(long idOrder, String paymentMethod, String state, String date) {
        this.idOrder = idOrder;
        this.paymentMethod = paymentMethod;
        this.state = state;
        this.date = date;
    }

    public void addProducts(Map<Product, Integer> m1){
        prods = m1;
    }


    public long getIdOrder() {
        return idOrder;
    }
    

    public void setIdOrder(long idOrder) {
        this.idOrder = idOrder;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getTotalPrice(){
        int total = 0;
        for(Product p1 : prods.keySet()){
            total += Integer.parseInt(p1.getPrice()) * prods.get(p1);
        }
        return total;
    }
    public int getProductNumber() {
        return prods.keySet().size();
    }

    public long getIdClient(){
        return client.getIdCustomer();
    }

    /* Methods with other names because of conflicts*/ 

    public Customer takeClient() {
        return client;
    }

    public void assingClient(Customer client) {
        this.client = client;
    }
    @Override
    public String toString(){
        return "Pedido{" +
                "idPedido='"+ idOrder + '\'' +
                ", idCustomer='" + client.getIdCustomer() + '\'' +
                ", metodoPago='" + paymentMethod + '\'' +
                ", fecha='" + date + '\'' +
                ", details='" + details + '\'' +
                ", estado='" + state + '\'' +
                ", carrito='" + prods + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Order order = (Order) object;
        return idOrder == order.idOrder;
    }
    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), idOrder);
    }
}
