package com.pccommunity;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.swing.plaf.synth.SynthTextAreaUI;
import javax.transaction.Transactional;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Client_Service {

    @Autowired
    private Client_Repository client_Repository;
    private Map<Customer, Map<Product, Integer>> lclients = new ConcurrentHashMap<>();

    public Boolean loginClient(String email, String pass){
        try{
            Customer c = client_Repository.findByEmail(email);
            if(c.getPassword().equals(pass)){
                lclients.put(c, new ConcurrentHashMap<>());
                return true;
            }
            else return false;
        }
        catch(Exception e){
            System.out.println(e);
            if(e instanceof NoResultException){
                System.out.println("[!]No existe el usuario");
                return false;
            }
            else return false; 
        }
    }
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
    public Customer getLoggedClient(long id){
        for(Customer c : lclients.keySet()){
            if(c.equalsId(id)) return c;
        }
        return null;
    }

    public List<Customer> getallClients(){
        List<Customer> l1 = client_Repository.findAll();
        return l1;
    }
     //Falta el updat
     /*
    public void updateUser(long id, Customer c1){
        Customer c  = (Customer)eManager.createQuery("SELECT c FROM Customer c WHERE id = '"+ id +"'").getSingleResult();
        String query = "";
        

    }*/
    public Customer deleteClient(long id){
        Customer c = client_Repository.getOne(id);
        client_Repository.delete(c);
        return c;
    }
    
    public void cleanCart(long idCustomer){
        for(Customer c : lclients.keySet()){
            if(c.equalsId(idCustomer)){
                lclients.get(c).clear();
            }
        }

    }

    public void addToCart(long idCustomer, Product p1, int n){
        for(Customer c : lclients.keySet()){
            System.out.println("Cliente : " + c);
            if(c.equalsId(idCustomer)){
                System.out.println("Añadiendo a carrito");
                lclients.get(c).put(p1, n);
                System.out.println(lclients.get(c));
            }
        }
        
    }

    public void updateCartinOne(long idCustomer, Product p1){
        for(Customer c : lclients.keySet()){
            if(c.equalsId(idCustomer)){
                Map<Product, Integer> nc = lclients.get(c);
                nc.put(p1, nc.get(p1) + 1);
            }
        }
    }
    public void updateCartinOneLess(long idCustomer, Product p1){
        for(Customer c : lclients.keySet()){
            if(c.equalsId(idCustomer)){
                Map<Product, Integer> nc = lclients.get(c);
                nc.put(p1, nc.get(p1) - 1);
            }
        }
    }

    public void deleteProduct(long idCustomer, Product p1){
        for(Customer c : lclients.keySet()){
            if(c.equalsId(idCustomer)){
                Map<Product, Integer> nc = lclients.get(c);
                nc.remove(p1);
            }
        }
    }

    public Map<Product, Integer> getallCart(long idCustomer){
        for(Customer c : lclients.keySet()){
            if(c.equalsId(idCustomer)){
                Map<Product, Integer> nc = lclients.get(c);
                return nc;
            }
        }
        return new ConcurrentHashMap<>();
    }

    public int getCartProdNumber(long idCustomer, Product p1){
        for(Customer c : lclients.keySet()){
            if(c.equalsId(idCustomer)){
                Map<Product, Integer> nc = lclients.get(c);
                return nc.get(p1);
            }
        }
        return 0;
    }

    


}
