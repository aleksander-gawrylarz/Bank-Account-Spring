package com.bank.dao;

import java.math.BigDecimal;

public interface CurrencyDAO {
	
	public BigDecimal getCurrencyValue(String currencyName);
}
