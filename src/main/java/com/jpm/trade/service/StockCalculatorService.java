package com.jpm.trade.service;

import java.math.BigDecimal;
import java.util.List;

import com.jpm.trade.model.StockModel;
import com.jpm.trade.model.TradeModel;

/**
 * Exposes all the available calculations on stocks.
 * 
 * @author Anna Amidani
 */
public interface StockCalculatorService {

	/**
	 * Calculates the All Share Index using the geometric mean of prices for all
	 * the given stocks.
	 * 
	 * @param list the list of stocks
	 * @return the calculated All Share Index
	 * 
	 * @throws IllegalArgumentException if the list is null or empty.
	 */
	public BigDecimal calculateAllShareIndex(List<StockModel> list);

	/**
	 * Calculates the dividend yeld of the given stock.
	 * 
	 * @param model a stock.
	 * @return the calculated dividend yeld.
	 * 
	 * @throws IllegalArgumentException if the ticker price of the stock is 0.
	 */
	public BigDecimal calculateDividendYeld(StockModel model);

	/**
	 * Calculates the pe ratio of the given stock.
	 * 
	 * @param model a stock.
	 * @return the calculated per ratio.
	 * 
	 * @throws IllegalArgumentException if the last dividend of the stock is 0.
	 */
	public BigDecimal calculatePeRatio(StockModel model);

	/**
	 * Calculates the stock price using the given trades.
	 * 
	 * @param list a list of trades associated to the same stock.
	 * @return the calculated stock price.
	 * 
	 * @throws IllegalArgumentException if the list of trades is null or empty.
	 * @throws IllegalArgumentException if the sum of the shared quantities of the trades is 0.
	 */
	public BigDecimal calculateStockPrice(List<TradeModel> list);

}
