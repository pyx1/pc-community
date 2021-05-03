package com.pccommunity;

import java.util.*;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping("/admin")
public class Admin_Controller {

    @Autowired
    Product_Service product_Service;
    @Autowired
    Client_Service client_Service;
    @Autowired
    Orders_Service orders_Service;

    @GetMapping("/")
    public String main(Model model){
        Collection<Product> lProductos = product_Service.getProducts().values();
		model.addAttribute("productos", lProductos);
        return "admin/index";
    }
    @GetMapping("/newproduct")
    public String createProduct(){
        return "admin/new-product";
    }
    @GetMapping("/profiles")
    public String profiles(Model model){
        model.addAttribute("clients", client_Service.getallClients());
        return "admin/profiles";
    }
    @PostMapping("/profile/delete/{id}")
    public String deleteClient(@PathVariable long id){
        client_Service.deleteClient(id);
        return "redirect:/admin/profiles";
    }

    @GetMapping("/orders")
    public String orders(Model model){
        model.addAttribute("orders", orders_Service.gettallOrders());
        return "admin/orders";
    }
    @PutMapping("/order/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable long id){
        return new ResponseEntity<>(orders_Service.updateOrder(id), HttpStatus.OK);
    }
    @PostMapping("/productos")
	@ResponseStatus(HttpStatus.CREATED)
    @Transactional
	public ResponseEntity<Product> addProduct(@RequestBody Product p1){
		product_Service.createProduct(p1);
		return new ResponseEntity<>(p1, HttpStatus.OK);
	}
    @PostMapping("/product/delete/{id}")
    @Transactional
    public String deleteProduct(@PathVariable long id){
        product_Service.deleteProduct(id);
        return "redirect:/admin/";
    }
    @PostMapping("/product/highlight/{id}")
    public String highlightProduct(@PathVariable long id){
        if(product_Service.addHighlighted(id)) return "redirect:/admin/";
        else{
            System.out.println("[!!]LISTA LLENA");
            throw new ResponseStatusException(HttpStatus.valueOf(500));

        } 
    }
    @DeleteMapping("/product/highlight/{id}")
    public String rhighlightProduct(@PathVariable long id){
        product_Service.removeHighLight(id);
        return "redirect:/admin/";
    }

}
