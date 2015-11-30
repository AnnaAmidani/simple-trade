package com.jpm.trade.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import com.jpm.trade.model.StockModel;
import com.jpm.trade.model.TradeModel;
import com.jpm.trade.util.OperationType;
import com.jpm.trade.util.StockType;
import com.jpm.trade.util.TestUtil;

/**
 * Unit test for the {@link StockCalculatorServiceImpl}.
 */
public class StockCalculatorServiceTest {

	StockCalculatorService stockCalculator = StockCalculatorServiceImpl.getInstance();

	@Test(expected=IllegalArgumentException.class)
	public void testDividendYeldUsingAnInvalidTickerPrice() {
		stockCalculator.calculateDividendYeld(TestUtil.buildStock(new BigDecimal(100), new BigDecimal(0), 22, "ABC", StockType.COMMON));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testDividendYeldUsingOfAStockWithoutTypeInvalidTickerPrice() {
		stockCalculator.calculateDividendYeld(TestUtil.buildStock(new BigDecimal(100), new BigDecimal(98), 22, "ABC", null));
	}

	@Test
	public void testDividendYeldOfACommonStock() {
		BigDecimal dividendYeld = stockCalculator.calculateDividendYeld(TestUtil.buildStock(new BigDecimal(100), new BigDecimal(98), 22, "ABC", StockType.COMMON));
		Assert.assertEquals(new BigDecimal("0.2244897959183673"), dividendYeld);		
	}

	@Test
	public void testDividendYeldOfAPreferredStock() {
		StockModel stock = TestUtil.buildStock(new BigDecimal(100), new BigDecimal(98), 22, "ABC", StockType.PREFERRED);
		stock.setFixedDividend(1.1f);
		BigDecimal dividendYeld = stockCalculator.calculateDividendYeld(stock);
		Assert.assertEquals(new BigDecimal("1.122449003920263"), dividendYeld);		
	}
	
	@Test
	public void testGeometricMean() {
		List<StockModel> list = new ArrayList<StockModel>();
		list.add(TestUtil.buildStock(new BigDecimal("113.19"), new BigDecimal("111.36"), 3, "ABC", StockType.COMMON));
		list.add(TestUtil.buildStock(new BigDecimal("30.01"), new BigDecimal("31.56"), 2, "BCD", StockType.COMMON));
		list.add(TestUtil.buildStock(new BigDecimal("78.34"), new BigDecimal("77.49"), 6, "CDE", StockType.PREFERRED));
		BigDecimal geometricMean = stockCalculator.calculateAllShareIndex(list);
		Assert.assertEquals(new BigDecimal("64.32096342909841"), geometricMean);
	}

	@Test
	public void testGeometricMeanOfASingleSotck() {
		List<StockModel> list = new ArrayList<StockModel>();
		list.add(TestUtil.buildStock(new BigDecimal("110.23"), new BigDecimal("111.36"), 12, "ABC", StockType.COMMON));
		BigDecimal geometricMean = stockCalculator.calculateAllShareIndex(list);
		Assert.assertEquals(new BigDecimal("110.23"), geometricMean);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGeometricMeanOnEmptyStockList() {
		stockCalculator.calculateAllShareIndex(new ArrayList<StockModel>());
	}

	@Test
	public void testPeRatio() {
		BigDecimal peRatio = stockCalculator.calculatePeRatio(TestUtil.buildStock(new BigDecimal("110.23"), new BigDecimal("112.56"), 12, "ABC", StockType.COMMON));
		Assert.assertEquals(new BigDecimal("9.38"), peRatio);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testPeRatioUsingAnInvalidLastDividend() {
		stockCalculator.calculatePeRatio(TestUtil.buildStock(new BigDecimal("110.23"), new BigDecimal("112.56"), 0, "ABC", StockType.COMMON));
	}

	@Test
	public void testStockPrice() {
		List<TradeModel> list = new ArrayList<TradeModel>();
		DateTime now = new DateTime();
		list.add(TestUtil.buildTrade(1, now, OperationType.BUY, 12, now.plusSeconds(34), new BigDecimal("103.04")));
		list.add(TestUtil.buildTrade(1, now.plusSeconds(12), OperationType.BUY, 33, now.plusSeconds(39), new BigDecimal("101.97")));
		list.add(TestUtil.buildTrade(1, now.plusSeconds(55), OperationType.BUY, 100, now.plusSeconds(67), new BigDecimal("100.12")));
		BigDecimal stockPrice = stockCalculator.calculateStockPrice(list);
		Assert.assertEquals(new BigDecimal("100.7826896551724"), stockPrice);
	}

	@Test
	public void testStockPriceUsingASingleTrade() {
		List<TradeModel> list = new ArrayList<TradeModel>();
		DateTime now = new DateTime();
		list.add(TestUtil.buildTrade(1, now, OperationType.BUY, 12, now.plusSeconds(34), new BigDecimal("103.04")));
		BigDecimal stockPrice = stockCalculator.calculateStockPrice(list);
		Assert.assertEquals(new BigDecimal("103.04"), stockPrice);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testStockPriceOnEmptyTradeList() {
		stockCalculator.calculateStockPrice(new ArrayList<TradeModel>());
	}
}
