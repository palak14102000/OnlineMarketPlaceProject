package com.marketplace.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.marketplace.dao.OrderRepository;
import com.marketplace.dao.ProductRespository;
import com.marketplace.dao.UserRepository;
import com.marketplace.dto.ProductDto;
import com.marketplace.entities.Order;
import com.marketplace.entities.Products;
import com.marketplace.entities.User;

@RestController
public class HomeController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProductRespository productRepository;
	@Autowired
	private OrderRepository orderRepository ;
	
	public static String uploadDirectory="C:/Users/palak/Documents/workspace-spring-tool-suite-4-4.13.1.RELEASE/onlineMarketPlaceProject/src/main/resources/static/img";

	//API for adding a user
	@PostMapping("/save")
	public String storeData(@RequestBody User userdetails) {
		userRepository.save(userdetails);
		return "user saved";
	}
	
	//API for listing all the users
	@GetMapping(path="/fetch")
	public List<User> getData() {
		List<User> users = userRepository.findAll();
		
		return users;
	}
	
	//API for posting a product for sale
	@PostMapping(
		value =	"/saveProduct",
		consumes = "multipart/form-data")
	public @ResponseBody String saveProduct(@ModelAttribute ProductDto product) throws IOException {
		int userid=0;
		User userquery=null;
		try{
			
			 userid=product.getUserId();
			userquery=userRepository.getById(userid);

		
		}
		catch(Exception ex) {

			return "invalid user";
		}
		if(userquery!=null && userquery.getisSeller().equals("true")) {
			
		
		
		MultipartFile file = product.getFile();
		String orignialfileName=file.getOriginalFilename();
		Path fileNamePath=Paths.get(uploadDirectory,orignialfileName);
		Files.write(fileNamePath, file.getBytes());
		
		
		String product_name = product.getProductname();
		int quantity = product.getQuantity();
		int price = product.getPrice();
		
		Products products =new Products();
		products.setUserId(userid);
		products.setPrice(price);
		products.setQuantity(quantity);
		products.setProductname(product_name);
		String p="/img/"+orignialfileName;
		products.setImage(p);
		
		productRepository.save(products);
		
		String fileStr = file.getBytes().toString();
		
		return "Product details saved successfully in database";
		}
		else if(userquery!=null && userquery.getisSeller().equals("false")) {
			return "You are not a seller, Please create a seller account";
		}
		return "Invalid User id";
		}
	
	//API for listing all the products on sale
	@GetMapping("/showProducts")
	public ModelAndView showProducts(Model model) {
		ModelAndView modelview=new ModelAndView();
		modelview.setViewName("productslist");
		model.addAttribute("title", "list of products");
		model.addAttribute("products",productRepository.findAll());
		return modelview;
	}

	//API for buying a product
	@PostMapping("/order")
	public String buy(@RequestBody Order order) {
		Products p=null;
		User u=null;
		try {
			p=productRepository.getById(order.getProductId());
			u=userRepository.getById(order.getUserId());
			System.out.println(u);
			System.out.println(p);
		}catch(Exception e) {
			//System.out.println("-------------------------------4");
			return "invalid details";
		}
		if(p!=null && u!=null) {
		order.setName(p.getProductname());
		
		if(p.getQuantity()-order.getQuantity()<=0) {
			return "Product out of stock :(";
		}
		p.setQuantity(p.getQuantity()-order.getQuantity());
		order.setTotalAmount(p.getPrice()*order.getQuantity());
		productRepository.save(p);
		orderRepository.save(order);
		return "Thank you for shopping :)";
		}
		return "incorrect details";
		
	}	
	
	
	//API for listing all the products bought by a user
	@GetMapping("/showOrders{userid}")
	public ModelAndView showOrders(Model model,@RequestParam("userid") int id) {
		ModelAndView modelview=new ModelAndView();
		modelview.setViewName("orderslist");
		List<Products> productlist=new ArrayList<Products>();
		List<Order> orders=orderRepository.findByuserId(id);
		List<Integer> pid=new ArrayList<Integer>();
		for(Order o:orders) {
			Products p=productRepository.findById(o.getProductId());
			if(!pid.contains(o.getProductId())) {
			productlist.add(p);
			pid.add(p.getId());
			}
		}
		model.addAttribute("title", "list of orders");
		model.addAttribute("orders",productlist);
	
		return modelview;
	}
	
}

