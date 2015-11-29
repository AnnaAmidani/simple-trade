package com.jpm.trade.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.jpm.trade.model.StockModel;
import com.jpm.trade.model.TradeModel;

public class StockCalculatorServiceImpl implements StockCalculatorService {

	private static final MathContext PRECISION = MathContext.DECIMAL128;
	private static StockCalculatorServiceImpl instance;

	public static StockCalculatorService getInstance() {
		if (instance == null) {
			instance = new StockCalculatorServiceImpl();
		}
		return instance;
	}

	/**
	 * 
	 * @param list
	 * @return
	 */
	public BigDecimal calculateGeometricMean(List<StockModel> list) {
		BigDecimal gm = new BigDecimal(1);
		for (StockModel model : list) {
			gm = gm.multiply(model.getParValue());
		}
		return new BigDecimal(Math.pow(gm.doubleValue(), 1.0 / list.size()));
	}

	/**
	 * 
	 */
	public BigDecimal calculateDividendYeld(StockModel stock) {
		switch (stock.getType()) {
		case COMMON:
			return new BigDecimal(stock.getLastDividend()).divide(stock.getTickerPrice(), PRECISION);
		case PREFERRED:
			BigDecimal fixedDividend = new BigDecimal(stock.getFixedDividend());
			return fixedDividend.multiply(stock.getParValue()).divide(stock.getTickerPrice(), PRECISION);
		}
		throw new IllegalArgumentException("The stock type " + stock.getType() + " is not supported.");
	}

	/**
	 * 
	 * @return
	 */
	public BigDecimal calculatePeRatio(StockModel stock) {
		return stock.getTickerPrice().divide(new BigDecimal(stock.getLastDividend()), MathContext.DECIMAL128);
	}

	/**
	 * 
	 */
	public BigDecimal calculateStockPrice(List<TradeModel> trades) {
		if (CollectionUtils.isEmpty(trades)) {
			return new BigDecimal(0);
		}
		BigDecimal totalPriceForQuantity = new BigDecimal(0);
		int totalQuantity = 0;
		for (TradeModel trade : trades) {
			totalPriceForQuantity = totalPriceForQuantity.add(trade.getStockPrice().multiply(new BigDecimal(trade.getSharesQnt())));
			totalQuantity += trade.getSharesQnt();
		}
		return totalPriceForQuantity.divide(new BigDecimal(totalQuantity), PRECISION);
	}
}
