/*
package com.pccommunity;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Rest_Controller {
    @Autowired
    Client_Service client_Service;
    @Autowired
    Orders_Service orders_Service;
    @Autowired
    Product_Service product_Service;
    @Autowired
    Review_Service review_Service;

    @GetMapping("/products") //All products
    public Collection<Product> getProductsAPI(){
        return product_Service.getProducts().values();       
    }
    @GetMapping("/products/category/{cat}") //Products by category
    public Collection<Product> getProductsByCategoryAPI(@PathVariable String cat){
        return product_Service.getProductsFiltered(cat);       
    }
    @GetMapping("/products/{id}") //Product by Id
    public ResponseEntity<Product> getProductAPI(@PathVariable long id){
        return new ResponseEntity<>(product_Service.getProduct(id), HttpStatus.OK);
    }

    @GetMapping("/products/search/{filter}") //Search products
    public ResponseEntity<List<Product>> searchProduct(@PathVariable String filter){
        return new ResponseEntity<>(product_Service.searchProduct(filter), HttpStatus.OK);
    }

    @GetMapping("/products/{id}/reviews") //Reviews from product
    public Collection<Review> getProdRevsAPI(@PathVariable long id){
        return review_Service.getReviewsFromProduct(product_Service.getProduct(id));
    }
    @GetMapping("/products/all/reviews/{id}") //Review from id
    public ResponseEntity<Review> getProdRevAPI(@PathVariable long id){
        return new ResponseEntity<>(review_Service.getReview(id), HttpStatus.OK);
    }

    @PostMapping("/products/{id}") //Adding item by id to cart and returning products in cart, not units
    public Collection<Product> addToCartAPI(@PathVariable long id){
        client_Service.getFirstClient().addToCart(product_Service.getProduct(id), 1);
        return client_Service.getFirstClient().getallCart().keySet();
    }
    @GetMapping("/products/{id}/units") //Getting units from product in cart
    public ResponseEntity<Integer> howManyAPI(@PathVariable long id){
        return new ResponseEntity<>(client_Service.getFirstClient().getCartProdNumber(product_Service.getProduct(id)), HttpStatus.OK);
    }

    @GetMapping("/orders") //Getting orders from a client as client view
    public Collection<Order> getOrdersFromClientAPI(){
        return orders_Service.getOrdersByClient(client_Service.getFirstClient());
    }

    @GetMapping("/reviews") //Getting reviews from a client
    public Collection<Review> getReviewsFromClientAPI(){
        return review_Service.getReviewsFromClient(client_Service.getFirstClient());
    }

    @GetMapping("/cart") //Getting clients current cart
    public Map<Product, Integer> getCartProductsAPI(){
        return client_Service.getFirstClient().getallCart();
    }

	@GetMapping("/cart/items")
	public ResponseEntity<Collection<Product>> cartitemsAPI() {
		return new ResponseEntity<>(client_Service.getFirstClient().getallCart().keySet(), HttpStatus.OK);
	}
    @GetMapping("/cart/{id}")
    public ResponseEntity<Integer> getCartNumberAPI(@PathVariable long id){
        return new ResponseEntity<>(client_Service.getFirstClient().getCartProdNumber(product_Service.getProduct(id)), HttpStatus.OK);
    }
    @PostMapping("/cart/{id}") //Update cart in one
    public Map<Product, Integer> updateCartNumberAPI(@PathVariable long id){
        client_Service.getFirstClient().updateCartinOne(product_Service.getProduct(id));
        return client_Service.getFirstClient().getallCart();
    } 
    @DeleteMapping("/cart/{id}") //Delete product from cart
    public Map<Product, Integer> deleteCartProdAPI(@PathVariable long id){
        client_Service.getFirstClient().deleteProduct(product_Service.getProduct(id));
        return client_Service.getFirstClient().getallCart();
    } 

    @PostMapping("/register") //Register endpoint
    public ResponseEntity<Customer> registerNewClientAPI(@RequestBody New_Client nc1){
        Customer c1 = new Customer(nc1);
        client_Service.createUser(c1);
        return new ResponseEntity<>(c1, HttpStatus.OK);

    }
    @PostMapping("/login") //It should return the session cookie, but thats for the future
    public Boolean loginNewClientAPI(@RequestBody List<String> data){
        return client_Service.loginClient(data.get(0), data.get(1));
    }

    /* Admin Section */
    /*
    @PostMapping("/admin/product")
    public ResponseEntity<Product> registerNewProductAPI(@RequestBody Product p1){
        product_Service.createProduct(p1);
        return new ResponseEntity<>(p1, HttpStatus.OK);
    }
    @GetMapping("/admin/product/{id}")
    public ResponseEntity<Product> getProductAdminAPI(@PathVariable long id){
        return new ResponseEntity<>(product_Service.getProduct(id), HttpStatus.OK);
    }
    @DeleteMapping("/admin/product/{id}")
    public ResponseEntity<Product> deleteProductAPI(@PathVariable long id){
        return new ResponseEntity<>(product_Service.deleteProduct(id), HttpStatus.OK);
    }
    @PutMapping("/admin/product/{id}/{number}") //Update stock in the amount "number"
    public ResponseEntity<Product> updateStockAPI(@PathVariable long id, @PathVariable int number){
        Product p1 = product_Service.getProduct(id);
        p1.setStock(p1.getStock() + number);
        return new ResponseEntity<>(p1, HttpStatus.OK);
    }

    @GetMapping("/admin/client/{id}")
    public ResponseEntity<Customer> getClientAPI(@PathVariable long id){
        return new ResponseEntity<>(client_Service.getClient(id), HttpStatus.OK);
    }

    @DeleteMapping("/admin/client/{id}")
    public ResponseEntity<Customer> deleteClientAPI(@PathVariable long id){
        return new ResponseEntity<>(client_Service.deleteClient(id), HttpStatus.OK);
    }
    
    @GetMapping("/admin/client/{id}/reviews")
    public Collection<Review> getClientReviewsAPI(@PathVariable long id){
        return review_Service.getReviewsFromClient(client_Service.getClient(id));
    }
    @GetMapping("/admin/client/{id}/reviews/amount") //Get number of reviews by client
    public int getClientNReviewsAPI(@PathVariable long id){
        return review_Service.getReviewsFromClient(client_Service.getClient(id)).size();
    }
    @GetMapping("/admin/client/{id}/orders/amount") //Get number of reviews by client
    public int getClientNOrdersAPI(@PathVariable long id){
        return orders_Service.getOrdersByClient(client_Service.getClient(id)).size();
    }

    @GetMapping("/admin/orders")
    public Collection<Order> getOrdersAPI(){
        return orders_Service.gettallOrders();
    }

    @GetMapping("/admin/orders/{id}")
    public ResponseEntity<Order> getOrderAPI(@PathVariable long id){
        return new ResponseEntity<>(orders_Service.getOrderById(id), HttpStatus.OK);
    }
    @PostMapping("/admin/orders/{id}")
    public ResponseEntity<Order> updateOrderAPI(@PathVariable long id){
        return new ResponseEntity<>(orders_Service.updateOrder(id), HttpStatus.OK);
    }
    
}*/
