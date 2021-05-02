package com.pccommunity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Client_Repository extends JpaRepository<Customer, Long>{
    Customer findByEmail(String email);
}
