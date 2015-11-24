package com.jpm.trade.store;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jpm.trade.model.StockModel;

public class StockStore {
	
	private static StockStore instance;

	private Map<Long,StockModel> store = new LinkedHashMap<Long,StockModel>();
	private long lastId = 0;
	
	public static StockStore getInstance() {
		if (instance == null) {
			instance = new StockStore();
		}
		return instance;
	}

	/**
	 * 
	 * @return
	 */
	public List<StockModel> getAll() {
		return new ArrayList<StockModel>(store.values());
	}

	
	/**
	 * 
	 * @return
	 */
	public boolean deleteAll() {
		store = new LinkedHashMap<Long,StockModel>();
		return true;
	}

	/**
	 * 
	 * @param model
	 * @return the model
	 */
	public StockModel create(StockModel model) {
		if(model == null) {
			throw new IllegalArgumentException();
		}
		model.setId(++lastId);
		return store.put(model.getId(), model);
	}

	/**
	 * 
	 * @param model
	 * @return the model
	 */
	public StockModel update(StockModel model) {
		if(model == null) {
			throw new IllegalArgumentException();
		}
		if (!store.containsKey(model.getId())) {
			throw new IllegalArgumentException();
		}
		return store.put(model.getId(), model);
	}

	/**
	 * 
	 * @param model
	 * @return the model
	 */
	public StockModel delete(StockModel model) {
		if(model == null || model.getId() < 1) {
			throw new IllegalArgumentException();
		}
		return store.remove(model.getId());
	}


}
