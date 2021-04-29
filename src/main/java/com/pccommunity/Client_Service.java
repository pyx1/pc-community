package com.pccommunity;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class Client_Service {

    private Map<Long, Customer> allclients = new ConcurrentHashMap<>();
    private AtomicLong lastId = new AtomicLong();

    public Customer createUser(Customer c1){
        long id = lastId.getAndIncrement();
        c1.setIdCustomer(id);
        allclients.put(id, c1);
        return c1;
    }
    public Customer getFirstClient(){
        long id = 0;
        return allclients.get(id);
    }
    public Customer getClient(long id){
        return allclients.get(id);
    }

    public Collection<Customer> getallClients(){
        return allclients.values();
    }

    public void updateUser(long id, Customer c1){
        allclients.put(id, c1);
    }

    public Customer deleteClient(long id){
        return allclients.remove(id);
    }

    public Boolean loginClient(String email, String pass){
        for(Customer c : allclients.values()){
            if(email.equals(c.getEmail())){
                if(pass.equals(c.getPassword())) return true;
            }
        }
        return false;

    }


}
