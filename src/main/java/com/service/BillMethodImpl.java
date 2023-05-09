	package com.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Repository.BillRepository;
import com.converter.BillConverter;
import com.dto.BillDTO;
import com.model.Bill;
@Repository
@Service
public class BillMethodImpl implements BillMethod {
	private JdbcTemplate jdbc;
	@Autowired
	public BillMethodImpl(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	@Autowired
	private BillRepository bil;
	@Autowired
	private BillConverter billConverter;
	@Override
	public Iterable<Bill> findAll() {
		// TODO Auto-generated method stub
		return bil.findAll();
	}

	@Override
	public void save(Bill bill) {
		// TODO Auto-generated method stub
		bil.save(bill);
	}

	@Override
	public void delete(Bill bill) {
		// TODO Auto-generated method stub
		bil.delete(bill);
	}

	@Override
	public List<Bill> findByDate(String day1) {
		// TODO Auto-generated method stub
		return bil.findByDate(day1);
	}
	private Bill mapRowToBill(ResultSet rs, int rowNum) throws SQLException {
		Bill bill = new Bill();
		bill.setIdbill(rs.getString("idbill"));
		bill.setCountry(rs.getString("country"));
		bill.setCity(rs.getString("city"));
		bill.setCounty(rs.getString("county"));
		bill.setHn(rs.getString("hn"));
		bill.setPhone(rs.getString("phone"));
		bill.setDate(rs.getString("date"));
		bill.setTotal(rs.getInt("total"));
		bill.setUsername(rs.getString("username"));
		bill.setProducts(rs.getString("products"));
		return bill;

	}
	@Override
	public List<Bill> getBillByDate(String day1, String day2) {
		return (List<Bill>) jdbc.query("select idbill, country, city, county, hn, phone, date, total, username, products from bill where date BETWEEN ? AND ?",
				this::mapRowToBill, day1, day2);
		
	}
	@Override
	@Transactional
	public BillDTO create(BillDTO billDTO) {
		// TODO Auto-generated method stub
		Bill bill= new Bill();
		bill= billConverter.toEntity(billDTO);
		return billConverter.toDto(bil.save(bill));
	}

	
}
