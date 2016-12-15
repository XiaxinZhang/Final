package rocketBase;

import static org.junit.Assert.*;

import org.junit.Test;

import exceptions.RateException;

public class rate_test {

	//TODO - RocketBLL rate_test
	//		Check to see if a known credit score returns a known interest rate
	
	//TODO - RocketBLL rate_test
	//		Check to see if a RateException is thrown if there are no rates for a given
	//		credit score
	@Test
	public void test() {
		assert(1==1);
	}
	
	@Test
	public void getRate_test() throws RateException {
		assertTrue(0 == RateBLL.getRate(550));
		assertEquals(5.0, RateBLL.getRate(630),0);
		assertEquals(4.0, RateBLL.getRate(720),0);
		assertEquals(3.5, RateBLL.getRate(810),0);
	}

}
