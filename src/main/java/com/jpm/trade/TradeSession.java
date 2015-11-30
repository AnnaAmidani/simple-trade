package com.jpm.trade;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jpm.trade.model.StockModel;
import com.jpm.trade.model.TradeModel;
import com.jpm.trade.service.StockCalculatorService;
import com.jpm.trade.service.StockCalculatorServiceImpl;
import com.jpm.trade.store.StockStore;
import com.jpm.trade.store.StockStoreImpl;
import com.jpm.trade.store.TradeStore;
import com.jpm.trade.store.TradeStoreImpl;
import com.jpm.trade.util.OperationType;
import com.jpm.trade.util.StockType;
import com.jpm.trade.util.TradeConstants;
import com.jpm.trade.util.TradeUtil;

/**
 * Main class that simulates a trade session.
 * 
 * @author Anna Amidani
 */
public class TradeSession {

	protected static Logger logger = LoggerFactory.getLogger(TradeSession.class);

	public static void main(String[] args) {

		StockStore stockStore = StockStoreImpl.getInstance();
		TradeStore tradeStore = TradeStoreImpl.getInstance();

		StockCalculatorService stockCalculatorService = StockCalculatorServiceImpl.getInstance();

		initStocksStore(stockStore);

		/*
		 * For each stock:
		 * 
		 * i.   Calculate the dividend yield 
		 * ii.  Calculate the P/E Ratio 
		 * iii. Record a trade, with timestamp, quantity of shares, buy or sell indicator and price 
		 * iv.  Calculate Stock Price based on trades recorded in past 15 minutes
		 */
		List<StockModel> stocksList = stockStore.getAll();
		for (StockModel stock : stocksList) {
			logger.info("#############################");
			logger.info("Starting trading session on " + stock.getSymbol());
			// i.
			BigDecimal dividendYeld = stockCalculatorService.calculateDividendYeld(stock);
			logger.info("DividendYeld: " + TradeUtil.formatGBP(dividendYeld));
			// ii.
			BigDecimal peRatio = stockCalculatorService.calculatePeRatio(stock);
			logger.info("peRatio: " + TradeUtil.formatGBP(peRatio));
			// iii.
			loadStockTrades(tradeStore, stock);
			// iv.
			DateTime now = new DateTime();
			Interval interval = new Interval(now.minusMinutes(15), now);
			List<TradeModel> trades = tradeStore.queryByStockAndInterval(stock, interval);
			BigDecimal stockPrice = stockCalculatorService.calculateStockPrice(trades);
			logger.info("stockPrice: " + TradeUtil.formatGBP(stockPrice));
			logger.info("#############################");
			logger.info("\n\n");
		}

		/*
		 * v. Calculate the All Share Index using the geometric mean of prices
		 * for all stocks (calculation is made considering all the trades
		 * occurred on a single stock during trading session)
		 */
		for (StockModel stock : stocksList) {
			List<TradeModel> trades = tradeStore.queryByStock(stock);
			BigDecimal stockPrice = stockCalculatorService.calculateStockPrice(trades);
			stock.setParValue(stockPrice);
			stock = stockStore.update(stock);
			logger.info(stock.getSymbol() + " stock price updated to " + TradeUtil.formatGBP(stock.getParValue()));
		}

		List<StockModel> allStocks = stockStore.getAll();
		BigDecimal geometricMean = stockCalculatorService.calculateAllShareIndex(allStocks);
		logger.info("#############################");
		logger.info("All Share Index: " + TradeUtil.formatGBP(geometricMean));
		logger.info("#############################");
	}

	/**
	 * Initialize the stock store adding some stock entities to use as example.
	 * 
	 * @param store the stock store.
	 */
	private static void initStocksStore(StockStore store) {
		logger.info("Initializing the store with a set of stocks");
		store.create(buildStockEntity(1.6f, 8,  new BigDecimal("50.09"),  new BigDecimal("51.38"),  TradeConstants.FERRARI_SYM,	  StockType.COMMON));
		store.create(buildStockEntity(1.2f, 13, new BigDecimal("5.42"),   new BigDecimal("4.95"),   TradeConstants.UNICREDIT_SYM, StockType.COMMON));
		store.create(buildStockEntity(0.4f, 7,  new BigDecimal("40.21"),  new BigDecimal("42.96"),  TradeConstants.COKE_SYM, 	  StockType.PREFERRED));
		store.create(buildStockEntity(1.3f, 25, new BigDecimal("706.12"), new BigDecimal("702.50"), TradeConstants.GOOGLE_SYM, 	  StockType.PREFERRED));
		store.create(buildStockEntity(1.3f, 25, new BigDecimal("117.75"), new BigDecimal("119.20"), TradeConstants.APPLE_SYM, 	  StockType.PREFERRED));
		store.create(buildStockEntity(2.1f, 7,  new BigDecimal("138.70"), new BigDecimal("138.60"), TradeConstants.IBM_SYM, 		  StockType.COMMON));
		store.create(buildStockEntity(1.7f, 9,  new BigDecimal("19.11"),  new BigDecimal("22.30"),  TradeConstants.GUESS_SYM, 	  StockType.COMMON));
	}

	/**
	 * Simulate the loading process of the trades of a given stock.
	 * 
	 * @param store the trade store
	 * @param stock the stock
	 */
	private static void loadStockTrades(TradeStore store, StockModel stock) {

		logger.info("Initializing the store with a set of trading operations on the stock " + stock.getSymbol());
		DateTime now = new DateTime();
		DateTime startTime = now.minusMinutes(20);
		DateTime lastTime = now.minusSeconds(20);
		while (startTime.isBefore(lastTime)) {
			DateTime endTime = startTime.plusSeconds(TradeUtil.randIntInRange(10, 20));
			TradeModel trade = buildTradeEntity(stock.getId(), startTime, OperationType.getRandomOperationType(), TradeUtil.randIntInRange(2, 100), endTime,
					stock.getParValue().add(TradeUtil.randBigDecimalInRange(-2.4000, 2.4000, 4)));
			store.create(trade);
			startTime = startTime.plusSeconds(TradeUtil.randIntInRange(60, 180));
		}

	}

	/**
	 * Builds and returns a stock model.
	 * 
	 * @param fixedDividend the fixed dividend of the stock.
	 * @param lastDividend the last dividend of the stock.
	 * @param parValue the par value of the stock.
	 * @param symbol the symbol of the stock.
	 * @param type the type of the stock.
	 * 
	 * @return the built stock model.
	 */
	private static StockModel buildStockEntity(float fixedDividend, int lastDividend, BigDecimal parValue, BigDecimal tickerPrice, String symbol, StockType type) {
		StringBuilder sb = new StringBuilder();
		sb.append("adding stock => symbol:").append(symbol);
		sb.append("| fixedDividend:").append(fixedDividend);
		sb.append("| lastDividend:").append(lastDividend);
		sb.append("| parValue:").append(TradeUtil.formatGBP(parValue));
		sb.append("| tickerPrice:").append(TradeUtil.formatGBP(tickerPrice));
		sb.append("| type:").append(type);
		logger.info(sb.toString());
		StockModel model = new StockModel();
		model.setFixedDividend(fixedDividend);
		model.setLastDividend(lastDividend);
		model.setParValue(parValue);
		model.setTickerPrice(tickerPrice);
		model.setSymbol(symbol);
		model.setType(type);
		return model;
	}

	/**
	 * Builds and returns a trade model.
	 * 
	 * @param startTime the start time of the trade.
	 * @param operationType the operation type of the trade.
	 * @param sharesQnt the share quantity of the trade.
	 * @param endTime the end time of the trade.
	 * @param stockPrice the stock price of the trade.
	 * 
	 * @return the built trade model.
	 */
	private static TradeModel buildTradeEntity(long stockRef, DateTime startTime, OperationType operationType, int sharesQnt, DateTime endTime, BigDecimal stockPrice) {
		StringBuilder sb = new StringBuilder();
		sb.append("executing trade => operationType:").append(operationType);
		sb.append("; sharesQnt:").append(sharesQnt);
		sb.append("; stockPrice:").append(TradeUtil.formatGBP(stockPrice));
		sb.append("; startTime:").append(startTime);
		sb.append("; endTime:").append(endTime);
		logger.info(sb.toString());
		TradeModel model = new TradeModel();
		model.setStockRef(stockRef);
		model.setStartTime(startTime);
		model.setOperationType(operationType);
		model.setSharesQnt(sharesQnt);
		model.setStockPrice(stockPrice);
		model.setEndTime(endTime);
		return model;
	}

}
