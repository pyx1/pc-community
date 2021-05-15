package com.pccommunity;

import javax.transaction.Transactional;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Client_Service {

    @Autowired
    private Client_Repository client_Repository;

    @Transactional
    public Customer createUser(Customer c1){
        client_Repository.saveAndFlush(c1);
        System.out.println("[*]Usuario creado: " + c1);
        return c1;
    }

    public Customer getClient(long id){
        Optional<Customer> c1 = client_Repository.findById(id); 
        if(c1.isPresent()){
            return c1.get();
        }
        else{
            return null;
        }
    }

    public void makeAdmin(long id){
        Customer c1 = getClient(id);
        if(c1.getRoles().contains("ROLE_ADMIN")){
            c1.getRoles().remove("ROLE_ADMIN");
        }
        else{
            c1.makeAdmin();
        }
        client_Repository.saveAndFlush(c1);
    }

    public Customer getClientByEmail(String email){
        Customer c1 = client_Repository.findByEmail(email);
        if(c1 != null) return c1;
        else return null;
    }

    public List<Customer> getallClients(){
        List<Customer> l1 = client_Repository.findAll();
        return l1;
    }
     
    public void updateUser(long id, Customer c1){
        client_Repository.saveAndFlush(c1);
    }
    public Customer deleteClient(long id){
        Customer c = client_Repository.getOne(id);
        client_Repository.delete(c);
        return c;
    }

}
