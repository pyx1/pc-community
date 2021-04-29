<<<<<<< HEAD
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
    private EntityManager eManager;
    private Map<Customer, Map<Product, Integer>> lclients = new ConcurrentHashMap<>();


    public Boolean loginClient(String email, String pass){
        try{
            TypedQuery<Customer> query = eManager.createNamedQuery("Customer.getByEmail", Customer.class)
            .setParameter("email", email)
            .setMaxResults(1);
            Customer c = query.getSingleResult();
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
        eManager.persist(c1);
        System.out.println("[*]Usuario creado: " + c1);
        return c1;
    }

    public Customer getClient(long id){
        List<Customer> l1 = eManager.createQuery("SELECT c FROM Customer c").getResultList(); 
        for(Customer c : l1){
            if(c.equalsId(id)) return c;
        }
        return null;
    }
    public Customer getLoggedClient(long id){
        for(Customer c : lclients.keySet()){
            if(c.equalsId(id)) return c;
        }
        return null;
    }

    public List<Customer> getallClients(){
        List<Customer> l1 = eManager.createQuery("SELECT c FROM Customer c").getResultList();
        return l1;
    }
     //Falta el updat
     /*
    public void updateUser(long id, Customer c1){
        Customer c  = (Customer)eManager.createQuery("SELECT c FROM Customer c WHERE id = '"+ id +"'").getSingleResult();
        String query = "";
        

    }*/
    @Transactional
    public Customer deleteClient(long id){
        List<Customer> l1 = eManager.createQuery("SELECT c FROM Customer c").getResultList();
        for(Customer c : l1){
            if(c.equalsId(id)){
                eManager.remove(c);
                return c;
            }
        }
        return null;
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
            if(c.equalsId(idCustomer)){
                lclients.get(c).put(p1, n);
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
        return null;
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
=======
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
>>>>>>> 74a060ef41211405c3b2d9186dd4e8562e6cf7cf
