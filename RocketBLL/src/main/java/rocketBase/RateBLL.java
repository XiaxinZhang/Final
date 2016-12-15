package rocketBase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.poi.ss.formula.functions.*;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import exceptions.RateException;
import rocketDomain.RateDomainModel;
import util.HibernateUtil;
import rocketBase.RateDAL;
import rocketData.LoanRequest;

public class RateBLL {

	private static RateDAL _RateDAL = new RateDAL();
	
	public static double getRate(int GivenCreditScore) throws RateException
	{
		//TODO - RocketBLL RateBLL.getRate - make sure you throw any exception
		
		//		Call RateDAL.getAllRates... this returns an array of rates
		//		write the code that will search the rates to determine the 
		//		interest rate for the given credit score
		//		hints:  you have to sort the rates...  you can do this by using
		//			a comparator... or by using an OrderBy statement in the HQL
		double matchingRate = 0;
		ArrayList<RateDomainModel> rates = _RateDAL.getAllRates();
		
		try {
			System.out.print("The given score is " + GivenCreditScore);
			
			if (GivenCreditScore < 600) {
				System.out.print("You are not eligible for Mortgage");
			}
			
			for (RateDomainModel rate : rates) {
				if (GivenCreditScore >= rate.getiMinCreditScore()) {
					matchingRate = rate.getdInterestRate();
				}
			}
			if (matchingRate == 0) {
				LoanRequest lq = new LoanRequest();
				lq.setiCreditScore(GivenCreditScore);
				throw new RateException(lq);
			}
		} catch (RateException e) {
			System.out.print("There is not enough credit score from RateBLL");
		}
		
		//TODO - RocketBLL RateBLL.getRate
		//			obviously this should be changed to return the determined rate
		System.out.print("The Rate is " + matchingRate);
		return matchingRate;
		
		
	}
	
	
	//TODO - RocketBLL RateBLL.getPayment 
	//		how to use:
	//		https://poi.apache.org/apidocs/org/apache/poi/ss/formula/functions/FinanceLib.html
	
	public static double getPayment(double r, double n, double p, double f, boolean t)
	{		
		return FinanceLib.pmt(r, n, p, f, t);
	}
}
