package com.bank.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class CurrencyDAOImpl implements CurrencyDAO {
	
	private Map<String, BigDecimal> currencyExchange ;
	
	public CurrencyDAOImpl() {
		
		currencyExchange = new HashMap<>();
		
		currencyExchange.put("PLN", new BigDecimal("1.00"));
		currencyExchange.put("EUR", new BigDecimal("4.20"));
		currencyExchange.put("USD", new BigDecimal("3.30"));
	}
	
	@Override
	public BigDecimal getCurrencyValue(String currencyName) {
		return currencyExchange.get(currencyName);
	}
}
