package com.jpm.trade.store;

import java.util.List;

import org.joda.time.Interval;

import com.jpm.trade.model.StockModel;
import com.jpm.trade.model.TradeModel;

/**
 * The store for the trade.
 * 
 * @author Anna Amidani
 */
public interface TradeStore {

	/**
	 * Returns all the stored trades.
	 *  
	 * @return all the stored trades.
	 */
	public abstract List<TradeModel> getAll();

	/**
	 * Returns all the trades associated to a specific stock in the specified interval.
	 * 
	 * @param stock a stock.
	 * @param interval a time interval.
	 * @return all the trades associated to a specific stock in the specified interval.
	 */
	public abstract List<TradeModel> queryByStockAndInterval(StockModel stock,
			Interval interval);

	/**
	 * Returns all the trades associated to a specific stock
	 * 
	 * @param stock a stock.
	 * @return the trades associated to a specific stock
	 */
	public abstract List<TradeModel> queryByStock(StockModel stock);

	/**
	 * Delete all the stored trades.
	 *  
	 * @return true if the operation completes successfully.
	 */
	public abstract boolean deleteAll();

	/**
	 * Stores a new trade.
	 * 
	 * @param model the trade to store.
	 * @return the stored trade.
	 */
	public abstract TradeModel create(TradeModel model);

	/**
	 * Updates the given trade.
	 * 
	 * @param model the trade to update.
	 * @return the model the updated trade.
	 */
	public abstract TradeModel update(TradeModel model);

	/**
	 * Deletes the given trade.
	 * 
	 * @param model the trade to delete.
	 * @return the deleted trade.
	 */
	public abstract TradeModel delete(TradeModel model);

}