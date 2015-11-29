package com.jpm.trade.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.joda.time.DateTime;

import com.jpm.trade.util.OperationType;


@SuppressWarnings("serial")
public class TradeModel implements Serializable{
	
	private long id;
	private long stockRef;
	private DateTime startTime;
	private DateTime endTime;
	private OperationType operationType;
	private int sharesQnt;
	private BigDecimal stockPrice;
	

	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getStockRef() {
		return stockRef;
	}
	public void setStockRef(long stockRef) {
		this.stockRef = stockRef;
	}
	public DateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(DateTime startTime) {
		this.startTime = startTime;
	}
	public DateTime getEndTime() {
		return endTime;
	}
	public void setEndTime(DateTime endTime) {
		this.endTime = endTime;
	}
	public OperationType getOperationType() {
		return operationType;
	}
	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}
	public int getSharesQnt() {
		return sharesQnt;
	}
	public void setSharesQnt(int sharesQnt) {
		this.sharesQnt = sharesQnt;
	}
	public BigDecimal getStockPrice() {
		return stockPrice;
	}
	public void setStockPrice(BigDecimal stockPrice) {
		this.stockPrice = stockPrice;
	}
	

}
