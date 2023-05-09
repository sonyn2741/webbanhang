package com.api.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.converter.BillConverter;
import com.dto.BillDTO;
import com.model.Bill;
import com.service.BillMethod;

@RestController
public class BillAPI {
	@Autowired
	private BillMethod billMethod;
	
	@Autowired
	private BillConverter billConverter;
	
	@GetMapping("/api/bill")
	public BillDTO showBill(){
		BillDTO billDTO= new BillDTO();
		Date datee = new Date();
		String ngay = new SimpleDateFormat("yyyy-MM-dd").format(datee.getTime());
		List<Bill> day= billMethod.findByDate(ngay);
		List<Bill> ago= (List<Bill>) billMethod.findAll();
		List<BillDTO> lOne= new ArrayList<>();
		List<BillDTO> lTwo= new ArrayList<>();
		List<BillDTO> lThree= new ArrayList<>();
		for(Bill b: day) {
			lOne.add(billConverter.toDto(b));
		}
		for(Bill m:ago) {
			if(m.getDate().substring(0, m.getDate().length()-3).trim().equals(ngay.substring(0, ngay.length()-3).trim())) {
				lTwo.add(billConverter.toDto(m));
			}
		}
		for(Bill a:ago) {
			lThree.add(billConverter.toDto(a));
		}
		billDTO.setListResult(lOne);
		billDTO.setListResultTwo(lTwo);
		billDTO.setListResultThree(lThree);
		return billDTO;
	}
}
