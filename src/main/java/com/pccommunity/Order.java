package com.pccommunity;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idOrder;
    private String paymentMethod;
    private String state;
    private String date;
    private String details;
    @ManyToOne
    private Customer client; //By id

    @OneToMany(mappedBy = "productId")
    private List<Order_Product> prods; //Map products and units


    public Order() {
    }

    public Order(long idOrder, String paymentMethod, String state, String date) {
        this.idOrder = idOrder;
        this.paymentMethod = paymentMethod;
        this.state = state;
        this.date = date;
        this.prods = new ArrayList<>();
    }

    public void addProducts(Map<Product, Integer> m1){
        for(Product p : m1.keySet()){
            Order_Product o1 = new Order_Product(p, this, m1.get(p));
            prods.add(o1);
        }
        System.out.println(prods);
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
        for(Order_Product p : prods){
            total += Integer.parseInt(p.productId.getPrice()) * p.getUds(); 
        }
        return total;
    }
    public int getProductNumber() {
        return prods.size();
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
