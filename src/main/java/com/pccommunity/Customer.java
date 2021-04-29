package com.pccommunity;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long idCustomer;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String password;
    private String direction;

    public Customer() {
    }
    public Customer(New_Client nc){
        this.name = nc.getName();
        this.surname = nc.getSurname();
        this.email = nc.getEmail();
        this.phone = nc.getPhone();
        this.password = nc.getPassword();
        this.direction = nc.getDirection();
    }
    public Customer(Customer c1){
        this.idCustomer = c1.idCustomer;
        this.name = c1.name;
        this.surname = c1.surname;
        this.email = c1.email;
        this.phone = c1.phone;
        this.password = c1.password;
        this.direction = c1.direction;
    }

    public Customer(long id, String name, String surname, String email, String phone, String password, String direction){
        this.idCustomer = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.direction = direction;
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
        this.password = password;
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