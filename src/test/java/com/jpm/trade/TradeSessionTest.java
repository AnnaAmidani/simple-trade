package com.jpm.trade;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple TradeSession.
 */
public class TradeSessionTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TradeSessionTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TradeSessionTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testTradeSession()
    {
        assertTrue( true );
    }
}
