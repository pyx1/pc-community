package com.pccommunity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Review_Repository extends JpaRepository<Review, Long> {
    List<Review> findByProduct(Product p);
    List<Review> findByClient(Customer c);
    
}
