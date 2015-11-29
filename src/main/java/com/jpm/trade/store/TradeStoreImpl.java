package com.jpm.trade.store;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.Interval;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.jpm.trade.model.StockModel;
import com.jpm.trade.model.TradeModel;

public class TradeStoreImpl implements TradeStore {
	
	private static TradeStore instance;

	private Map<Long,TradeModel> store = new LinkedHashMap<Long,TradeModel>();
	private long lastId = 0;
	
	public static TradeStore getInstance() {
		if (instance == null) {
			instance = new TradeStoreImpl();
		}
		return instance;
	}

	
	/* (non-Javadoc)
	 * @see com.jpm.trade.store.TradeStore#getAll()
	 */
	public List<TradeModel> getAll() {
		return new ArrayList<TradeModel>(store.values());
	}

	
	/* (non-Javadoc)
	 * @see com.jpm.trade.store.TradeStore#queryByStockAndInterval(com.jpm.trade.model.StockModel, org.joda.time.Interval)
	 */
	public List<TradeModel> queryByStockAndInterval(final StockModel stock, final Interval interval) {
		return FluentIterable.from(store.values()).filter(new Predicate<TradeModel>() {
	        public boolean apply(TradeModel trade) {
				return trade.getStockRef() == stock.getId() && interval.contains(trade.getEndTime());
	        }			
		}).toList();
	}

	
	/* (non-Javadoc)
	 * @see com.jpm.trade.store.TradeStore#queryByStock(com.jpm.trade.model.StockModel)
	 */
	public List<TradeModel> queryByStock(final StockModel stock) {
		return FluentIterable.from(store.values()).filter(new Predicate<TradeModel>() {
	        public boolean apply(TradeModel trade) {
				return trade.getStockRef() == stock.getId();
	        }			
		}).toList();
	}
	
	/* (non-Javadoc)
	 * @see com.jpm.trade.store.TradeStore#deleteAll()
	 */
	public boolean deleteAll() {
		store = new LinkedHashMap<Long,TradeModel>();
		return true;
	}

	/* (non-Javadoc)
	 * @see com.jpm.trade.store.TradeStore#create(com.jpm.trade.model.TradeModel)
	 */
	public TradeModel create(TradeModel model) {
		if(model == null) {
			throw new IllegalArgumentException();
		}
		model.setId(++lastId);
		return store.put(model.getId(), model);
	}

	/* (non-Javadoc)
	 * @see com.jpm.trade.store.TradeStore#update(com.jpm.trade.model.TradeModel)
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

	/* (non-Javadoc)
	 * @see com.jpm.trade.store.TradeStore#delete(com.jpm.trade.model.TradeModel)
	 */
	public TradeModel delete(TradeModel model) {
		if(model == null || model.getId() < 1) {
			throw new IllegalArgumentException();
		}
		return store.remove(model.getId());
	}




}
