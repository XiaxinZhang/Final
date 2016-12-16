package rocket.app.view;

import java.net.URL;
import java.util.ResourceBundle;

import eNums.eAction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import rocket.app.MainApp;
import rocketBase.RateBLL;
import rocketCode.Action;
import rocketData.LoanRequest;

public class MortgageController implements Initializable {

	private MainApp mainApp;
	
	//	TODO - RocketClient.RocketMainController
	
	//	Create private instance variables for:
	//		TextBox  - 	txtIncome
	//		TextBox  - 	txtExpenses
	//		TextBox  - 	txtCreditScore
	//		TextBox  - 	txtHouseCost
	//		ComboBox -	loan term... 15 year or 30 year
	//		Labels   -  various labels for the controls
	//		Button   -  button to calculate the loan payment
	//		Label    -  to show error messages (exception throw, payment exception)
	private double txtIncome;
	private double txtExpenses;
	private int txtCreditScore;
	private double txtHouseCost;
	private int term;
	
	//Labels
	@FXML
	private Label incomeLabel;
	
	@FXML
	private Label creditscoreLabel;
	
	@FXML
	private Label termLabel;
	
	@FXML
	private Label expenseLabel;
	
	@FXML
	private Label housecostLabel;
	
	@FXML
	private Label mortgageLabel;
	
	@FXML
	private Label costtoohighLabel;
	
	//TestFields
	@FXML
	private TextField incomeTextField;
	
	@FXML
	private TextField expenseTextField;
	
	@FXML
	private TextField creditscoreTextField;
	
	@FXML
	private TextField housecostTextField;
	
	//ComboBox
	@FXML
	private ComboBox<Integer> termComboBox;
	
	

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		costtoohighLabel.setVisible(false);
		termComboBox.getItems().addAll(15,30);
	}
	
	
	//	TODO - RocketClient.RocketMainController
	//			Call this when btnPayment is pressed, calculate the payment
	@FXML
	public void btnCalculatePayment(ActionEvent event)
	{
		Object message = null;
		//	TODO - RocketClient.RocketMainController
		
		Action a = new Action(eAction.CalculatePayment);
		LoanRequest lq = new LoanRequest();
		//	TODO - RocketClient.RocketMainController
		//			set the loan request details...  rate, term, amount, credit score, downpayment
		//			I've created you an instance of lq...  execute the setters in lq

		lq.setIncome(Double.valueOf(incomeTextField.getText()));
		lq.setExpense(Double.valueOf(expenseTextField.getText()));
		lq.setiTerm(Integer.valueOf(termComboBox.getValue()));
		lq.setiCreditScore(Integer.valueOf(creditscoreTextField.getText()));
		lq.setdAmount(Double.valueOf(housecostTextField.getText()));
		lq.setdPayment(RateBLL.getPayment(lq.getdRate()/1200, lq.getiTerm()*12, lq.getdAmount(), 0, false)/(lq.getiTerm()*12));
		
		a.setLoanRequest(lq);
		
		//	send lq as a message to RocketHub		
		mainApp.messageSend(lq);
	}
	
	public void HandleLoanRequestDetails(LoanRequest lRequest)
	{
		//	TODO - RocketClient.HandleLoanRequestDetails
		//			lRequest is an instance of LoanRequest.
		//			after it's returned back from the server, the payment (dPayment)
		//			should be calculated.
		//			Display dPayment on the form, rounded to two decimal places
		
		double payment = Math.abs(lRequest.getdPayment());
		String finalResult = String.format("%1$.02f", String.valueOf(payment));
		
		if (lRequest.getiTerm() == 30 && payment <  (lRequest.getIncome() -lRequest.getExpense() * 0.28) 
				|| lRequest.getiTerm() == 15 && payment <  (lRequest.getIncome() -lRequest.getExpense())*0.28) {
			
			mortgageLabel.setText("The monthly payment is $" + finalResult);
		} else {
			mortgageLabel.setVisible(false);
			costtoohighLabel.setVisible(true);
			costtoohighLabel.setText("House cost is too high.");
		}
	}
}
