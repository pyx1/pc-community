package com.pccommunity;

import java.util.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class Customer{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long idCustomer;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String password;
    private String direction;

    @JsonIgnore
    @OneToMany(mappedBy = "client")
    private Set<Order> orders;
    @JsonIgnore
    @OneToMany(mappedBy="client")
    private Set<Review> reviews;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    public Customer() {
    }
    public Customer(New_Client nc){
        this.name = nc.getName();
        this.surname = nc.getSurname();
        this.email = nc.getEmail();
        this.phone = nc.getPhone();
        this.password = new BCryptPasswordEncoder().encode(nc.getPassword());
        this.direction = nc.getDirection();
        this.roles = new ArrayList<>();
        this.roles.add("ROLE_USER");
    }
    public Customer(Customer c1){
        this.idCustomer = c1.idCustomer;
        this.name = c1.name;
        this.surname = c1.surname;
        this.email = c1.email;
        this.phone = c1.phone;
        this.password = new BCryptPasswordEncoder().encode(c1.password);
        this.direction = c1.direction;
        this.roles = new ArrayList<>();
        this.roles.add("ROLE_USER");
    }

    public Customer(long id, String name, String surname, String email, String phone, String password, String direction){
        this.idCustomer = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.password = new BCryptPasswordEncoder().encode(password);
        this.direction = direction;
        this.roles = new ArrayList<>();
        this.roles.add("ROLE_USER");
    }
    public Customer(String name, String surname, String email, String phone, String password, String direction){
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.password = new BCryptPasswordEncoder().encode(password);
        this.direction = direction;
        this.roles = new ArrayList<>();
        this.roles.add("ROLE_USER");
    }
    

    public void setIdCustomer(long idCustomer) {
        this.idCustomer = idCustomer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public long getIdCustomer() {
        return idCustomer;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getDirection() {
        return direction;
    }

    public List<String> getRoles(){
        return this.roles;
    }

    public boolean getAdmin(){
        for(String role : roles){
            if(role.equals("ROLE_ADMIN"))return true;
        }
        return false;
    }


    public void makeAdmin(){
        this.roles.add("ROLE_ADMIN");
    }
    public void makeSAdmin(){
        this.roles.add("ROLE_SADMIN");
    }
    @JsonIgnore
    public int getOrdersNumber(){
        return this.orders.size();
    }
    @JsonIgnore
    public int getReviewsNumber(){
        return this.reviews.size();
    }

    public boolean equalsId(long id){
        if(this.idCustomer == id) return true;
        else return false;

    }

    @Override
    public String toString() {
        return "Customer{" +
               "idCustomer=" + idCustomer +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", direction='" + direction + '\'' +
                '}';
    }
}