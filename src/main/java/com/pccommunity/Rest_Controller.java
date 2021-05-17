
package com.pccommunity;

import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    private Logged_Customer lCustomer;
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

    @PostMapping("/products/search") //Search products
    public ResponseEntity<List<Product>> searchProduct(@RequestBody Map<String,Object> m1){
        String filter = m1.get("name").toString();
		int maxP = Integer.parseInt(m1.get("max").toString());
		int minP = Integer.parseInt(m1.get("min").toString());
		List<Product> l1 = product_Service.searchProduct(filter, maxP, minP);
		if(l1.size() > 0) return new ResponseEntity<>(l1, HttpStatus.OK);
		else return new ResponseEntity<>(HttpStatus.OK);
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
        Product p1 = product_Service.getProduct(id);
        product_Service.reduceStock(id, 1);
        lCustomer.addToCart(p1, 1);
        return lCustomer.getCart().keySet();
    }
    @GetMapping("/products/{id}/units") //Getting units from product in cart
    public ResponseEntity<Integer> howManyAPI(@PathVariable long id){
        return new ResponseEntity<>(lCustomer.getCartProdNumber(product_Service.getProduct(id)), HttpStatus.OK);
    }

    @GetMapping("/orders") //Getting orders from a client as client view
    public Collection<Order> getOrdersFromClientAPI(Authentication auth){
        return orders_Service.getOrdersByClient(client_Service.getClientByEmail(auth.getName()));
    }

    @GetMapping("/reviews") //Getting reviews from a client
    public Collection<Review> getReviewsFromClientAPI(Authentication auth){
        return review_Service.getReviewsFromClient(client_Service.getClientByEmail(auth.getName()));
    }

    @GetMapping("/cart") //Getting clients current cart
    public Map<Product, Integer> getCartProductsAPI(){
        return lCustomer.getCart();
    }

	@GetMapping("/cart/items")
	public ResponseEntity<Collection<Product>> cartitemsAPI() {
		return new ResponseEntity<>(lCustomer.getCart().keySet(), HttpStatus.OK);
	}
    @GetMapping("/cart/{id}")
    public ResponseEntity<Integer> getCartNumberAPI(@PathVariable long id){
        return new ResponseEntity<>(lCustomer.getCartProdNumber(product_Service.getProduct(id)), HttpStatus.OK);
    }
    @PostMapping("/cart/{id}") //Update cart in one
    public Map<Product, Integer> updateCartNumberAPI(@PathVariable long id){
        lCustomer.updateCartinOne(product_Service.getProduct(id));
        product_Service.reduceStock(id, 1);
        return lCustomer.getCart();
    } 
    @DeleteMapping("/cart/{id}") //Delete product from cart
    public Map<Product, Integer> deleteCartProdAPI(@PathVariable long id){
        int uds = lCustomer.getCartProdNumber(product_Service.getProduct(id));
        product_Service.incrementStock(id, uds);
        lCustomer.deleteProduct(product_Service.getProduct(id)); 
        return lCustomer.getCart();
    } 

    @PostMapping("/register") //Register endpoint
    public ResponseEntity<Customer> registerNewClientAPI(@RequestBody New_Client nc1){
        Customer c1 = new Customer(nc1);
        client_Service.createUser(c1);
        return new ResponseEntity<>(c1, HttpStatus.OK);

    }

    @GetMapping("/login") 
    public String loginNewClientAPI(HttpServletResponse response, Authentication auth){
        Customer c1 = client_Service.getClientByEmail(auth.getName());
        String info = "Esta loggeado como:\nLogged_Customer{\n" +
            "\tNombre: " + c1.getName() +",\n" +
            "\tApellidos: " + c1.getSurname() +",\n" +
            "\tEmail: " + c1.getEmail() +",\n" +
            "\tDireccion: " + c1.getDirection() + ",\n" + 
            "\tTelefono: " + c1.getPhone() + ",\n" + 
        "}";
        return info;
    }

    /* Admin Section */
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
    
}
