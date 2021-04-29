package com.pccommunity;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

@Service
public class Review_Service {
    private Map<Long, Review> allReviews = new ConcurrentHashMap<>();
    private AtomicLong lastId = new AtomicLong();

    public void addReview(Review r1, Customer c1, Product p1){
        long id = lastId.getAndIncrement();
        r1.setIdReview(id);
        r1.assingClient(c1);
        r1.assingProduct(p1);
        allReviews.put(id, r1);
    }
    public Review getReview(long id){
        return allReviews.get(id);
    }

    public Collection<Review> getReviewsFromProduct(Product p1){
        List<Review> c1 = new ArrayList<Review>();
        for(Review tmp : allReviews.values()){
            if(tmp.takeProduct().equals(p1))c1.add(tmp);
        }
        return c1;
    }

    public Collection<Review> getReviewsFromClient(Customer c1){
        List<Review> reviews = new ArrayList<Review>();
        for(Review tmp : allReviews.values()){
            if(tmp.takeClient().equals(c1))reviews.add(tmp);
        }
        return reviews;
    }

    public int starsAverage(Product p1){
        int counter = 0;
        int sum = 0;
        for(Review tmp : allReviews.values()){
            if(tmp.takeProduct().equals(p1)){
                sum+=tmp.getStars();
                counter++;
            }
        }
        return sum/counter;
    }
}
