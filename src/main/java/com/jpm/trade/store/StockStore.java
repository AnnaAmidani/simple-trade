package com.jpm.trade.store;

import java.util.List;

import com.jpm.trade.model.StockModel;

public interface StockStore {

	/**
	 * 
	 * @return
	 */
	public abstract List<StockModel> getAll();

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
	public abstract StockModel create(StockModel model);

	/**
	 * 
	 * @param model
	 * @return the model
	 */
	public abstract StockModel update(StockModel model);

	/**
	 * 
	 * @param model
	 * @return the model
	 */
	public abstract StockModel delete(StockModel model);

}