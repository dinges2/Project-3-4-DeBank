import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

public class ATM {
	
	// these must be declared here to be seen in the Action classes
	JFrame welcomeFrame;
	JFrame mainFrame;
	JFrame inputFrame;
		
	JTextField numberField;
	JTextField pinField;
	JLabel verifyLabel;
	JLabel outLabel;
	
	JButton depositButton;
	JButton withdrawButton;
	JButton makeDepositButton;
	JButton makeWithdrawalButton;
	
	BankAccount theAccount;
	NumberFormat currency;
	double theAmount;
	int width;
	int height;
	ArrayList accounts;
	
	public ATM() {
						
		int xLocation = 200;
		int yLocation = 200;
		
		// define frames
		mainFrame = new JFrame("ATM");
		welcomeFrame = new JFrame("Welcome to the ATM");
		inputFrame = new JFrame("Enter Amount");
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		inputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		welcomeFrame.setLocation(xLocation,yLocation);
		mainFrame.setLocation(xLocation,yLocation);
		inputFrame.setLocation(xLocation,yLocation);
		
		JPanel welcomePanel = new JPanel();
		welcomePanel.setLayout(new FlowLayout(FlowLayout.CENTER,200,20));
		welcomePanel.setPreferredSize(new Dimension(400,200));
		
		JLabel pinLabel = new JLabel("Enter pin and Press Enter");
		pinField = new JTextField(10);
		pinField.addActionListener(new PinFieldAction());
		welcomePanel.add(pinLabel);
		welcomePanel.add(pinField);
		
		JButton nextButton = new JButton("Next");
		nextButton.addActionListener(new NextButtonAction());
		//welcomePanel.add(nextButton);
				
		// the main frame panel
		JPanel thePanel = new JPanel();
		width = 500;
		height = 400;
		thePanel.setPreferredSize(new Dimension(width,height));
		thePanel.setBackground(Color.LIGHT_GRAY);
		thePanel.setLayout(new FlowLayout(FlowLayout.CENTER,500,20));		
		
		JPanel inputPanel = new JPanel();
		
		JLabel numberLabel = new JLabel("Type Amount and Press Enter");
		numberField = new JTextField(10);
		numberField.addActionListener(new NumberFieldAction());
		inputPanel.add(numberLabel);
		inputPanel.add(numberField);
		
		JPanel verifyPanel = new JPanel();
		verifyLabel = new JLabel("No Amount Entered");
		verifyPanel.add(verifyLabel);
		
		JPanel choosePanel = new JPanel();
		JLabel chooseLabel = new JLabel("Choose Transaction");
		choosePanel.add(chooseLabel);
		
		JPanel buttonPanel = new JPanel();
		JPanel makeButtonPanel = new JPanel();
		
		JButton balanceButton = new JButton("Balance");
		balanceButton.addActionListener(new BalanceButtonAction());
		buttonPanel.add(balanceButton);
		
		depositButton = new JButton("Deposit");
		depositButton.addActionListener(new DepositButtonAction());
		buttonPanel.add(depositButton);		
		
		withdrawButton = new JButton("Withdraw");
		withdrawButton.addActionListener(new WithdrawButtonAction());
		buttonPanel.add(withdrawButton);
		
		makeDepositButton = new JButton("Make Deposit");
		makeDepositButton.addActionListener(new MakeDepositButtonAction());
		makeButtonPanel.add(makeDepositButton);
		
		makeWithdrawalButton = new JButton("Make Withdrawal");
		makeWithdrawalButton.addActionListener(new MakeWithdrawalButtonAction());
		makeButtonPanel.add(makeWithdrawalButton);
		
		JPanel finishButtonPanel = new JPanel();
		JButton finishButton = new JButton("Finish");
		finishButton.addActionListener(new FinishButtonAction());
		finishButtonPanel.add(finishButton);
		
		JPanel outPanel = new JPanel();
		outPanel.setBackground(Color.LIGHT_GRAY);
		outLabel = new JLabel("");		
		outLabel.setForeground(Color.RED);
		outLabel.setFont(new Font("Courier",Font.BOLD,20));		
		outPanel.add(outLabel);

		thePanel.add(choosePanel);
		thePanel.add(verifyPanel);
		thePanel.add(buttonPanel);		
		thePanel.add(makeButtonPanel);
		thePanel.add(finishButtonPanel);
		thePanel.add(outPanel);
		
		welcomeFrame.getContentPane().add(welcomePanel);
		welcomeFrame.pack();
		welcomeFrame.setVisible(true);
		
		mainFrame.getContentPane().add(thePanel);
		mainFrame.pack();
		
		inputFrame.getContentPane().add(inputPanel);
		inputFrame.pack();
		
		currency = NumberFormat.getCurrencyInstance();
		
		// Initialize the database
		// Load the data base
		accounts = getAccountDataFromFile("accounts.data");
		
	} // end constructor
	
	ArrayList getAccountDataFromFile(String fileName) {
		try{
			ArrayList accounts = new ArrayList();
			
			// add code here to read lines from the file
			// (each line contains an initial balance)
			// and add BankAccount objects to the ArrayList
			
			return accounts;		
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null,"File Error. Program Aborted");
			System.exit(0);
			return accounts;
		} // end try/catch			
	} // end getAccountDataFromFile
	
	class PinFieldAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			// Get the pin number, verify that it is legal
			// and then get the account from the ArrayList accounts
			// and remove the following statement
			theAccount = new BankAccount(1000000);
			
			welcomeFrame.setVisible(false);
			mainFrame.setVisible(true);
			pinField.setText("");
		} // end actionPerformed
	} // end PinFieldAction
	
	class NumberFieldAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String input = numberField.getText();
			if( ! isInputOK(input) ) {
				JOptionPane.showMessageDialog(null,"Invalid input. Try again.");
				numberField.requestFocus();
				return;
			} // end if
			theAmount = Double.parseDouble(input);
			welcomeFrame.setVisible(false);
			inputFrame.setVisible(false);
			mainFrame.setVisible(true);
			verifyLabel.setText("Amount Entered was " + currency.format(theAmount));
		} // end actionPerformed
	} // end NumberButtonAction
	
	class BalanceButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			numberField.requestFocus();
			drawBalance(outLabel,theAccount);
		} // end actionPerformed
	} // end BalanceButtonAction
	
	class DepositButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			inputFrame.setVisible(true);
			numberField.setText("");
			numberField.requestFocus();
			withdrawButton.setEnabled(false);
			makeWithdrawalButton.setEnabled(false);
		} // end actionPerformed
	} // end DepositButtonAction
	
	class WithdrawButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			inputFrame.setVisible(true);
			numberField.setText("");
			numberField.requestFocus();
			depositButton.setEnabled(false);
			makeDepositButton.setEnabled(false);
		} // end actionPerformed
	} // end WithdrawButtonAction
	
	class MakeDepositButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			// add code here to make the deposit
			
			withdrawButton.setEnabled(true);
			makeWithdrawalButton.setEnabled(true);
		} // end actionPerformed
	} // end MakeDepositButtonAction
	
	class MakeWithdrawalButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			// add code here to make the withdrawal
			
			depositButton.setEnabled(true);
			makeDepositButton.setEnabled(true);
		} // end actionPerformed
	} // end MakeWithDrawalButtonAction
	
	class NextButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			pinField.setText("");
			pinField.requestFocus(true);
			welcomeFrame.setVisible(false);
			mainFrame.setVisible(true);
		} // end actionPerformed
	} // end NextButtonAction
	
	class FinishButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			outLabel.setText("");
			mainFrame.setVisible(false);
			inputFrame.setVisible(false);
			welcomeFrame.setVisible(true);
			pinField.requestFocus();
			depositButton.setEnabled(true);
			withdrawButton.setEnabled(true);
		} // end actionPerformed
	} // end FinishButtonAction
	
	boolean isInputOK(String input) {
			input = input.trim();
			if( input.equals("") ) {
				return false;
			} // end if
			int decimalIndex = input.indexOf(".");
			if( decimalIndex == -1 ) {
				return isAllDigits(input);
			} else {
				String first = input.substring(0,decimalIndex);
				String last = input.substring(decimalIndex+1);
				boolean firstAndLastAllDigits = isAllDigits(first) && isAllDigits(last);
				boolean lastlengthOK = last.length() == 2;
				return firstAndLastAllDigits && lastlengthOK;
			} // end if
	} // end is InputOK
	
	boolean isAllDigits(String input) {
		int index = 0;
		while( index < input.length() ) {
			if( ! Character.isDigit(input.charAt(index) ) ) {
				return false;
			} // end if
			index++;
		} // end while
		return true;
	} // end isAllDigits
	
	boolean isAmountOK(double amount)	 {
		if( amount <= 0.0 ) {
			JOptionPane.showMessageDialog(null, "Enter an Positive Amount First");
			return false;
		} // end if
		return true;
	} // isAmountOK
	
	void drawBalance(JLabel outLabel, BankAccount account) {
		outLabel.setText("The balance is " + currency.format(theAccount.getBalance()) + ".");
	} // end drawBalance
		
	public static void main(String[] args) {
		new ATM();		
	} // end main
	
} // end ATM