package com.jpm.trade.store;

import java.util.List;

import com.jpm.trade.model.StockModel;

/**
 * The store for the stocks.
 * 
 * @author Anna Amidani
 */
public interface StockStore {

	/**
	 * Returns all the stored stocks.
	 * 
	 * @return all the stored stocks.
	 */
	public abstract List<StockModel> getAll();

	/**
	 * Deletes all the stored stocks.
	 * 
	 * @return true if the operation completes successfully.
	 */
	public abstract boolean deleteAll();

	/**
	 * Stores a new stock.
	 * 
	 * @param model the stock to store.
	 * @return the stored stock.
	 */
	public abstract StockModel create(StockModel model);

	/**
	 * Update the given stock.
	 * 
	 * @param model the stock to update.
	 * @return the updated stock.
	 */
	public abstract StockModel update(StockModel model);

	/**
	 * Delete the given stock.
	 * 
	 * @param model the stock to delete.
	 * @return the deleted stock.
	 */
	public abstract StockModel delete(StockModel model);

}