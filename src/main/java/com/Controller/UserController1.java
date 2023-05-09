//package com.Controller;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.model.Bill;
//import com.model.Cart;
//import com.model.Carts;
//import com.model.Product;
//import com.model.User;
//import com.service.AdminMethod;
//import com.service.BillMethod;
//import com.service.CartMethod;
//import com.service.ProductMethod;
//import com.service.UserMethod;
//
//
//@Controller
//public class UserController1 {
//	@Autowired
//	private BillMethod bil;
//	@Autowired
//	private UserMethod use1;
//	@Autowired 
//	private AdminMethod adm;
//	@Autowired
//	private ProductMethod pro;
//	@Autowired
//	private CartMethod car;
//	@Autowired
//	JavaMailSender mail;
//	@GetMapping("/")
//	public String shop() {
//		return "index";
//	}
//	@GetMapping("/userForm")
//    public String index(Model model) {
//   	 model.addAttribute("user", new User());
//   	 model.addAttribute("userdk", new User());
//   	 return "Login";
//    }
//	@PostMapping("/userlogin")
//	public String useLogin(User user,HttpSession session,Model model) {
//		session.setAttribute("user", user.getUsername());
//		if( use1.existsByUsernameAndPass(user.getUsername(), user.getPass())==true) {
//			model.addAttribute("listProduct", pro.findAll());
//			model.addAttribute("username", session.getAttribute("user"));
//			return "usershop";
//		}
//		model.addAttribute("message", "User not exist!!!");
//		return "Login";
//	}
//	@PostMapping("/loginCtl")
//	public String userdk(User user,Model model) {
//		if(use1.findByUsername(user.getUsername())==null) {
//			use1.save(user);
//			model.addAttribute("mess1","registion suscessful!!");
//			return "Login";
//		}
//		model.addAttribute("mess", "username existed!!!");
//		return "Login";
//	}
//	@PostMapping("/filterForm")
//	public String fil(@RequestParam("type") String type,@RequestParam("con") String con,@RequestParam("cost") String cost,Model model) {
//		if(type.equals("Cancel") && con.equals("Cancel") && cost.equals("Cancel")) {
//			model.addAttribute("listProduct", pro.findAll());
//		}
//		else if(con.equals("Cancel") && cost.equals("Cancel")) {
//				model.addAttribute("listProduct", pro.findByModel(type));
//		}
//		else if(type.equals("Cancel") && cost.equals("Cancel")) {
//			model.addAttribute("listProduct", pro.findByConfiguration(con));
//		}
//		else if(type.equals("Cancel") && con.equals("Cancel")) {
//			String gia[]=cost.split("-");
//			int gia1=Integer.parseInt(gia[0]);
//			int gia2=Integer.parseInt(gia[1]);
//			model.addAttribute("listProduct", pro.findByPrice1(gia1, gia2));
//		}
//		else if(cost.equals("Cancel")) {
//			model.addAttribute("listProduct", pro.findByModelAndConfiguration(type, con));
//		}
//		else if(con.equals("Cancel")) {
//			String gia0[]= cost.split("-");
//			int gia11=Integer.parseInt(gia0[0]);
//			int gia21=Integer.parseInt(gia0[1]);
//			model.addAttribute("listProduct", pro.findByModelAndPrice1(type, gia11,gia21));
//		}
//		else if(type.equals("Cancel")) {
//			String gia02[]= cost.split("-");
//			int gia12=Integer.parseInt(gia02[0]);
//			int gia22=Integer.parseInt(gia02[1]);
//			model.addAttribute("listProduct", pro.findByModelAndPrice1(type, gia12,gia22));
//		}
//		else {
//			String gia02[]= cost.split("-");
//			int gia12=Integer.parseInt(gia02[0]);
//			int gia22=Integer.parseInt(gia02[1]);
//			model.addAttribute("listProduct", pro.findByModelAndConfigurationAndPrice1(type, con, gia12, gia22));
//		}
//		return "usershop";
//	}
//	@GetMapping("/cartForm/{code}/tocart")
//	public String carttocart(@PathVariable String code,HttpSession session ,Model model) {
//		int slcl= pro.findByCode(code).getQuantity();
//		if(slcl>0) {
//			String usename= (String) session.getAttribute("user");
//			int countcart= car.findIdCart2();
//			int price=Integer.parseInt(pro.findByCode(code).getPrice());
//			Cart cart= new Cart();
//			if(countcart==0) {
//				cart.setIdcart(1);
//			}
//			else {
//				int idcar= car.findIdCart().getIdcart();
//				cart.setIdcart(idcar+1);
//			}
//			cart.setUsername(usename);
//			cart.setIdproduct(code);
//			cart.setNum(1);
//			cart.setTotal(price);
//			car.save(cart);
//			model.addAttribute("cart", cart);
//			model.addAttribute("mess", "add to cart successful!!!");
//		}
//		else {
//			model.addAttribute("mess", "The product is temporarily out of stock!!!");
//		}
//		return "redirect:/shoppage";
//	}
//	@GetMapping("/shoppage")
//	public String shoppage(Model model, HttpSession session) {
//		Iterable<Product> listPro= pro.findAll();
//		model.addAttribute("listProduct", listPro);
//		String name= (String) session.getAttribute("user");
//		List<Carts> list= car.getInfoCart(name);
//		int sumT=0;
//		for(Carts carts:list) {
//			sumT+=carts.getTotal();
//		}
//		model.addAttribute("sumT", sumT);
//		int a = car.getCountCart(name);
//		model.addAttribute("cCart", a);
//		model.addAttribute("username", name);
//		return "usershop";	
//	}
//	@GetMapping("/cart")
//	public String cart(Model model,HttpSession session) {
//		String name= (String) session.getAttribute("user");
//		List<Carts> list= car.getInfoCart(name);
//		model.addAttribute("listCart", list);
//		model.addAttribute("username", name);
//		int sumT = 0;
//		for(Carts carts: list) {
//			sumT += carts.getTotal();
//		}
//		model.addAttribute("sumT", sumT);
//		int a = car.getCountCart(name);
//		model.addAttribute("cCart", a);
//		return "cart";
//	}
//	@GetMapping("/deleteCart/{code}/dele")
//	public String cart2(@PathVariable int code,HttpSession session,Model model){
//		Cart cart = car.findByIdcart(code);
//		car.delete(cart);
////		String name= (String) session.getAttribute("user");
////		List<Carts> list= car.getInfoCart(name);
////		model.addAttribute("listCart", list);
////		model.addAttribute("username", name);
////		int sumT = 0;
////		for(Carts carts: list) {
////			sumT += carts.getTotal();
////		}
////		model.addAttribute("sumT", sumT);
////		int a = car.getCountCart(name);
////		model.addAttribute("cCart", a);
//		return "redirect:/cart";
//	}
//	@GetMapping("/numCart")
//	public String updNum(@RequestParam(value = "id")int iD, @RequestParam(value = "check") String check, @RequestParam(value = "idpro") String idpro,
//	                     Model model,HttpSession session) {
//			String name= (String) session.getAttribute("user");
//			String iD2=String.valueOf(iD);
//			if(check.equals("plus")) {
//				car.updateNum(iD2);
//			}
//			else {
//				car.updateMNum(iD2);
//			}
//			Cart cart= car.findByIdcart(iD);
//			int num=cart.getNum();
//			int price= Integer.parseInt(pro.findByCode(idpro).getPrice());
//			int sum=num*price;
//			car.updateTotalCart(iD2, sum);
//			model.addAttribute("username", name);
//		return "redirect:/cart";
//	}
//	@GetMapping("/checkoutForm")
//	public String checkoutform(Model model,HttpSession session) {
//		String name= (String) session.getAttribute("user");
//		List<Carts> list= car.getInfoCart(name);
//		model.addAttribute("listCart", list);
//		int sumT = 0;
//		for(Carts carts: list) {
//			sumT += carts.getTotal();
//		}
//		model.addAttribute("sumT", sumT);
//		int a = car.getCountCart(name);
//		model.addAttribute("cCart", a);
//		model.addAttribute("bill", new Bill());
//		model.addAttribute("username", name);
//		return "checkout";
//	}
//	@GetMapping("/billForm")
//	public String rabill(Bill bill,Model model,HttpSession session) {
//		String name= (String) session.getAttribute("user");
//		List<Carts> list = car.getInfoCart(name);
//		int sumT = 0;
//		for(Carts carts: list) {
//			sumT += carts.getTotal();
//		}
//		for(Carts carts: list) {
//			car.udProduct(carts.getIdproduct(), carts.getNum());
//		}
//		bill.setTotal(sumT);
//		bill.setUsername(name);
//		Date datee = new Date();
//        String ngayMua = new SimpleDateFormat("yyyy/MM/dd").format(datee.getTime());
//		bill.setDate(ngayMua);
//		
//		
//		int idb = Integer.parseInt(car.getIdBill().getIdbill());
//		bill.setIdbill(String.valueOf(idb+1));
//		String listp = "";
//		for(Carts carts: list) {
//			listp += carts.getNameproduct()+"x"+carts.getNum()+"; ";
//		}
//		bill.setProducts(listp);
//		bil.save(bill);
//		car.deleteAllByUsername(name);
//		model.addAttribute("mess1", "Order Successfull!!!");
//		return "redirect:/checkoutForm";
//	}
//	@GetMapping("/contactForm")
//	public String contactF(Model model,HttpSession session) {
//		
//		model.addAttribute("username", session.getAttribute("user"));
//		return "contact";
//	}
//	
//	@PostMapping("/sendMail")
//	public String sendMailer(@RequestParam("to") String to, @RequestParam("sj") String sj, @RequestParam("mess") String mess, Model model) {
//		SimpleMailMessage msg = new SimpleMailMessage();
//		msg.setFrom(to);
//		msg.setTo("nguyenmanh.2014.1102@gmail.com");
//		msg.setSubject(sj);
//		msg.setText(mess);
//		mail.send(msg);
//		return "contact";
//	}
//}
