package com.jpm.trade.service;

import java.util.List;

import com.jpm.trade.model.StockModel;
import com.jpm.trade.model.TradeModel;

public interface StockCalculatorService {
	
	public double getGeometricMean(List<StockModel> list);

	public double getDividendYeld(StockModel model);

	public double getPeRatio(StockModel model); 
	
	public double getStockPrice(List<TradeModel> list);

}
