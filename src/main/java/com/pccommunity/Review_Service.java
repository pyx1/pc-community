package com.pccommunity;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Review_Service {
    
    @Autowired
    private Review_Repository review_Repository;
    private AtomicLong lastId = new AtomicLong();

    public void addReview(Review r1, Customer c1, Product p1){
        long id = lastId.getAndIncrement();
        r1.setIdReview(id);
        r1.assingProduct(p1);
        r1.setClient(c1);
        review_Repository.saveAndFlush(r1);
    }
    public Review getReview(long id){
        return review_Repository.getOne(id);
    }

    public Collection<Review> getReviewsFromProduct(Product p1){
        return review_Repository.findByProduct(p1);
    }

    public Collection<Review> getReviewsFromClient(Customer c1){
        return review_Repository.findByClient(c1);
    }

    public int starsAverage(Product p1){
        int counter = 0;
        int sum = 0;
        for(Review tmp : review_Repository.findByProduct(p1)){
            if(tmp.takeProduct().equals(p1)){
                sum+=tmp.getStars();
                counter++;
            }
        }
        return sum/counter;
    }
}
