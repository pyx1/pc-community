package com.pccommunity;

import java.util.*;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class Review_Service {
    
    @Autowired
    private Review_Repository review_Repository;

    public void addReview(Review r1, Customer c1, Product p1){
        PolicyFactory sanitizer = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS).and(Sanitizers.FORMATTING).and(Sanitizers.IMAGES).and(Sanitizers.STYLES)
        .and(Sanitizers.TABLES).and(Sanitizers.LINKS); //The library implemented in js already sanitize but just in case
        r1.setMessage(sanitizer.sanitize(r1.getMessage()));
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
        int counter = 1;
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
