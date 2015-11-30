package com.jpm.trade.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.jpm.trade.model.StockModel;
import com.jpm.trade.model.TradeModel;

/**
 * An implementation of the  {@link StockCalculatorService}.
 * 
 * @author Anna Amidani
 */
public class StockCalculatorServiceImpl implements StockCalculatorService {

	private static final MathContext PRECISION = MathContext.DECIMAL64;
	private static StockCalculatorServiceImpl instance;

	/**
	 * Returns an instance of the stock calculator service.
	 * 
	 * @return an instance of the stock calculator service.
	 */
	public static StockCalculatorService getInstance() {
		if (instance == null) {
			instance = new StockCalculatorServiceImpl();
		}
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * @see com.jpm.trade.service.StockCalculatorService#calculateGeometricMean(java.util.List)
	 */
	public BigDecimal calculateAllShareIndex(List<StockModel> list) {
		if (CollectionUtils.isEmpty(list)) {
			throw new IllegalArgumentException("the list cannot be empty.");
		}
		BigDecimal gm = new BigDecimal(1);
		for (StockModel model : list) {
			gm = gm.multiply(model.getParValue());
		}
		gm = new BigDecimal(Math.pow(gm.doubleValue(), 1.0 / list.size()), PRECISION);
		return gm.stripTrailingZeros();
	}

	/*
	 * (non-Javadoc)
	 * @see com.jpm.trade.service.StockCalculatorService#calculateDividendYeld(com.jpm.trade.model.StockModel)
	 */
	public BigDecimal calculateDividendYeld(StockModel stock) {
		if (stock.getTickerPrice().equals(new BigDecimal(0))) {
			throw new IllegalArgumentException("the ticker price cannot be 0.");
		}
		if (stock.getType() == null) {
			throw new IllegalArgumentException("the stock type is null.");
		}
		BigDecimal dividendYeld = null;
		switch (stock.getType()) {
		case COMMON:
			dividendYeld = new BigDecimal(stock.getLastDividend()).divide(stock.getTickerPrice(), PRECISION);
			break;
		case PREFERRED:
			BigDecimal fixedDividend = new BigDecimal(stock.getFixedDividend());
			dividendYeld = fixedDividend.multiply(stock.getParValue()).divide(stock.getTickerPrice(), PRECISION);
			break;
		default:
			throw new IllegalArgumentException("the stock type " + stock.getType() + " is not supported.");
		}
		return dividendYeld.stripTrailingZeros();
	}

	/*
	 * (non-Javadoc)
	 * @see com.jpm.trade.service.StockCalculatorService#calculatePeRatio(com.jpm.trade.model.StockModel)
	 */
	public BigDecimal calculatePeRatio(StockModel stock) {
		if (stock.getLastDividend() == 0) {
			throw new IllegalArgumentException("the last dividend cannot be 0.");
		}
		BigDecimal peRatio = stock.getTickerPrice().divide(new BigDecimal(stock.getLastDividend()), MathContext.DECIMAL128);
		return peRatio.stripTrailingZeros();
	}

	/*
	 * (non-Javadoc)
	 * @see com.jpm.trade.service.StockCalculatorService#calculateStockPrice(java.util.List)
	 */
	public BigDecimal calculateStockPrice(List<TradeModel> trades) {
		if (CollectionUtils.isEmpty(trades)) {
			throw new IllegalArgumentException("the trade list cannot be empty.");
		}
		BigDecimal totalPriceForQuantity = new BigDecimal(0);
		int totalQuantity = 0;
		for (TradeModel trade : trades) {
			totalPriceForQuantity = totalPriceForQuantity.add(trade.getStockPrice().multiply(new BigDecimal(trade.getSharesQnt())));
			totalQuantity += trade.getSharesQnt();
		}
		if (totalQuantity == 0) {
			throw new IllegalArgumentException("the total quantity cannot be 0.");
		}
		BigDecimal stockPrice = totalPriceForQuantity.divide(new BigDecimal(totalQuantity), PRECISION);
		return stockPrice.stripTrailingZeros();
	}
}
