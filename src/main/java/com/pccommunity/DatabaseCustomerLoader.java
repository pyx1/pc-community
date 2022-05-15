package com.pccommunity;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseCustomerLoader {
    @Autowired
    private Client_Repository cRepository;

    @PostConstruct
    private void initDatabase() {
        if (cRepository.findByEmail("admin@admin.com") == null) {
            Customer ad = new Customer("Admin", "-", "admin@admin.com", "-", "admin123", "-");
            ad.makeAdmin();
            ad.makeSAdmin();
            cRepository.save(ad);
        }
    }
}
