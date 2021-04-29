package com.pccommunity;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Product_Service {

    private Map<Long, Product> allproducts = new ConcurrentHashMap<>();
    private List<Product> highlighted = new ArrayList<Product>();
    private AtomicLong lastId = new AtomicLong();
    
    public Map<Long, Product> getProducts(){
        return allproducts;
    }

    public List<Product> getProductsFiltered(String a){
        List<Product> l1 = new ArrayList<Product>();
        for(Product p1 : allproducts.values()){
            if(p1.getCategoria().equals(a)) l1.add(p1);
        }
        return l1;
    }

    public List<Product> searchProduct(String str){
        List<Product> l1 = new ArrayList<Product>();
        for(Product p1 : allproducts.values()){
            if(p1.getName().contains(str)) l1.add(p1);
        }
        return l1;
    }

    public boolean addHighlighted(long id){
        if(highlighted.size() < 3){
            highlighted.add(allproducts.get(id));
            allproducts.get(id).adjustHighlighted("Highlighted");
            return true;
        }else return false;
    }

    public Collection<Product> getHighlighted(){
        return highlighted;
    }

    public void removeHighLight(long id){
        highlighted.remove(allproducts.get(id)); 
        allproducts.get(id).adjustHighlighted("");
    }

    public void createProduct(Product p1){
        long id = lastId.incrementAndGet();
        p1.setidProduct(id);
        allproducts.put(id, p1);
    }

    public List<Product> getProdsByList(List<Long> ids){
        List<Product> products= new ArrayList<Product>();
        for(int i = 0; i < ids.size(); i++){
            products.add(allproducts.get(ids.get(i)));
        }
        return products;
    }

    public Product getProduct(long id){
        return allproducts.get(id);
    }

    public void reduceStock(long id, int s){
        Product p1 = allproducts.get(id);
        p1.setStock(p1.getStock() - s);
    }

    public Product deleteProduct(long id){
        return allproducts.remove(id);
    }


}
