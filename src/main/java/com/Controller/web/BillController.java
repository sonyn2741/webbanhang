package com.Controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller(value = "billControllerOfWeb")
public class BillController {
	@GetMapping("/billForm")
	public String rabill() {
		return "redirect:/checkoutForm";
	}
}
