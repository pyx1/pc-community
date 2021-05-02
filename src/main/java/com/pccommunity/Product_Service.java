package com.pccommunity;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Product_Service {

    @Autowired
    private Product_Repository product_Repository;
    private List<Product> highlighted = new ArrayList<Product>();
    private AtomicLong lastId = new AtomicLong();
    
    public Map<Long, Product> getProducts(){
        Map <Long, Product> n1 = new ConcurrentHashMap<>();
        List<Product> q1 = product_Repository.findAll();
        for(Product p1 : q1){
            n1.put(p1.getidProduct(), p1);
        }
        return n1;
    }
    public Product getProduct(long id){
        return product_Repository.getOne(id);
    }

    public List<Product> getProductsFiltered(String a){
        List<Product> l = product_Repository.findByCategory(a);
        return l;
    }

    public List<Product> searchProduct(String str){
        List<Product> l = product_Repository.findByName(str);
        return l;
    }

    public boolean addHighlighted(long id){
        if(highlighted.size() < 3){
            highlighted.add(product_Repository.getOne(id));
            product_Repository.getOne(id).adjustHighlighted("Highlighted");
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
        product_Repository.save(p1);
    }

    public List<Product> getProdsByList(List<Long> ids){
        List<Product> products= product_Repository.findAllById(ids);
        return products;
    }

    public void reduceStock(long id, int s){
        Product p1 = product_Repository.getOne(id);
        p1.setStock(p1.getStock() - s);
    }
    @Transactional
    public Product deleteProduct(long id){
        Product p1 = product_Repository.getOne(id);
        product_Repository.delete(p1);
        return p1;
    }


}
