package com.jpm.trade.store;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jpm.trade.model.TradeModel;

public class TradeStore {
	
	private static TradeStore instance;

	private Map<Long,TradeModel> store = new LinkedHashMap<Long,TradeModel>();
	private long lastId = 0;
	
	public static TradeStore getInstance() {
		if (instance == null) {
			instance = new TradeStore();
		}
		return instance;
	}

	/**
	 * 
	 * @return
	 */
	public List<TradeModel> getAll() {
		return new ArrayList<TradeModel>(store.values());
	}


	
	/**
	 * 
	 * @return
	 */
	public boolean deleteAll() {
		store = new LinkedHashMap<Long,TradeModel>();
		return true;
	}

	/**
	 * 
	 * @param model
	 * @return the model
	 */
	public TradeModel create(TradeModel model) {
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
	public TradeModel update(TradeModel model) {
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
	public TradeModel delete(TradeModel model) {
		if(model == null || model.getId() < 1) {
			throw new IllegalArgumentException();
		}
		return store.remove(model.getId());
	}


}
