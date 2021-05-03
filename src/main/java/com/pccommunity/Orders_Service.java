package com.pccommunity;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Orders_Service {

    @Autowired
    private Order_Repository order_Repository;
    @Autowired
    private Order_Product_Repository opr_Repository;
    private AtomicLong lastId = new AtomicLong();

    public Order addOrder(Order o1, Map<Product, Integer> m1){
        long id = lastId.getAndIncrement();
        o1.setIdOrder(id);
        order_Repository.saveAndFlush(o1);
        addProducts(m1, o1);
        order_Repository.saveAndFlush(o1);
        return o1;
    }
    public void assingClient(Order o1, Customer client){
        o1.assingClient(client);
    }
    @Transactional
    public void addProducts(Map<Product, Integer> m1, Order o){
        for(Product p : m1.keySet()){
            Order_Product o1 = new Order_Product(p, o, m1.get(p));
            opr_Repository.save(o1);
        }
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
