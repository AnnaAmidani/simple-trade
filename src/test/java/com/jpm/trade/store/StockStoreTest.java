package com.jpm.trade.store;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.jpm.trade.model.StockModel;
import com.jpm.trade.util.StockType;

/**
 * Unit test for simple TradeSession.
 */
public class StockStoreTest {

	StockStore stockStore = StockStoreImpl.getInstance();

	@Test
	public void testStockCreation() {
		Assert.assertTrue(stockStore.deleteAll());
		List<StockModel> all = stockStore.getAll();
		Assert.assertNotNull(all);
		Assert.assertTrue(all.isEmpty());
		
		BigDecimal parValue = new BigDecimal(10);
		BigDecimal tickerPrice = new BigDecimal(12);
		int lastDividend = 12;
		String symbol = "ALE";
		StockType type = StockType.COMMON;

		StockModel created = stockStore.create(TestUtil.buildStock(parValue, tickerPrice, lastDividend, symbol, type));
		
		Assert.assertNotNull(created);
		Assert.assertEquals(parValue, created.getParValue());
		Assert.assertEquals(symbol, created.getSymbol());
		Assert.assertEquals(type, created.getType());
		Assert.assertEquals(tickerPrice, created.getTickerPrice());
		Assert.assertEquals(lastDividend, created.getLastDividend());
	}

	
	@Test
	public void testGetAllStock() {
		Assert.assertTrue(stockStore.deleteAll());
		List<StockModel> all = stockStore.getAll();
		Assert.assertNotNull(all);
		Assert.assertTrue(all.isEmpty());
		
		stockStore.create(TestUtil.buildStock(new BigDecimal(10), new BigDecimal(12), 3, "ALE", StockType.COMMON));
		stockStore.create(TestUtil.buildStock(new BigDecimal(10), new BigDecimal(12), 3, "ALE", StockType.COMMON));
		
		all = stockStore.getAll();
		
		Assert.assertNotNull(all);
		Assert.assertEquals(2, all.size());
		for (StockModel stock : all) {
			Assert.assertNotNull(stock);
			Assert.assertNotNull(stock.getParValue());
			Assert.assertNotNull(stock.getTickerPrice());
			Assert.assertTrue(StringUtils.isNoneBlank(stock.getSymbol()));
			Assert.assertEquals(StockType.COMMON, stock.getType());
		}
	}

	@Test
	public void testDeleteAllStock() {
		Assert.assertTrue(stockStore.deleteAll());
		List<StockModel> all = stockStore.getAll();
		Assert.assertNotNull(all);
		Assert.assertTrue(all.isEmpty());
		
		stockStore.create(TestUtil.buildStock(new BigDecimal(10), new BigDecimal(12), 2, "ALE", StockType.COMMON));
		stockStore.create(TestUtil.buildStock(new BigDecimal(10), new BigDecimal(12), 2, "ALE", StockType.COMMON));
		
		Assert.assertTrue(stockStore.deleteAll());
		all = stockStore.getAll();
		Assert.assertNotNull(all);
		Assert.assertTrue(all.isEmpty());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testInvalidStockCreation() {
		StockModel stock = new StockModel();
		stockStore.create(stock);
	}
}
