package com.bank.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class CurrencyDAOImpl implements CurrencyDAO {
	
	private Map<String, Double> currencyExchange ;
	
	public CurrencyDAOImpl() {
		
		currencyExchange = new HashMap<>();
		
		currencyExchange.put("PLN", 1.0);
		currencyExchange.put("EUR", 4.2);
		currencyExchange.put("USD", 3.3);
	}
	
	@Override
	public double getCurrencyValue(String currencyName) {
		return currencyExchange.get(currencyName);
	}
}
