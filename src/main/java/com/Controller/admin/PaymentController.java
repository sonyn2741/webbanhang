
 package com.Controller.admin;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.model.Bill;
import com.model.Carts;
import com.service.CartMethod;

@Controller 
 public class PaymentController { 
	@Autowired
	private CartMethod cartMethod;
	@GetMapping("/payment_infor")
	public String transaction(Model model,HttpSession session){
		String name= (String) session.getAttribute("user");
		List<Carts> list= cartMethod.getInfoCart(name);
		model.addAttribute("listCart", list);
		Bill bill= (Bill) session.getAttribute("billOnl");
		System.out.print(bill);
		model.addAttribute("bill", bill);
		int sumT = 0;
		for(Carts carts: list) {
			sumT += carts.getTotal();
		}
		model.addAttribute("sumT", sumT);
		int a = cartMethod.getCountCart(name);
		model.addAttribute("cCart", a);
		model.addAttribute("username", name);
		return "checkout_onl";
	}
 }