package com.jpm.trade.store;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.jpm.trade.model.StockModel;

/**
 * Simplified implementation of a {@link StockStore}, to be used only for example purpose.
 * 
 * @author Anna Amidani
 */
public class StockStoreImpl implements StockStore {
	
	private static StockStore instance;

	private Map<Long,StockModel> store = new LinkedHashMap<Long,StockModel>();
	private long lastId = 0;

	/**
	 * Returns an instance of a {@link StockStore}.
	 * 
	 * @return an instance of a {@link StockStore}.
	 */
	public static StockStore getInstance() {
		if (instance == null) {
			instance = new StockStoreImpl();
		}
		return instance;
	}

	/* (non-Javadoc)
	 * @see com.jpm.trade.store.StockStore#getAll()
	 */
	public List<StockModel> getAll() {
		return new ArrayList<StockModel>(store.values());
	}

	
	/* (non-Javadoc)
	 * @see com.jpm.trade.store.StockStore#deleteAll()
	 */
	public boolean deleteAll() {
		store = new LinkedHashMap<Long,StockModel>();
		return true;
	}

	/* (non-Javadoc)
	 * @see com.jpm.trade.store.StockStore#create(com.jpm.trade.model.StockModel)
	 */
	public StockModel create(StockModel model) {
		if (isNotValid(model)) {
			throw new IllegalArgumentException();
		}
		model.setId(++lastId);
		store.put(model.getId(), model);
		return store.get(model.getId());
	}

	private boolean isNotValid(StockModel model) {
		return model == null || model.getParValue() == null || model.getTickerPrice() == null || StringUtils.isBlank(model.getSymbol()) || model.getType() == null;
	}

	/* (non-Javadoc)
	 * @see com.jpm.trade.store.StockStore#update(com.jpm.trade.model.StockModel)
	 */
	public StockModel update(StockModel model) {
		if (isNotValid(model)) {
			throw new IllegalArgumentException();
		}
		if (!store.containsKey(model.getId())) {
			throw new IllegalArgumentException();
		}
		return store.put(model.getId(), model);
	}

	/* (non-Javadoc)
	 * @see com.jpm.trade.store.StockStore#delete(com.jpm.trade.model.StockModel)
	 */
	public StockModel delete(StockModel model) {
		if(model == null || model.getId() < 1) {
			throw new IllegalArgumentException();
		}
		return store.remove(model.getId());
	}
}
