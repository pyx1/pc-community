package com.pccommunity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;



public interface Product_Repository extends JpaRepository<Product, Long>{
    List<Product> findByName(String name);
    List<Product> findByNameStartsWith(String name);
    List<Product> findByCategory(String category);
    List<Product> findByHighlighted(String str);
    List<Product> findByNameContainingAndPriceBetween(String name, int priceL, int priceM);
}
