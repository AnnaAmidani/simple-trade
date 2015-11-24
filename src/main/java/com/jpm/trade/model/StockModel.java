package com.jpm.trade.model;
import java.io.Serializable;

import com.jpm.trade.util.StockType;


@SuppressWarnings("serial")
public class StockModel implements Serializable{
	
	private long id;
	private String symbol;
	private StockType type;
	private int lastDividend;
	private float fixedDividend;
	private double parValue;
	private double tickerPrice; //Latest price of the stock based on the ticker symbol
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public StockType getType() {
		return type;
	}
	public void setType(StockType type) {
		this.type = type;
	}
	public int getLastDividend() {
		return lastDividend;
	}
	public void setLastDividend(int lastDividend) {
		this.lastDividend = lastDividend;
	}
	public float getFixedDividend() {
		return fixedDividend;
	}
	public void setFixedDividend(float fixedDividend) {
		this.fixedDividend = fixedDividend;
	}
	public double getParValue() {
		return parValue;
	}
	public void setParValue(double parValue) {
		this.parValue = parValue;
	}
	public double getTickerPrice() {
		return tickerPrice;
	}
	public void setTickerPrice(double tickerPrice) {
		this.tickerPrice = tickerPrice;
	}

	
	
	
	

}
