package com.jpm.trade.util;

import java.math.BigDecimal;

import org.joda.time.DateTime;

import com.jpm.trade.model.StockModel;
import com.jpm.trade.model.TradeModel;
import com.jpm.trade.util.StockType;

/**
 * Unit test utility methods.
 */
public class TestUtil {

	public static StockModel buildStock(BigDecimal parValue, BigDecimal tickerPrice, int lastDividend, String symbol, StockType type) {
		StockModel stock = new StockModel();
		stock.setParValue(parValue);
		stock.setTickerPrice(tickerPrice);
		stock.setLastDividend(lastDividend);
		stock.setSymbol(symbol);
		stock.setType(type);
		return stock;
	}

	public static TradeModel buildTrade(long stockRef, DateTime startTime, OperationType operationType, int sharesQnt, DateTime endTime, BigDecimal stockPrice) {
		TradeModel trade = new TradeModel();
		trade.setStockRef(stockRef);
		trade.setStartTime(startTime);
		trade.setOperationType(operationType);
		trade.setSharesQnt(sharesQnt);
		trade.setStockPrice(stockPrice);
		trade.setEndTime(endTime);
		return trade;
	}

}
