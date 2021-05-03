package com.pccommunity;

import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Client_Service {

    @Autowired
    private Client_Repository client_Repository;
    private Map<Customer, Map<Product, Integer>> lclients = new ConcurrentHashMap<>();
    private Map<String, Customer> sessions = new ConcurrentHashMap<>();

    public String loginClient(String email, String pass){
        try{
            Customer c = client_Repository.findByEmail(email);
            if(c.getPassword().equals(pass)){
                String token = generateBase64(c.getEmail(), c.getIdCustomer());
                sessions.put(token, c);
                lclients.put(c, new ConcurrentHashMap<>());
                return token;
            }
            else return null;
        }
        catch(Exception e){
            System.out.println(e);
            if(e instanceof NoResultException){
                System.out.println("[!]No existe el email");
                return "UserNotFound";
            }
            else return null; 
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
     
    public void updateUser(long id, Customer c1){
        client_Repository.saveAndFlush(c1);
    }
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
        boolean con = false;
        Product pcon = new Product();
        for(Customer c : lclients.keySet()){
            if(c.equalsId(idCustomer)){
                for(Product ph : lclients.get(c).keySet()){
                    if(ph.equalsId(p1)){
                        con = true;
                        pcon = ph;
                    } 
                }
                if(!con) lclients.get(c).put(p1, n);
                else lclients.get(c).put(pcon, lclients.get(c).get(pcon) + n);
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
                for(Product ph : nc.keySet()){
                    if(p1.equalsId(ph)) nc.remove(ph);
                }
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
                return nc.get(containsCart(nc, p1));
            }
        }
        return 0;
    }

    public Product containsCart(Map<Product, Integer> m1, Product p){
        for(Product ph : m1.keySet()){
            if(ph.equalsId(p)) return ph;
        }
        return null;
    }

    public String generateBase64(String email, long id){
        String str =  "{id:"+ id +"email:" + email +"}"; 
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    


}
