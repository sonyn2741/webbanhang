package com.api.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dto.BillDTO;
import com.model.Carts;
import com.service.BillMethod;
import com.service.CartMethod;

@RestController(value = "BillAPIOfWeb")
public class BillAPI {
	@Autowired
	private BillMethod billMethod;
	@Autowired
	private CartMethod cartMethod;
	@PostMapping("/api/web/bill_off")
	public BillDTO saveBill(@RequestBody BillDTO bill,HttpSession session){
		String name= (String) session.getAttribute("user");
		List<Carts> list = cartMethod.getInfoCart(name);
		int sumT = 0;
		for(Carts carts: list) {
			sumT += carts.getTotal();
		}
		for(Carts carts: list) {
			cartMethod.udProduct(carts.getIdproduct(), carts.getNum());
		}
		bill.setTotal(sumT);
		bill.setUsername(name);
		Date datee = new Date();
        String ngayMua = new SimpleDateFormat("yyyy/MM/dd").format(datee.getTime());
		bill.setDate(ngayMua);
		int idb = Integer.parseInt(cartMethod.getIdBill().getIdbill());
		bill.setIdbill(String.valueOf(idb+1));
		String listp = "";
		for(Carts carts: list) {
			listp += carts.getNameproduct()+"x"+carts.getNum()+"; ";
		}
		bill.setProducts(listp);
		bill.setStatus(0);
		cartMethod.deleteAllByUsername(name);
		return billMethod.create(bill);
	}
	@PostMapping("/api/web/bill_onl")
	public BillDTO saveBillOnl(@RequestBody BillDTO bill,HttpSession session){
		String name= (String) session.getAttribute("user");
		List<Carts> list = cartMethod.getInfoCart(name);
		int sumT = 0;
		for(Carts carts: list) {
			sumT += carts.getTotal();
		}
		for(Carts carts: list) {
			cartMethod.udProduct(carts.getIdproduct(), carts.getNum());
		}
		bill.setTotal(sumT);
		bill.setUsername(name);
		Date datee = new Date();
        String ngayMua = new SimpleDateFormat("yyyy/MM/dd").format(datee.getTime());
		bill.setDate(ngayMua);
		int idb = Integer.parseInt(cartMethod.getIdBill().getIdbill());
		bill.setIdbill(String.valueOf(idb+1));
		String listp = "";
		for(Carts carts: list) {
			listp += carts.getNameproduct()+"x"+carts.getNum()+"; ";
		}
		bill.setProducts(listp);
		bill.setStatus(1);
		cartMethod.deleteAllByUsername(name);
		return billMethod.create(bill);
	}
}
