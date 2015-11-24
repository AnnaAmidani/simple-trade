package com.jpm.trade;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jpm.trade.model.StockModel;
import com.jpm.trade.model.TradeModel;
import com.jpm.trade.service.StockCalculatorServiceImpl;
import com.jpm.trade.store.StockStore;
import com.jpm.trade.store.TradeStore;
import com.jpm.trade.util.OperationType;
import com.jpm.trade.util.StockType;
import com.jpm.trade.util.TradeConstants;
import com.jpm.trade.util.TradeUtil;

/**
 * 
 * 
 */
public class SuperSimpleTrade {

	protected static Logger logger = LoggerFactory.getLogger(SuperSimpleTrade.class);
	  
	public static void main(String[] args) {
		StockCalculatorServiceImpl stockCalculatorServiceinstance = StockCalculatorServiceImpl.getInstance();

		StockStore stockStore = StockStore.getInstance();
		TradeStore tradeStore = TradeStore.getInstance();
			
		initStocksStore(stockStore);

		/**
		 * For each stock: 
		 * i.   Calculate the dividend yield 
		 * ii.  Calculate the P/E Ratio 
		 * iii. Record a trade, with timestamp, quantity of shares, buy or sell indicator and price 
		 * iv.  Calculate Stock Price based on trades recorded in past 15 minutes
		 **/
		List<StockModel> stocksList = stockStore.getAll();
		for (StockModel stock : stocksList) {
			logger.info("#############################");
			logger.info("Analizing "+stock.getSymbol());
			// i.
			double dividendYeld = stockCalculatorServiceinstance.getDividendYeld(stock);
			logger.info("DividendYeld: "+dividendYeld);
			// ii.
			double peRatio = stockCalculatorServiceinstance	.getPeRatio(stock);
			logger.info("peRatio: "+peRatio);
			// iii.
			initTradesStore(tradeStore, stock);
			// iv.
			double stockPrice = stockCalculatorServiceinstance.getStockPrice(tradeStore.getAll());
			logger.info("stockPrice: "+stockPrice);

			//cleaning the trade for the next turn
			tradeStore.deleteAll();
			logger.info("#############################");
			logger.info("\n\n");
		}
		
		/**
		 * 
		  * v. Calculate the All Share Index using the geometric mean of prices for all stocks
		  * 
		 **/
		List<StockModel> allStocks = stockStore.getAll();
		double geometricMean = stockCalculatorServiceinstance.getGeometricMean(allStocks);
		logger.info("#############################");
		logger.info("All Share Index: " +geometricMean);
		logger.info("\n\n");

	}
	
	/**
	 * 
	 * @param store
	 */
	private static void initStocksStore(StockStore store) {
		logger.info("Initializing the store with a set of stocks");
		store.create(buildEntity(1.6f, 8, 50.09, 51.38,
				TradeConstants.FERRARI_SYM, StockType.COMMON));
		store.create(buildEntity(1.2f, 13, 5.42, 4.95,
				TradeConstants.UNICREDIT_SYM, StockType.COMMON));
		store.create(buildEntity(0.4f, 7, 40.21, 42.96,
				TradeConstants.COKE_SYM, StockType.PREFERRED));
		store.create(buildEntity(1.3f, 25, 706.12, 702.50,
				TradeConstants.GOOGLE_SYM, StockType.PREFERRED));
		store.create(buildEntity(1.3f, 25, 117.75, 119.20,
				TradeConstants.APPLE_SYM, StockType.PREFERRED));
		store.create(buildEntity(2.1f, 7, 138.70, 138.60,
				TradeConstants.IBM_SYM, StockType.COMMON));
		store.create(buildEntity(1.7f, 9, 19.11, 22.30,
				TradeConstants.GUESS_SYM, StockType.COMMON));
	}

	
	/**
	 * 
	 * @param store
	 * @param stock
	 */
	private static void initTradesStore(TradeStore store, StockModel stock) {
		logger.info("Initializing the store with a set of trading operations on the given stock");
		int tradesNumber = TradeUtil.randIntInRange(5, 25);
		logger.info("Trades operation number: "+tradesNumber);
		for(int i = 0; i < tradesNumber; i++) {
			DateTime now = new DateTime();
			store.create(
					buildTradeEntity(
							stock.getId(), 
							now.minusMinutes(10), 
							OperationType.getRandomOperationType(), 
							TradeUtil.randIntInRange(2, 100), 
							now.plusMinutes(TradeUtil.randIntInRange(1, 20)), 
							TradeUtil.randDoubleInRange(stock.getParValue(), (stock.getParValue()+TradeUtil.randDoubleInRange(1.4, 10.20)))
					)
			);
		}

	}

	/**
	 * 
	 * @param fixedDividend
	 * @param lastDividend
	 * @param parVaue
	 * @param symbol
	 * @param type
	 * @return the model
	 */
	private static StockModel buildEntity(float fixedDividend,
			int lastDividend, double parValue, double tickerPrice,
			String symbol, StockType type) {
		logger.info("----------------------");
		logger.info("symbol:"+symbol);
		logger.info("fixedDividend:"+fixedDividend);
		logger.info("lastDividend:"+lastDividend);
		logger.info("parValue:"+parValue);
		logger.info("tickerPrice:"+tickerPrice);
		logger.info("type:"+type);
		logger.info("----------------------");
		logger.info("\n\n");
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
	 * 
	 * @param startTime
	 * @param operationType
	 * @param sharesQnt
	 * @param endTime
	 * @param stockPrice
	 * @return the model
	 */
	private static TradeModel buildTradeEntity(long stockRef, DateTime startTime,
			OperationType operationType, int sharesQnt, DateTime endTime,
			double stockPrice) {
		logger.info("----------------------");
		logger.info("startTime:"+startTime);
		logger.info("operationType:"+operationType);
		logger.info("sharesQnt:"+sharesQnt);
		logger.info("stockPrice:"+stockPrice);
		logger.info("endTime:"+endTime);
		logger.info("----------------------");
		logger.info("\n\n");
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
