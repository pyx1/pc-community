package com.pccommunity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class Logged_Customer {

    private Map<Product, Integer> cart = new ConcurrentHashMap<>();

    public void addToCart(Product p, int uds){
        Product inn = inCart(p);
        if(inn != null){
            this.cart.put(inn, this.cart.get(inn) + uds);
        }else{
            this.cart.put(p, uds);
        }
    }

    public Map<Product, Integer> getCart(){
        return this.cart;
    }

    public void deleteProduct(Product p){
        Product inn = inCart(p);
        if(inn != null){
            this.cart.remove(inn);
        }
    }

    public void cleanCart(){
        this.cart.clear();
    }

    public void updateCartinOne(Product p){
        Product inn = inCart(p);
        if(inn != null){
            this.cart.put(inn, this.cart.get(inn) + 1);
        }
    }
    public void updateCartinOneLess(Product p){
        Product inn = inCart(p);
        if(inn != null){
            this.cart.put(inn, this.cart.get(inn) - 1);
        }
    }

    public int getCartProdNumber(Product p){
        Product inn = inCart(p);
        return this.cart.get(inn);
    }

    public boolean containsCart(Product p){
        /* If is in cart returns true */
        Product inn = inCart(p);
        return inn != null;
    }

    private Product inCart(Product p1){
        for(Product p : this.cart.keySet()){
            if(p.getIdProduct() == p1.getIdProduct()) return p;
        }
        return null;
    }
    
}
