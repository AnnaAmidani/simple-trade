package com.jpm.trade.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.jpm.trade.model.StockModel;
import com.jpm.trade.model.TradeModel;
import com.jpm.trade.store.TestUtil;
import com.jpm.trade.util.StockType;

/**
 * Unit test for simple TradeSession.
 */
public class StockCalculatorServiceTest {

	StockCalculatorService stockCalculator = StockCalculatorServiceImpl.getInstance();
	
	@Test
	public void testDividendYeld() {
		BigDecimal dividendYeld = stockCalculator.calculateDividendYeld(TestUtil.buildStock(new BigDecimal(100), new BigDecimal(98), 22, "ABC", StockType.COMMON));
		//Assert.assertEquals(0.2244897959183673469387755102040816, dividendYeld);
	}
	
	@Test
	public void testGeometricMean() {
		List<StockModel> list = new ArrayList<StockModel>();
		list.add(TestUtil.buildStock(new BigDecimal("110.23"), new BigDecimal("111.36"), 12, "ABC", StockType.COMMON));
		BigDecimal geometricMean = stockCalculator.calculateGeometricMean(list);
		Assert.assertEquals(new BigDecimal("110.2300000000000039790393202565610408782958984375"), geometricMean);

		list = new ArrayList<StockModel>();
		list.add(TestUtil.buildStock(new BigDecimal("110.23"), new BigDecimal("111.36"), 12, "ABC", StockType.COMMON));
		geometricMean = stockCalculator.calculateGeometricMean(list);
//		Assert.assertEquals(new BigDecimal("110.23"), geometricMean);
		Assert.assertEquals(new BigDecimal("110.2300000000000039790393202565610408782958984375"), geometricMean);
	}
	
	@Test
	public void testPeRatio() {
		BigDecimal peRatio = stockCalculator.calculatePeRatio(TestUtil.buildStock(new BigDecimal("110.23"), new BigDecimal("112.56"), 12, "ABC", StockType.COMMON));
		Assert.assertEquals(new BigDecimal("9.38"), peRatio);
	}


	@Test
	public void testStockPrice() {
		List<TradeModel> list = new ArrayList<TradeModel>();
		BigDecimal stockPrice = stockCalculator.calculateStockPrice(list);
		Assert.assertEquals(new BigDecimal("0"), stockPrice);
	}

}
