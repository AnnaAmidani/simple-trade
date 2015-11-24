package com.jpm.trade.service;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.jpm.trade.model.StockModel;
import com.jpm.trade.model.TradeModel;

public class StockCalculatorServiceImpl implements StockCalculatorService {

	private static StockCalculatorServiceImpl instance;

	public static StockCalculatorServiceImpl getInstance() {
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
	public double getGeometricMean(List<StockModel> list) {
		double gm = 1;
		for (StockModel model : list) {
			gm *= model.getParValue();
		}
		return Math.pow(gm, 1.0 / list.size());
	}

	/**
	 * 
	 */
	public double getDividendYeld(StockModel stock) {
		double dividendYeld = 0;
		switch (stock.getType()) {
		case COMMON:
			dividendYeld = stock.getLastDividend() / stock.getTickerPrice();
			break;
		case PREFERRED:
			dividendYeld = (stock.getFixedDividend() * stock.getParValue())
					/ stock.getTickerPrice();
			break;
		default:
		}

		return dividendYeld;
	}

	/**
	 * 
	 * @return
	 */
	public double getPeRatio(StockModel stock) {
		return stock.getTickerPrice() / stock.getLastDividend();
	}

	/**
	 * 
	 */
	public double getStockPrice(List<TradeModel> list) {

		double totalPriceForQuantity = 0;
		int totalQuantity = 0;

		for(TradeModel trade : list) {
			DateTime now = new DateTime();
			DateTime minutesAgo = now.minusMinutes(15);
			Interval interval = new Interval(minutesAgo, now);
			boolean intervalContainsEndOfTrade = interval.contains( trade.getEndTime() );
			if(intervalContainsEndOfTrade) {
				totalPriceForQuantity += trade.getStockPrice() * trade.getSharesQnt();
				totalQuantity += trade.getSharesQnt();
			}
		}
		return totalPriceForQuantity / totalQuantity;


	}
}
