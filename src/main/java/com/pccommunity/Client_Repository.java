package com.pccommunity;


import org.springframework.data.jpa.repository.JpaRepository;

public interface Client_Repository extends JpaRepository<Customer, Long>{
    Customer findByEmail(String email);
}
