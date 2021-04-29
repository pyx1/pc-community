package com.pccommunity;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Product_Service {

    @Autowired
    private EntityManager eManager; 
    private List<Product> highlighted = new ArrayList<Product>();
    private AtomicLong lastId = new AtomicLong();
    
    public Map<Long, Product> getProducts(){
        Map <Long, Product> n1 = new ConcurrentHashMap<>();
        List<Product> q1 = eManager.createQuery("SELECT c FROM Product c").getResultList();
        for(Product p1 : q1){
            n1.put(p1.getidProduct(), p1);
        }
        return n1;
    }
    @Transactional
    public Product getProduct(long id){
        return eManager.find(Product.class, id);
    }

    public List<Product> getProductsFiltered(String a){
        List<Product> l = eManager.createQuery("SELECT c FROM Product c").getResultList();
        List<Product> l1 = new ArrayList<>();
        for(Product p1 : l){
            if(p1.getCategoria().equals(a)) l1.add(p1);
        }
        return l1;
    }

    public List<Product> searchProduct(String str){
        List<Product> l = eManager.createQuery("SELECT c FROM Product c").getResultList();
        List<Product> l1 = new ArrayList<Product>();
        for(Product p1 : l){
            if(p1.getName().contains(str)) l1.add(p1);
        }
        return l1;
    }

    public boolean addHighlighted(long id){
        if(highlighted.size() < 3){
            highlighted.add(getProduct(id));
            getProduct(id).adjustHighlighted("Highlighted");
            return true;
        }else return false;
    }

    public Collection<Product> getHighlighted(){
        return highlighted;
    }

    public void removeHighLight(long id){
        highlighted.remove(getProduct(id)); 
        getProduct(id).adjustHighlighted("");
    }
    @Transactional
    public void createProduct(Product p1){
        eManager.persist(p1);
    }

    public List<Product> getProdsByList(List<Long> ids){
        List<Product> products= new ArrayList<Product>();
        for(int i = 0; i < ids.size(); i++){
            products.add(getProduct(i));
        }
        return products;
    }

    

    public void reduceStock(long id, int s){
        Product p1 = getProduct(id);
        p1.setStock(p1.getStock() - s);
    }
    @Transactional
    public Product deleteProduct(long id){
        Product p1 = getProduct(id);
        eManager.remove(p1);
        return p1;
    }


}
