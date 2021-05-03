package com.pccommunity;

import java.util.*;

import javax.persistence.EntityManager;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/* Imports */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
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
	public String index(Model model) {
		return "index";

	}

	@GetMapping("/catalogo")
	public String catalogo(@CookieValue(name = "sessionid", required = false) String sessionid, Model model) {
		model.addAttribute("productos", product_Service.getProducts().values());
		return "catalogo";
	}

	@PostMapping("/catalogo")
	public ResponseEntity<List<Product>> busqueda(@RequestBody String filter) {
		return new ResponseEntity<>(product_Service.searchProduct(filter), HttpStatus.OK);
	}

	@GetMapping("/catalogo/{categoria}")
	public String filteredcatalog(Model model, @PathVariable String categoria) {
		if (categoria.equals("componentes") || categoria.equals("perifericos") || categoria.equals("monitores")) {
			model.addAttribute("productos", product_Service.getProductsFiltered(categoria));
			return "catalogo";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/profile")
	public String profile(@CookieValue(name = "sessionid", required = false) String sessionid, Model model) {
		if(sessionid != null){
			model.addAttribute("client", client_Service.getClient(1));
			model.addAttribute("orders", orders_Service.getOrdersByClient(client_Service.getClient(1)));
			model.addAttribute("reviews", review_Service.getReviewsFromClient(client_Service.getClient(1)));
			return "profile";
		}else throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
	}
	/*
	 * @PutMapping("/profile/{id}") public ResponseEntity<Customer>
	 * profileedit(Model model,@PathVariable long id, @RequestBody New_Client nc2) {
	 * Customer c1 = client_Service.getFirstClient(); Customer c2 = new
	 * Customer(nc2); c1.setName(c2.getName()); c1.setSurname(c2.getSurname());
	 * c1.setDirection(c2.getDirection()); c1.setPhone(c2.getPhone());
	 * c1.setPassword(c2.getPassword()); client_Service.updateUser(id,c1);
	 * System.out.println(client_Service.getallClients()); return new
	 * ResponseEntity<>(c1, HttpStatus.OK); }
	 */

	@GetMapping("/cart")
	public String cart(@CookieValue(name = "sessionid", required = false) String sessionid, Model model) {
		for (Product p : client_Service.getallCart(1).keySet()) {
			System.out.println(p.getIdProduct());
		}
		model.addAttribute("carrito", client_Service.getallCart(1).keySet());
		model.addAttribute("unidades", client_Service.getallCart(1).values());
		return "cart";
	}

	@PostMapping("/cart")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Map<Product, Integer>> createProduct(@CookieValue(name = "sessionid", required = false) String sessionid, @RequestBody List<String> pet) {
		if (sessionid != null) {
			int uds = Integer.parseInt(pet.get(1));
			long id = Long.parseLong(pet.get(0));
			Product p1 = product_Service.getProduct(id);
			product_Service.reduceStock(id, uds);
			client_Service.addToCart(1, p1, uds);
			return new ResponseEntity<>(client_Service.getallCart(1), HttpStatus.OK);
		}else{
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}

	}

	@DeleteMapping("/cart")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ResponseEntity<Map<Product, Integer>> deleteProduct(@RequestBody Long id) {
		Product p1 = product_Service.getProduct(id);
		client_Service.deleteProduct(1, p1);
		return new ResponseEntity<>(client_Service.getallCart(1), HttpStatus.OK);

	}

	@PostMapping("/complete")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Order> completeOrder(@CookieValue(name = "sessionid", required = false) String sessionid,
			@RequestBody Order order1) {
		if (sessionid != null) {
			orders_Service.assingClient(order1, client_Service.getLoggedClient(1));
			orders_Service.addOrder(order1, client_Service.getallCart(1));
			client_Service.cleanCart(1);
			return new ResponseEntity<>(order1, HttpStatus.OK);
		} else
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("/producto/{id}")
	public String product(Model model, @PathVariable long id) {
		if (eManager.find(Product.class, id) != null) {
			Product producto = product_Service.getProduct(id);
			model.addAttribute("producto", producto);
			model.addAttribute("carrito", client_Service.getallCart(1).keySet());
			model.addAttribute("reviews", review_Service.getReviewsFromProduct(producto));
			return "product";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping("/producto/{id}")
	public ResponseEntity<Review> newProductoReview(Model model, @PathVariable long id, @RequestBody Review r1) {
		Product p1 = product_Service.getProduct(id);
		review_Service.addReview(r1, client_Service.getClient(1), p1);
		product_Service.adjustStars(p1, review_Service.starsAverage(p1));
		return new ResponseEntity<>(r1, HttpStatus.OK);
	}

	@GetMapping("/login")
	public String login(@CookieValue(name = "sessionid", required = false) String sessionid, Model model) {
		if (sessionid == null) {
			return "login";
		} else {
			return "redirect:/";
		}
	}

	@PostMapping("/login") // It should return the session cookie, but thats for the future
	@ResponseStatus(HttpStatus.CREATED)
	public String loginUser(@RequestBody List<String> data, HttpServletResponse response) {
		String consul = client_Service.loginClient(data.get(0), data.get(1));
		if (consul == null) {
			throw new ResponseStatusException(HttpStatus.valueOf(500));
		} else if (consul.equals("UserNotFound"))
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		else {
			Cookie sescookie = new Cookie("sessionid", consul);
			sescookie.setPath("/");
			sescookie.setSecure(true);
			response.addCookie(sescookie);
			return "redirect:/";
		}
	}

	@PostMapping("/logout")
	public String logoutUser(@CookieValue(name = "sessionid", required = false) String sessionid,
			HttpServletResponse response) {
		if (sessionid != null) {
			Cookie sescookie = new Cookie("sessionid", "");
			sescookie.setMaxAge(0);
			sescookie.setPath("/");
			sescookie.setSecure(true);
			response.addCookie(sescookie);
		}
		return "redirect:/login/";
	}

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Customer> registerUser(@RequestBody New_Client c1) {
		Customer nCliente = client_Service.createUser(new Customer(c1));
		System.out.println(nCliente);
		return new ResponseEntity<>(nCliente, HttpStatus.OK);
	}

}