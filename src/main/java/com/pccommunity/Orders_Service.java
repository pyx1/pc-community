package com.pccommunity;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class Orders_Service {
    private Map<Long, Order> allOrders = new ConcurrentHashMap<>();
    private AtomicLong lastId = new AtomicLong();

    public Order addOrder(Order o1, Map<Product, Integer> m1){
        long id = lastId.getAndIncrement();
        o1.setIdOrder(id);
        o1.addProducts(new ConcurrentHashMap<>(m1));
        allOrders.put(id, o1);
        return o1;
    }
    public void assingClient(Order o1, Customer client){
        o1.assingClient(client);
    }

    public Collection<Order> getOrdersByClient(Customer c1){
       List<Order> orders = new ArrayList<Order>();
        for(Order o1 : allOrders.values()){
           if(o1.takeClient().equals(c1)) orders.add(o1);
       }
        return orders;
    }

    public Order updateOrder(long id){
        Order o1 = allOrders.get(id);
        o1.setState("Sent");
        return o1;
    }

    public Collection<Order> gettallOrders(){
        return allOrders.values();
    }

    public Order getOrderById(long id){
        return allOrders.get(id);
    }

}
