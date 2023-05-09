package com.Controller.admin;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dto.BillDTO;
import com.google.gson.Gson;

@Controller(value = "billControllerOfAdmin")
public class BillController {
	
	@GetMapping("/adminForm2")
	public ModelAndView dash2(@RequestParam(name = "BillDTO", required = false) String billDTO, HttpSession session) {
			ModelAndView mav= new ModelAndView("dashboard");
			if (billDTO != null) {
	            Gson gson = new Gson();
	            BillDTO bDto = gson.fromJson(billDTO, BillDTO.class);
	            mav.addObject("list", bDto.getListResult());
				mav.addObject("list1", bDto.getListResultTwo());
				mav.addObject("list3", bDto.getListResultThree());
	        }
			mav.addObject("username", session.getAttribute("admin"));
			return mav;
	}
}
