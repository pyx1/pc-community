package com.pccommunity;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Orders_Service {

    @Autowired
    private Order_Repository order_Repository;
    private AtomicLong lastId = new AtomicLong();

    public Order addOrder(Order o1, Map<Product, Integer> m1){
        long id = lastId.getAndIncrement();
        o1.setIdOrder(id);
        o1.addProducts(m1);
        order_Repository.saveAndFlush(o1);
        return o1;
    }
    public void assingClient(Order o1, Customer client){
        o1.assingClient(client);
    }

    public Collection<Order> getOrdersByClient(Customer c1){
        return order_Repository.findByClient(c1);
    }

    public Order updateOrder(long id){
        Order o1 = order_Repository.getOne(id);
        o1.setState("Sent");
        order_Repository.save(o1);
        return o1;
    }

    public Collection<Order> gettallOrders(){
        return order_Repository.findAll();
    }

    public Order getOrderById(long id){
        return order_Repository.getOne(id);
    }

}
