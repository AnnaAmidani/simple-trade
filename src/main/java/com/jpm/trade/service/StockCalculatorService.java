package com.jpm.trade.service;

import java.math.BigDecimal;
import java.util.List;

import com.jpm.trade.model.StockModel;
import com.jpm.trade.model.TradeModel;

public interface StockCalculatorService {
	
	public BigDecimal calculateGeometricMean(List<StockModel> list);

	public BigDecimal calculateDividendYeld(StockModel model);

	public BigDecimal calculatePeRatio(StockModel model); 
	
	public BigDecimal calculateStockPrice(List<TradeModel> list);

}
