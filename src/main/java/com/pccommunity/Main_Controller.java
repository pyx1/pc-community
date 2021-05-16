package com.pccommunity;

import java.util.*;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

/* Imports */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	private Logged_Customer lCustomer;

	@Autowired
	private EntityManager eManager;
	@Autowired
	Product_Service product_Service;
	@Autowired
	Client_Service client_Service;
	@Autowired
	Orders_Service orders_Service;
	@Autowired
	Review_Service review_Service;

	@GetMapping("/")
	public String index(Model model, HttpServletRequest request) {
		model.addAttribute("logged", request.isUserInRole("USER"));
		model.addAttribute("admin", request.isUserInRole("ADMIN"));
		model.addAttribute("highlight", product_Service.getHighlighted());
		return "index";

	}

	@GetMapping("/catalogo")
	public String catalogo(Model model, HttpServletRequest request) {
		model.addAttribute("logged", request.isUserInRole("USER"));
		model.addAttribute("admin", request.isUserInRole("ADMIN"));
		model.addAttribute("productos", product_Service.getProducts().values());
		return "catalogo";
	}

	@PostMapping("/catalogo")
	public ResponseEntity<List<Product>> busqueda(@RequestBody Map<String, Object> m1) {
		String filter = m1.get("name").toString();
		int maxP = Integer.parseInt(m1.get("max").toString());
		int minP = Integer.parseInt(m1.get("min").toString());
		List<Product> l1 = product_Service.searchProduct(filter, maxP, minP);
		if(l1.size() > 0) return new ResponseEntity<>(l1, HttpStatus.OK);
		else return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/catalogo/{categoria}")
	public String filteredcatalog(Model model, @PathVariable String categoria, HttpServletRequest request) {
		if (categoria.equals("componentes") || categoria.equals("perifericos") || categoria.equals("monitores")) {
			model.addAttribute("logged", request.isUserInRole("USER"));
			model.addAttribute("admin", request.isUserInRole("ADMIN"));
			model.addAttribute("productos", product_Service.getProductsFiltered(categoria));
			return "catalogo";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/profile")
	public String profile(Model model, Authentication auth, HttpServletRequest request) {
		model.addAttribute("logged", request.isUserInRole("USER"));
		Customer client = client_Service.getClientByEmail(auth.getName());
		model.addAttribute("client", client);
		model.addAttribute("orders", orders_Service.getOrdersByClient(client));
		model.addAttribute("reviews", review_Service.getReviewsFromClient(client));
		model.addAttribute("admin", request.isUserInRole("ADMIN"));
		return "profile";
	}

	@PutMapping("/profile/{id}")
	public ResponseEntity<Customer> profileedit(Model model, @PathVariable long id, @RequestBody New_Client nc2, Authentication auth) {
		Customer c1 = client_Service.getClientByEmail(auth.getName());
		Customer c2 = new Customer(nc2);
		c1.setName(c2.getName());
		c1.setSurname(c2.getSurname());
		c1.setDirection(c2.getDirection());
		c1.setPhone(c2.getPhone());
		c1.setPassword(c2.getPassword());
		client_Service.updateUser(id, c1);
		return new ResponseEntity<>(c1, HttpStatus.OK);
	}

	@GetMapping("/cart")
	public String cart(Model model, HttpServletRequest request) {
		model.addAttribute("logged", request.isUserInRole("USER"));
		model.addAttribute("carrito", lCustomer.getCart().keySet());
		model.addAttribute("unidades", lCustomer.getCart().values());
		model.addAttribute("admin", request.isUserInRole("ADMIN"));
		return "cart";
	}

	@PostMapping("/cart")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Map<Product, Integer>> createProduct(@RequestBody List<String> pet) {
			int uds = Integer.parseInt(pet.get(1));
			long id = Long.parseLong(pet.get(0));
			Product p1 = product_Service.getProduct(id);
			product_Service.reduceStock(id, uds);
			lCustomer.addToCart(p1, uds);
			return new ResponseEntity<>(lCustomer.getCart(), HttpStatus.OK);
	}

	@DeleteMapping("/cart")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ResponseEntity<Map<Product, Integer>> deleteProduct(@RequestBody Long id) {
		Product p1 = product_Service.getProduct(id);
		product_Service.incrementStock(id, lCustomer.getCartProdNumber(p1));
		lCustomer.deleteProduct(p1);
		return new ResponseEntity<>(lCustomer.getCart(), HttpStatus.OK);

	}

	@PostMapping("/complete")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Order> completeOrder(@RequestBody Order order1, Authentication auth) {
			orders_Service.assingClient(order1, client_Service.getClientByEmail(auth.getName()));
			orders_Service.addOrder(order1, lCustomer.getCart());
			lCustomer.cleanCart();
			return new ResponseEntity<>(order1, HttpStatus.OK);
	}

	@GetMapping("/producto/{id}")
	public String product(Model model, @PathVariable long id, HttpServletRequest request) {
		if (eManager.find(Product.class, id) != null) {
			Product producto = product_Service.getProduct(id);
			CsrfToken token = (CsrfToken) request.getAttribute("_csrf"); 
			model.addAttribute("token", token.getToken());
			model.addAttribute("producto", producto);
			model.addAttribute("carrito", lCustomer.getCart().keySet());
			model.addAttribute("reviews", review_Service.getReviewsFromProduct(producto));
			model.addAttribute("admin", request.isUserInRole("ADMIN"));
			model.addAttribute("logged", request.isUserInRole("USER"));
			return "product";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping("/producto/{id}")
	public ResponseEntity<Review> newProductoReview(Model model, @PathVariable long id, @RequestBody Review r1, Authentication auth) {
		Product p1 = product_Service.getProduct(id);
		Customer c1 = client_Service.getClientByEmail(auth.getName());
		review_Service.addReview(r1, c1, p1);
		product_Service.adjustStars(p1, review_Service.starsAverage(p1));
		return new ResponseEntity<>(r1, HttpStatus.OK);
	}

	@GetMapping("/login")
	public String login(Model model, HttpServletRequest request) {
		return "login";
	}


	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Customer> registerUser(@RequestBody New_Client c1) {
		Customer nCliente = client_Service.createUser(new Customer(c1));
		System.out.println(nCliente);
		return new ResponseEntity<>(nCliente, HttpStatus.OK);
	}


}