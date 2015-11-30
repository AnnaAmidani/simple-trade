package com.jpm.trade.store;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jpm.trade.model.StockModel;
import com.jpm.trade.util.StockType;
import com.jpm.trade.util.TestUtil;

/**
 * Unit test for the {@link StockStore}.
 */
public class StockStoreTest {

	StockStore stockStore = StockStoreImpl.getInstance();

	@Before
	public void cleanStockStore() {
		Assert.assertTrue(stockStore.deleteAll());
		List<StockModel> all = stockStore.getAll();
		Assert.assertNotNull(all);
		Assert.assertTrue(all.isEmpty());
	}
	
	
	@Test
	public void testStockCreation() {		
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
		stockStore.create(TestUtil.buildStock(new BigDecimal(10), new BigDecimal(12), 3, "ALE", StockType.COMMON));
		stockStore.create(TestUtil.buildStock(new BigDecimal(10), new BigDecimal(12), 3, "ALE", StockType.COMMON));
		
		List<StockModel> all = stockStore.getAll();
		
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
		stockStore.create(TestUtil.buildStock(new BigDecimal(10), new BigDecimal(12), 2, "ALE", StockType.COMMON));
		stockStore.create(TestUtil.buildStock(new BigDecimal(10), new BigDecimal(12), 2, "ALE", StockType.COMMON));
		
		Assert.assertTrue(stockStore.deleteAll());
		List<StockModel> all = stockStore.getAll();
		Assert.assertNotNull(all);
		Assert.assertTrue(all.isEmpty());
	}

	@Test
	public void testDeleteStock() {
		StockModel stock = stockStore.create(TestUtil.buildStock(new BigDecimal(10), new BigDecimal(12), 2, "ALE", StockType.COMMON));
		StockModel toDelete = stockStore.create(TestUtil.buildStock(new BigDecimal(10), new BigDecimal(12), 2, "ALE", StockType.COMMON));
		
		Assert.assertEquals(2, stockStore.getAll().size());
		
		StockModel deleted = stockStore.delete(toDelete);

		Assert.assertEquals(1, stockStore.getAll().size());
		Assert.assertEquals(toDelete.getId(), deleted.getId());
		Assert.assertNotEquals(stock.getId(), deleted.getId());
		Assert.assertEquals(stock.getId(), stockStore.getAll().get(0).getId());
	}
}
