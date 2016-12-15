package rocketServer;

import java.io.IOException;

import netgame.common.Hub;
import rocketBase.RateBLL;
import rocketData.LoanRequest;
import exceptions.RateException;


public class RocketHub extends Hub {

	private RateBLL _RateBLL = new RateBLL();
	
	public RocketHub(int port) throws IOException {
		super(port);
	}

	@Override
	protected void messageReceived(int ClientID, Object message) {
		System.out.println("Message Received by Hub");
		
		if (message instanceof LoanRequest) {
			resetOutput();
			
			LoanRequest lq = (LoanRequest) message;
			
			//	TODO - RocketHub.messageReceived

			//	You will have to:
			//	Determine the rate with the given credit score (call RateBLL.getRate)
			//		If exception, show error message, stop processing
			//		If no exception, continue
			//	Determine if payment, call RateBLL.getPayment
			//	
			//	you should update lq, and then send lq back to the caller(s)
			
			try {
				double loanRate = _RateBLL.getRate(lq.getiCreditScore());
				double payment = _RateBLL.getPayment(loanRate/1200, lq.getiTerm()*12, lq.getdAmount(), 0, false);
				lq.setdPayment(payment);
				lq.setdRate(loanRate);
			} catch (RateException e) {
				System.out.print("Please check the credit score");
				e.printStackTrace();
			}
			
			sendToAll(lq);
		}
	}
}
