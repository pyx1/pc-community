package com.pccommunity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Order_Repository extends JpaRepository<Order, Long>{
    List<Order> findByClient(Customer client);
}
