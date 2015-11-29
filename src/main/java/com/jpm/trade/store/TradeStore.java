package com.jpm.trade.store;

import java.util.List;

import org.joda.time.Interval;

import com.jpm.trade.model.StockModel;
import com.jpm.trade.model.TradeModel;

public interface TradeStore {

	/**
	 * 
	 * @return
	 */
	public abstract List<TradeModel> getAll();

	/**
	 * 
	 * @return
	 */
	public abstract List<TradeModel> queryByStockAndInterval(StockModel stock,
			Interval interval);

	/**
	 * 
	 * @param stockId
	 * @return
	 */
	public abstract List<TradeModel> queryByStock(StockModel stock);

	/**
	 * 
	 * @return
	 */
	public abstract boolean deleteAll();

	/**
	 * 
	 * @param model
	 * @return the model
	 */
	public abstract TradeModel create(TradeModel model);

	/**
	 * 
	 * @param model
	 * @return the model
	 */
	public abstract TradeModel update(TradeModel model);

	/**
	 * 
	 * @param model
	 * @return the model
	 */
	public abstract TradeModel delete(TradeModel model);

}