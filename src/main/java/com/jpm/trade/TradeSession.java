package com.jpm.trade;

import com.jpm.trade.model.TradeModel;

public class TradeSession implements Runnable {

    public void run() {
    	TradeModel trade = new TradeModel();
        System.out.println("Hello from a thread!");
    }

    public static void main(String args[]) {
        (new Thread(new TradeSession())).start();
    }

}