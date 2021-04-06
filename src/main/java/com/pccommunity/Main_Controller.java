package com.pccommunity;


import java.util.*;

/* Imports */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

/* Clases */

@Controller
public class Main_Controller {

	@Autowired
	Product_Service product_Service;
	@Autowired
	Client_Service client_Service;
	@Autowired
	Orders_Service orders_Service;
	@Autowired
	Review_Service review_Service;

	
	
	@GetMapping("/")
	public String index(Model model){
		model.addAttribute("highlight", product_Service.getHighlighted());
		return "index";
	}
	@GetMapping("/catalogo")
	public String catalogo(Model model) {
		model.addAttribute("productos", product_Service.getProducts().values());
		return "catalogo";
	}

	@GetMapping("/catalogo/{categoria}")
	public String filteredcatalog(Model model, @PathVariable String categoria){
		if(categoria.equals("componentes") || categoria.equals("perifericos") || categoria.equals("monitores")){
			model.addAttribute("productos", product_Service.getProductsFiltered(categoria));
			return "catalogo";
		}else{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
	}

	@GetMapping("/profile")
	public String profile(Model model) {
		model.addAttribute("client", client_Service.getFirstClient());
		model.addAttribute("orders", orders_Service.getOrdersByClient(client_Service.getFirstClient()));
		model.addAttribute("reviews", review_Service.getReviewsFromClient(client_Service.getFirstClient()));
		return "profile";
	}
	@PutMapping("/profile/{id}")
	public ResponseEntity<Customer> profileedit(Model model,@PathVariable long id, @RequestBody New_Client nc2) {
		Customer c1 = client_Service.getFirstClient();
		Customer c2 = new Customer(nc2);
		c1.setName(c2.getName());
		c1.setSurname(c2.getSurname());
		c1.setDirection(c2.getDirection());
		c1.setPhone(c2.getPhone());
		c1.setPassword(c2.getPassword());
		client_Service.updateUser(id,c1);
		System.out.println(client_Service.getallClients());
		return new ResponseEntity<>(c1, HttpStatus.OK);
	}

	@GetMapping("/cart")
	public String cart(Model model) {
		model.addAttribute("carrito", client_Service.getFirstClient().getallCart().keySet());
		model.addAttribute("unidades", client_Service.getFirstClient().getallCart().values());
		return "cart";
	}
	

	@PostMapping("/cart")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Map<Product, Integer>> createProducto(@RequestBody List<String> pet){
		int uds = Integer.parseInt(pet.get(1));
		long id = Long.parseLong(pet.get(0));
		Product p1 = product_Service.getProduct(id);
		client_Service.getFirstClient().addToCart(p1, uds);
		product_Service.reduceStock(id, uds);
		return new ResponseEntity<>(client_Service.getFirstClient().getallCart(), HttpStatus.OK);

	}
	@PostMapping("/complete")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Order> completeOrder(@RequestBody Order order1){
		orders_Service.assingClient(order1, client_Service.getFirstClient());
		orders_Service.addOrder(order1, client_Service.getFirstClient().getallCart());
		client_Service.getFirstClient().cleanCart();
		return new ResponseEntity<>(order1, HttpStatus.OK);
	}

	@GetMapping("/producto/{id}")
	public String product(Model model, @PathVariable long id) {
		if(product_Service.getProducts().containsKey(id)){
			Product producto = product_Service.getProduct(id);
			model.addAttribute("producto", producto);
			model.addAttribute("carrito", client_Service.getFirstClient().getallCart().keySet());
			model.addAttribute("reviews", review_Service.getReviewsFromProduct(producto));
			return "product";
		}else{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
	}
	@PostMapping("/producto/{id}")
	public ResponseEntity<Review> newProductoReview(Model model, @PathVariable long id, @RequestBody Review r1) {
		Product p1 = product_Service.getProduct(id);
		review_Service.addReview(r1, client_Service.getFirstClient(), p1);
		p1.adjustStars(review_Service.starsAverage(p1));
		return new ResponseEntity<>(r1, HttpStatus.OK);
	}
	
	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}
	@PostMapping("/login") //It should return the session cookie, but thats for the future
    @ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Boolean> loginUser(@RequestBody List<String> data){
        return new ResponseEntity<>(client_Service.loginClient(data.get(0), data.get(1)), HttpStatus.OK);
    }
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Customer> registerUser(@RequestBody New_Client c1){
		Customer nCliente = client_Service.createUser(new Customer(c1));
		System.out.println(nCliente);
		return new ResponseEntity<>(nCliente, HttpStatus.OK);
	}
	
	/*@GetMapping("/help")
	public String help(Model model) {
		return "help";
	}*/

	
	
}