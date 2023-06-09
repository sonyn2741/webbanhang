package com.Controller.admin;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.config.VNPayConfig;
import com.dto.PaymentResDTO;
import com.dto.TransactionStatusDTO;
import com.model.Bill;

@RestController
@RequestMapping("/api/paymentVNPay")
public class VNPayController {

	
	@PostMapping("/create_payment")
	public ResponseEntity<?> createPayment(@RequestBody Bill bill,HttpSession session) throws UnsupportedEncodingException{
		
        session.setAttribute("billOnl", bill);
		long amount = bill.getTotal();
		
        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
        
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VNPayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", VNPayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount*100));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl",VNPayConfig.vnp_Returnurl);
        
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String vnp_CreateDate = myDateObj.format(myFormatObj);
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
               
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;
        
        PaymentResDTO paymentResDTO = new PaymentResDTO();
        paymentResDTO.setStatus("OK");
        paymentResDTO.setMessage("success");
        paymentResDTO.setUrl(paymentUrl);
		return ResponseEntity.status(HttpStatus.OK).body(paymentResDTO);
	}
	
	@GetMapping("/payment_infor")
	public ResponseEntity<?> transaction(
			@RequestParam(value = "vnp_Amount") String amount,
			@RequestParam(value = "vnp_BankCode") String bankCode,
			@RequestParam(value = "vnp_OrderInfo") String orderInfo,
			@RequestParam(value = "vnp_ResponeCode") String responeCode
	){
		TransactionStatusDTO transactionStatusDTO = new TransactionStatusDTO();
		
		
		if(responeCode.equals("00")) {
			transactionStatusDTO.setStatus("OK");
			transactionStatusDTO.setMessage("Successfully");
			transactionStatusDTO.setData("");
		}else {
			transactionStatusDTO.setStatus("No");
			transactionStatusDTO.setMessage("Failed");
			transactionStatusDTO.setData("");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(transactionStatusDTO);

	}
	
}
