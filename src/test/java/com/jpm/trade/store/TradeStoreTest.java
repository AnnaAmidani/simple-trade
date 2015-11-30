package com.jpm.trade.store;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jpm.trade.model.TradeModel;
import com.jpm.trade.util.OperationType;
import com.jpm.trade.util.TestUtil;

/**
 * Unit test for the {@link TradeStore}.
 */
public class TradeStoreTest {

	TradeStore tradeStore = TradeStoreImpl.getInstance();

	@Before
	public void cleanTradeStore() {
		Assert.assertTrue(tradeStore.deleteAll());
		List<TradeModel> all = tradeStore.getAll();
		Assert.assertNotNull(all);
		Assert.assertTrue(all.isEmpty());
	}
	
	
	@Test
	public void testStockCreation() {		

		long stockRef = 1;
		DateTime startTime = new DateTime();
		OperationType operationType = OperationType.BUY;
		int sharesQnt = 12;
		DateTime endTime = startTime.plusSeconds(45);
		BigDecimal stockPrice = new BigDecimal("98.34");
		
		TradeModel created = tradeStore.create(TestUtil.buildTrade(stockRef, startTime, operationType, sharesQnt, endTime, stockPrice));
		
		Assert.assertNotNull(created);
		Assert.assertEquals(stockRef, created.getStockRef());
		Assert.assertEquals(startTime, created.getStartTime());
		Assert.assertEquals(operationType, created.getOperationType());
		Assert.assertEquals(sharesQnt, created.getSharesQnt());
		Assert.assertEquals(endTime, created.getEndTime());
		Assert.assertEquals(stockPrice, created.getStockPrice());
	}

	
	@Test
	public void testGetAllStock() {
		DateTime now = new DateTime();
		tradeStore.create(TestUtil.buildTrade(1, now.plusSeconds(1), OperationType.BUY, 12, now.plusSeconds(34), new BigDecimal("13.04")));
		tradeStore.create(TestUtil.buildTrade(1, now.plusSeconds(23), OperationType.BUY, 2, now.plusSeconds(41), new BigDecimal("2.06")));
		
		List<TradeModel> all = tradeStore.getAll();
		
		Assert.assertNotNull(all);
		Assert.assertEquals(2, all.size());
		for (TradeModel trade : all) {
			Assert.assertNotNull(trade);
			Assert.assertNotNull(trade.getEndTime());
			Assert.assertTrue(trade.getEndTime().isAfter(now));
			Assert.assertTrue(trade.getId() > 0);
			Assert.assertEquals(trade.getOperationType(), OperationType.BUY);
			Assert.assertTrue(trade.getSharesQnt() > 0);
			Assert.assertNotNull(trade.getStartTime());
			Assert.assertTrue(trade.getStartTime().isAfter(now));
			Assert.assertNotNull(trade.getStockPrice());
			Assert.assertEquals(trade.getStockRef(), 1);
		}
	}

	@Test
	public void testDeleteAllStock() {
		DateTime now = new DateTime();
		tradeStore.create(TestUtil.buildTrade(1, now.plusSeconds(1), OperationType.BUY, 12, now.plusSeconds(34), new BigDecimal("13.04")));
		tradeStore.create(TestUtil.buildTrade(1, now.plusSeconds(23), OperationType.BUY, 2, now.plusSeconds(41), new BigDecimal("2.06")));
		
		List<TradeModel> all = tradeStore.getAll();		
		Assert.assertNotNull(all);
		Assert.assertEquals(2, all.size());
		
		Assert.assertTrue(tradeStore.deleteAll());
		all = tradeStore.getAll();
		Assert.assertNotNull(all);
		Assert.assertTrue(all.isEmpty());
	}
	

	@Test
	public void testDeleteStock() {
		DateTime now = new DateTime();
		TradeModel trade = tradeStore.create(TestUtil.buildTrade(1, now.plusSeconds(1), OperationType.BUY, 12, now.plusSeconds(34), new BigDecimal("13.04")));
		TradeModel toDelete = tradeStore.create(TestUtil.buildTrade(1, now.plusSeconds(23), OperationType.BUY, 2, now.plusSeconds(41), new BigDecimal("2.06")));
		
		Assert.assertEquals(2, tradeStore.getAll().size());
		
		TradeModel deleted = tradeStore.delete(toDelete);

		Assert.assertEquals(1, tradeStore.getAll().size());
		Assert.assertEquals(toDelete.getId(), deleted.getId());
		Assert.assertNotEquals(trade.getId(), deleted.getId());
		Assert.assertEquals(trade.getId(), tradeStore.getAll().get(0).getId());
	}
}
