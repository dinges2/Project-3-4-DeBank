package ATM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.beans.*; 

public class PinAutomaat{

    JFrame mainFrame;
    JPanel startingScreenPanel;
    JPanel enterPinPanel;
    JPanel mainMenuPanel;
    JPanel withdrawPanel;
    JPanel amountPanel;
    JPanel balancePanel;
    JPanel billsPanel;
    JPanel billChoicePanel;
    JPanel receiptPanel;
    JPanel thanksPanel;
    
    private JPasswordField passwordField;
    private JFormattedTextField amountField;
    private NumberFormat amountFormat;
    private int amount;
    private static int amountPressed = 0;
    private static String accountBalance;
    static int[] noOfBills = {0, 0, 0};

    private JButton abortButton;
    private JButton enterButton;
    private JButton withdrawButton;
    private JButton enterAmountButton;
    private JButton balanceButton;
    private JButton quickSeventyButton;
    private JButton backToMainMenuButton;
    private JButton optionOneButton;
    private JButton optionTwoButton;
    
    private JLabel startingScreenLabel;
    private JLabel enterPinLabel;
    private JLabel mainMenuLabel;
    private JLabel withdrawLabel;
    private JLabel amountLabel;
    private static JLabel balanceLabel;
    private JLabel accountBalanceLabel;
    private JLabel billsLabel;
    private JLabel billChoiceLabel;
    private JLabel receiptLabel;
    private JLabel thanksLabel;

    public void messagePin(){

        JPanel warning = new JPanel();
        mainFrame.add(warning);
        GridBagConstraints grid = new GridBagConstraints();
        warning.setLayout(new GridBagLayout());

        JLabel errorMessage = new JLabel();
        errorMessage.setFont(new Font("Roboto", Font.BOLD, 40));
        errorMessage.setText("Uw pincode is get");
        grid.anchor = GridBagConstraints.CENTER;
        warning.add(errorMessage, grid);
        mainFrame.setVisible(true);

        Timer timer = new Timer(2000, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                mainFrame.remove(warning);
                enterPinPanel = null;
                enterPin();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void messageBlock(){

        JPanel warning = new JPanel();
        mainFrame.add(warning);
        GridBagConstraints grid = new GridBagConstraints();
        warning.setLayout(new GridBagLayout());

        JLabel errorMessage = new JLabel();
        errorMessage.setFont(new Font("Roboto", Font.BOLD, 40));
        errorMessage.setText("Uw pas is get");
        grid.anchor = GridBagConstraints.CENTER;
        warning.add(errorMessage, grid);
        mainFrame.setVisible(true);

        Timer timer = new Timer(2000, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                mainFrame.remove(warning);
                startingScreenPanel = null;
                startingScreen();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void messageInsufficient(String message){

        JPanel warning = new JPanel();
        mainFrame.add(warning);
        GridBagConstraints grid = new GridBagConstraints();
        warning.setLayout(new GridBagLayout());

        JLabel errorMessage = new JLabel();
        errorMessage.setFont(new Font("Roboto", Font.BOLD, 40));
        errorMessage.setText(message);
        grid.anchor = GridBagConstraints.CENTER;
        warning.add(errorMessage, grid);
        mainFrame.setVisible(true);

        Timer timer = new Timer(2000, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                mainFrame.remove(warning);
                withdrawPanel = null;
                withdraw();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public PinAutomaat(){

        mainFrame = new JFrame("Pinautomaat GUI");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1280, 720);
        mainFrame.setResizable(true);

        startingScreen();
        mainFrame.setVisible(true);
        
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public void setAmountField(int keypressed) {
        amountField.setValue(keypressed);
    }

    public static void setAmountPressed(int i) {
        amountPressed = i;
    }
    public static void setBalance(String s) {
        accountBalance = s;
    }

    public void setPasswordField(String s) {
        this.passwordField.setText(s);
    }
    
    public String getPasswordField() {
        return this.passwordField.getText();
    }

    public void startingScreen(){
        
        startingScreenPanel = new JPanel();
        mainFrame.add(startingScreenPanel);
        GridBagConstraints grid = new GridBagConstraints();
        startingScreenPanel.setLayout(new GridBagLayout());

        startingScreenLabel = new JLabel();
        startingScreenLabel.setFont(new Font("Roboto", Font.BOLD, 80));
        startingScreenLabel.setText("Houdt uw pinpas voor de lezer.");
        grid.gridx = 1;
        grid.gridy = 0;
        grid.weighty = 1.0;
        grid.anchor = GridBagConstraints.CENTER;
        startingScreenPanel.add(startingScreenLabel, grid);

        JButton OkButton = new JButton("OK");
        grid.gridx = 1;
        grid.gridy = 2;
        startingScreenPanel.add(OkButton, grid);
        mainFrame.setVisible(true);

        OkButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                mainFrame.remove(startingScreenPanel);          
                enterPinPanel = null;
                enterPin();
            }
        });
    }

    public void enterPin(){
        
        enterPinPanel = new JPanel();
        mainFrame.add(enterPinPanel);
        GridBagConstraints grid = new GridBagConstraints();
        enterPinPanel.setLayout(new GridBagLayout());

        enterPinLabel = new JLabel();
        enterPinLabel.setFont(new Font("Roboto", Font.BOLD, 60));
        enterPinLabel.setText("Voer uw pincode in.");
        grid.gridx = 1;
        grid.gridy = 0;
        grid.weighty = 1.0;
        grid.anchor = GridBagConstraints.PAGE_START;
        enterPinPanel.add(enterPinLabel, grid);

        passwordField = new JPasswordField(4);
        passwordField.setFont(new Font("Roboto", Font.BOLD, 25));
        grid.gridx = 0;
        grid.gridy = 2;
        grid.ipadx = 40;
        grid.gridwidth = 3;
        enterPinPanel.add(passwordField, grid);
        

        enterButton = new JButton("[*]   Enter");
        enterButton.setFont(new Font("Roboto", Font.BOLD, 25));
        grid.gridx = 0;
        grid.gridy = 3;
        grid.ipadx = 0;
        grid.gridwidth = 1;
        grid.fill = GridBagConstraints.HORIZONTAL;
        enterPinPanel.add(enterButton, grid);

        abortButton = new JButton("[#]   Afbreken");
        abortButton.setFont(new Font("Roboto", Font.BOLD, 25));
        grid.gridx = 2;
        grid.gridy = 3;
        grid.gridwidth = 1;
        grid.fill = GridBagConstraints.HORIZONTAL;
        enterPinPanel.add(abortButton, grid);
        mainFrame.setVisible(true);

        enterButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                
                    mainFrame.remove(enterPinPanel);
                    messagePin();
            }
        });

        abortButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                mainFrame.remove(enterPinPanel);
                startingScreenPanel = null;
                startingScreen();  
            }
        });
    }

    public void mainMenu(){

        mainMenuPanel = new JPanel();
        mainFrame.add(mainMenuPanel);
        GridBagConstraints grid = new GridBagConstraints();
        mainMenuPanel.setLayout(new GridBagLayout());

        mainMenuLabel = new JLabel();
        mainMenuLabel.setFont(new Font("Roboto", Font.BOLD, 80));
        mainMenuLabel.setText("Kies wat u wil doen.");
        grid.gridx = 0;
        grid.gridy = 0;
        grid.gridwidth = 3;
        grid.weighty = 1.0;
        grid.anchor = GridBagConstraints.PAGE_START;
        mainMenuPanel.add(mainMenuLabel, grid);

        withdrawButton = new JButton("[A]   Opnemen");
        withdrawButton.setFont(new Font("Roboto", Font.BOLD, 40));
        grid.gridx = 0;
        grid.gridy = 1;
        grid.weightx = 0.5;
        grid.gridwidth = 1;
        grid.fill = GridBagConstraints.BOTH;
        grid.insets = new Insets(5, 50, 5, 100);
        mainMenuPanel.add(withdrawButton, grid);

        balanceButton = new JButton("[B]   Saldo");
        balanceButton.setFont(new Font("Roboto", Font.BOLD, 40));
        grid.gridx = 0;
        grid.gridy = 2;
        grid.weightx = 0.5;
        grid.gridwidth = 1;
        grid.fill = GridBagConstraints.BOTH;
        grid.insets = new Insets(5, 50, 50, 100);
        mainMenuPanel.add(balanceButton, grid);

        quickSeventyButton = new JButton("[C]   Snel 70");
        quickSeventyButton.setFont(new Font("Roboto", Font.BOLD, 40));
        grid.gridx = 2;
        grid.gridy = 1;
        grid.weightx = 0.5;
        grid.gridwidth = 1;
        grid.fill = GridBagConstraints.BOTH;
        grid.insets = new Insets(5, 100, 5, 50);
        mainMenuPanel.add(quickSeventyButton, grid);

        abortButton = new JButton("[#]   Afbreken");
        abortButton.setFont(new Font("Roboto", Font.BOLD, 40));
        grid.gridx = 2;
        grid.gridy = 2;
        grid.weightx = 0.5;
        grid.gridwidth = 1;
        grid.fill = GridBagConstraints.BOTH;
        grid.insets = new Insets(5, 100, 50, 50);
        mainMenuPanel.add(abortButton, grid);
        mainFrame.setVisible(true);

        abortButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                mainFrame.remove(mainMenuPanel);
                startingScreenPanel = null;
                startingScreen();
            }
        });

        withdrawButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                mainFrame.remove(mainMenuPanel);
                withdrawPanel = null;
                withdraw();
            }
        });

        balanceButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                mainFrame.remove(mainMenuPanel);
                balancePanel = null;
                balance();
            }
        });

        quickSeventyButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                mainFrame.remove(mainMenuPanel);
                thanksPanel = null;
                thanks();
            }
        });
    }

    public void remove(JPanel a, JPanel b) {
    mainFrame.remove(a);
    b = null;
    }

    public void withdraw(){

        withdrawPanel = new JPanel();
        mainFrame.add(withdrawPanel);
        GridBagConstraints grid = new GridBagConstraints();
        withdrawPanel.setLayout(new GridBagLayout());

        withdrawLabel = new JLabel();
        withdrawLabel.setFont(new Font("Roboto", Font.BOLD, 70));
        withdrawLabel.setText("Kies het bedrag dat u wil pinnen.");
        grid.gridx = 0;
        grid.gridy = 0;
        grid.gridwidth = 3;
        grid.weighty = 1.0;
        grid.anchor = GridBagConstraints.PAGE_START;
        withdrawPanel.add(withdrawLabel, grid);

        JLabel filler = new JLabel();
        grid.gridx = 1;
        grid.gridy = 1;
        grid.gridwidth = 1;
        grid.weightx = 0.5;
        withdrawPanel.add(filler, grid);

        JButton tenButton = new JButton("[1]   \u20BD 10");
        tenButton.setFont(new Font("Roboto", Font.BOLD, 25));
        grid.gridx = 0;
        grid.gridy = 1;
        grid.gridwidth = 1;
        grid.weightx = 0.5;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.insets = new Insets(5, 50, 5, 100);
        withdrawPanel.add(tenButton, grid);

        JButton twentyButton = new JButton("[2]   \u20BD 20");
        twentyButton.setFont(new Font("Roboto", Font.BOLD, 25));
        grid.gridx = 0;
        grid.gridy = 2;
        grid.gridwidth = 1;
        grid.weightx = 0.5;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.insets = new Insets(5, 50, 5, 100);
        withdrawPanel.add(twentyButton, grid);

        JButton fiftyButton = new JButton("[3]   \u20BD 50");
        fiftyButton.setFont(new Font("Roboto", Font.BOLD, 25));
        grid.gridx = 0;
        grid.gridy = 3;
        grid.gridwidth = 1;
        grid.weightx = 0.5;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.insets = new Insets(5, 50, 5, 100);
        withdrawPanel.add(fiftyButton, grid);

        backToMainMenuButton = new JButton("[*]   Hoofdmenu");
        backToMainMenuButton.setFont(new Font("Roboto", Font.BOLD, 25));
        grid.gridx = 0;
        grid.gridy = 4;
        grid.gridwidth = 1;
        grid.weightx = 0.5;
        grid.fill = GridBagConstraints.BOTH;
        grid.insets = new Insets(5, 50, 50, 100);
        withdrawPanel.add(backToMainMenuButton, grid);

        JButton hundredButton = new JButton("[4]   \u20BD 100");
        hundredButton.setFont(new Font("Roboto", Font.BOLD, 25));
        grid.gridx = 2;
        grid.gridy = 1;
        grid.gridwidth = 1;
        grid.weightx = 0.5;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.insets = new Insets(5, 100, 50, 50);
        withdrawPanel.add(hundredButton, grid);

        enterAmountButton = new JButton("[5]   Zelf invoeren");
        enterAmountButton.setFont(new Font("Roboto", Font.BOLD, 25));
        grid.gridx = 2;
        grid.gridy = 2;
        grid.gridwidth = 1;
        grid.weightx = 0.5;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.insets = new Insets(5, 100, 50, 50);
        withdrawPanel.add(enterAmountButton, grid);

        abortButton = new JButton("[#]   Afbreken");
        abortButton.setFont(new Font("Roboto", Font.BOLD, 25));
        grid.gridx = 2;
        grid.gridy = 4;
        grid.gridwidth = 1;
        grid.weightx = 0.5;
        grid.fill = GridBagConstraints.BOTH;            
        grid.insets = new Insets(5, 100, 50, 50);
        withdrawPanel.add(abortButton, grid);
        mainFrame.setVisible(true);

        tenButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                mainFrame.remove(withdrawPanel);
                amountPressed = 10;
                receiptPanel = null;
                receipt();
            }
        });

        twentyButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                mainFrame.remove(withdrawPanel);
                amountPressed = 20;
                billsPanel = null;
                bills();
            }
        });

        fiftyButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                mainFrame.remove(withdrawPanel);
                amountPressed = 50;
                billsPanel = null;
                bills();
            }
        });

        hundredButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                mainFrame.remove(withdrawPanel);
                amountPressed = 100;
                billsPanel = null;
                bills();
            }
        });

        enterAmountButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                mainFrame.remove(withdrawPanel);
                amountPanel = null;
                enterAmount();
            }
        });

        abortButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                mainFrame.remove(withdrawPanel);
                startingScreenPanel = null;
                startingScreen();
            }
        });

        backToMainMenuButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                mainFrame.remove(withdrawPanel);
                mainMenuPanel= null;
                mainMenu();
            }
        });
    }

    public void enterAmount(){

        amountPanel =  new JPanel();
        mainFrame.add(amountPanel);
        GridBagConstraints grid = new GridBagConstraints();
        amountPanel.setLayout(new GridBagLayout());

        amountLabel = new JLabel();
        amountLabel.setFont(new Font("Roboto", Font.BOLD, 70));
        amountLabel.setText("Voer het bedrag in dat u wilt pinnen.");
        grid.gridx = 0;
        grid.gridy = 0;
        grid.gridwidth = 3;
        grid.weighty = 1.0;
        grid.anchor = GridBagConstraints.PAGE_START;
        amountPanel.add(amountLabel, grid);
        
        amountField = new JFormattedTextField(amountFormat);
        amountField.setFont(new Font("Roboto", Font.BOLD, 25));
        amountField.setColumns(5);
        amountField.addPropertyChangeListener("value", new PropertyChangeListener(){
        
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                Object source = e.getSource();
                if (source == amountField) {
                    amount = ((Number)amountField.getValue()).intValue();
                }
            }
        });
        grid.gridx = 1;
        grid.gridy = 1;
        grid.ipadx = 40;
        grid.weightx = 1.0;
        grid.weighty = 0.0;
        grid.gridwidth = 1;
        grid.insets = new Insets(5, 50, 250, 50);
        amountPanel.add(amountField, grid);

        enterButton = new JButton("[A]   Enter");
        enterButton.setFont(new Font("Roboto", Font.BOLD, 25));
        grid.gridx = 2;
        grid.gridy = 1;
        grid.ipadx = 0;
        grid.weightx = 0.0;
        grid.gridwidth = 1;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.insets = new Insets(5, 50, 250, 100);
        amountPanel.add(enterButton, grid);            

        backToMainMenuButton = new JButton("[*]   Hoofdmenu");
        backToMainMenuButton.setFont(new Font("Roboto", Font.BOLD, 25));
        grid.gridx = 0;
        grid.gridy = 2;
        grid.weightx = 0.0;
        grid.gridwidth = 1;
        grid.fill = GridBagConstraints.BOTH;
        grid.insets = new Insets(5, 100, 50, 50);
        amountPanel.add(backToMainMenuButton, grid);

        abortButton = new JButton("[#]   Afbreken");
        abortButton.setFont(new Font("Roboto", Font.BOLD, 25));
        grid.gridx = 2;
        grid.gridy = 2;
        grid.weightx = 0.0;
        grid.gridwidth = 1;
        grid.fill = GridBagConstraints.BOTH;
        grid.insets = new Insets(5, 50, 50, 100);
        amountPanel.add(abortButton, grid);
        mainFrame.setVisible(true);

        enterButton.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){

                mainFrame.remove(amountPanel);
                billChoicePanel = null;
                billChoice();
            }
        });

        abortButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                mainFrame.remove(amountPanel);
                amountField.setValue(0);
                startingScreenPanel = null;
                startingScreen();
            }
        });

        backToMainMenuButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                mainFrame.remove(amountPanel);
                amountField.setValue(0);
                mainMenuPanel = null;
                mainMenu();
            }
        });
        
    }

    public void balance(){

        balancePanel =  new JPanel();
        mainFrame.add(balancePanel);
        GridBagConstraints grid = new GridBagConstraints();
        balancePanel.setLayout(new GridBagLayout());

        balanceLabel = new JLabel();
        balanceLabel.setFont(new Font("Roboto", Font.BOLD, 60));
        balanceLabel.setText("Uw saldo is: ");
        grid.gridx = 1;
        grid.gridy = 0;
        grid.weighty = 1.0;
        grid.anchor = GridBagConstraints.PAGE_START;
        balancePanel.add(balanceLabel, grid);

        accountBalanceLabel = new JLabel(accountBalance);
        grid.gridx = 1;
        grid.gridy = 1;
        grid.weighty = 0.0;
        grid.anchor = GridBagConstraints.CENTER;
        balancePanel.add(accountBalanceLabel, grid);

        backToMainMenuButton = new JButton("[*]   Hoofdmenu");
        backToMainMenuButton.setFont(new Font("Roboto", Font.BOLD, 25));
        grid.gridx = 0;
        grid.gridy = 2;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.weighty = 1.0;            
        balancePanel.add(backToMainMenuButton, grid);

        abortButton = new JButton("[#]   Afbreken");
        abortButton.setFont(new Font("Roboto", Font.BOLD, 25));
        grid.gridx = 2;
        grid.gridy = 2;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.weighty = 1.0;
        balancePanel.add(abortButton, grid);
        mainFrame.setVisible(true);

        backToMainMenuButton.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){

                mainFrame.remove(balancePanel);
                mainMenuPanel = null;
                mainMenu();
            }
        });

        abortButton.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){

                mainFrame.remove(balancePanel);
                startingScreenPanel = null;
                startingScreen();
            }
        });

    }

    public void bills(){

        billsPanel = new JPanel();
        mainFrame.add(billsPanel);
        GridBagConstraints grid = new GridBagConstraints();
        billsPanel.setLayout(new GridBagLayout());

        billsLabel = new JLabel();
        billsLabel.setFont(new Font("Roboto", Font.BOLD, 60));
        billsLabel.setText("Maak uw biljetkeuze.");
        grid.gridx = 1;
        grid.gridy = 0;
        grid.weighty = 1.0;
        grid.anchor = GridBagConstraints.PAGE_START;
        billsPanel.add(billsLabel, grid);

        if(amountPressed == 20){
            optionOneButton = new JButton("[A]   2 x \u20BD 10");
            optionOneButton.setFont(new Font("Roboto", Font.BOLD, 25));
            grid.gridx = 1;
            grid.gridy = 1;
            grid.fill = GridBagConstraints.HORIZONTAL;
            billsPanel.add(optionOneButton, grid);

            optionTwoButton = new JButton("[B]   1 x \u20BD 20");
            optionTwoButton.setFont(new Font("Roboto", Font.BOLD, 25));
            grid.gridx = 1;
            grid.gridy = 2;
            grid.fill = GridBagConstraints.HORIZONTAL;
            billsPanel.add(optionTwoButton, grid);
        }

        else if(amountPressed == 50){
            optionOneButton = new JButton("[A]   5 x \u20BD 10");
            optionOneButton.setFont(new Font("Roboto", Font.BOLD, 25));
            grid.gridx = 1;
            grid.gridy = 1;
            grid.fill = GridBagConstraints.HORIZONTAL;
            billsPanel.add(optionOneButton, grid);

            optionTwoButton = new JButton("[B]   2 x \u20BD 20 + 1 x \u20BD 10");
            optionTwoButton.setFont(new Font("Roboto", Font.BOLD, 25));
            grid.gridx = 1;
            grid.gridy = 2;
            grid.fill = GridBagConstraints.HORIZONTAL;
            billsPanel.add(optionTwoButton, grid);
        }

        else if(amountPressed == 100){
            optionOneButton = new JButton("[A]   3 x \u20BD 20 + 4 x \u20BD 10");
            optionOneButton.setFont(new Font("Roboto", Font.BOLD, 25));
            grid.gridx = 1;
            grid.gridy = 1;
            grid.fill = GridBagConstraints.HORIZONTAL;
            billsPanel.add(optionOneButton, grid);

            optionTwoButton = new JButton("[B]   5 x \u20BD 20");
            optionTwoButton.setFont(new Font("Roboto", Font.BOLD, 25));
            grid.gridx = 1;
            grid.gridy = 2;
            grid.fill = GridBagConstraints.HORIZONTAL;
            billsPanel.add(optionTwoButton, grid);
        }

        backToMainMenuButton = new JButton("[*]   Hoofdmenu");
        backToMainMenuButton.setFont(new Font("Roboto", Font.BOLD, 25));
        grid.gridx = 0;
        grid.gridy = 4;
        grid.fill = GridBagConstraints.HORIZONTAL;
        billsPanel.add(backToMainMenuButton, grid);

        abortButton = new JButton("[#]   Afbreken");
        abortButton.setFont(new Font("Roboto", Font.BOLD, 25));
        grid.gridx = 2;
        grid.gridy = 4;
        grid.fill = GridBagConstraints.HORIZONTAL;
        billsPanel.add(abortButton, grid);
        mainFrame.setVisible(true);

        optionOneButton.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){

                mainFrame.remove(billsPanel);
                receiptPanel = null;
                receipt();
            }
        });

        optionTwoButton.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){

                mainFrame.remove(billsPanel);
                receiptPanel = null;
                receipt();
            }
        });

        backToMainMenuButton.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){

                mainFrame.remove(billsPanel);
                amountPressed = 0;
                mainMenuPanel = null;
                mainMenu();
            }
        });

        abortButton.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){

                mainFrame.remove(billsPanel);
                amountPressed = 0;
                startingScreenPanel = null;
                startingScreen();
            }
        });
    }

    public void billChoice(){

        billChoicePanel = new JPanel();
        mainFrame.add(billChoicePanel);
        GridBagConstraints grid = new GridBagConstraints();
        billChoicePanel.setLayout(new GridBagLayout());

        billChoiceLabel = new JLabel();
        billChoiceLabel.setFont(new Font("Roboto", Font.BOLD, 40));
        billChoiceLabel.setText("Maak uw biljetkeuze.");
        grid.gridx = 1;
        grid.gridy = 0;
        grid.weighty = 1.0;
        grid.anchor = GridBagConstraints.PAGE_START;
        billChoicePanel.add(billChoiceLabel, grid);

        optionOneButton = new JButton("[A]   " +optionOne(amount));
        optionOneButton.setFont(new Font("Roboto", Font.BOLD, 25));
        grid.gridx = 1;
        grid.gridy = 1;
        grid.fill = GridBagConstraints.HORIZONTAL;
        billChoicePanel.add(optionOneButton, grid);

        optionTwoButton = new JButton("[B]   " +optionTwo(amount));
        optionTwoButton.setFont(new Font("Roboto", Font.BOLD, 25));
        grid.gridx = 1;
        grid.gridy = 2;
        grid.fill = GridBagConstraints.HORIZONTAL;
        billChoicePanel.add(optionTwoButton, grid);

        backToMainMenuButton = new JButton("[*]   Hoofdmenu");
        backToMainMenuButton.setFont(new Font("Roboto", Font.BOLD, 25));
        grid.gridx = 0;
        grid.gridy = 4;
        grid.fill = GridBagConstraints.HORIZONTAL;
        billChoicePanel.add(backToMainMenuButton, grid);

        abortButton = new JButton("[#]   Afbreken");
        abortButton.setFont(new Font("Roboto", Font.BOLD, 25));
        grid.gridx = 2;
        grid.gridy = 4;
        grid.fill = GridBagConstraints.HORIZONTAL;
        billChoicePanel.add(abortButton, grid);
        mainFrame.setVisible(true);

        optionOneButton.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){

                mainFrame.remove(billChoicePanel);
                amountField.setValue(0);
                receiptPanel = null;
                receipt();
            }
        });

        optionTwoButton.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){

                mainFrame.remove(billChoicePanel);
                amountField.setValue(0);
                receiptPanel = null;
                receipt();
            }
        });

        backToMainMenuButton.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){

                mainFrame.remove(billChoicePanel);
                amountField.setValue(0);
                mainMenuPanel = null;
                mainMenu();
            }
        });

        abortButton.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){

                mainFrame.remove(billChoicePanel);
                amountField.setValue(0);
                startingScreenPanel = null;
                startingScreen();
            }
        });

    }

    public void receipt(){

        receiptPanel = new JPanel();
        mainFrame.add(receiptPanel);
        GridBagConstraints grid = new GridBagConstraints();
        receiptPanel.setLayout(new GridBagLayout());

        receiptLabel = new JLabel();
        receiptLabel.setFont(new Font("Roboto", Font.BOLD, 60));
        receiptLabel.setText("Wilt u een bonnetje?");
        grid.gridx = 1;
        grid.gridy = 0;
        grid.weighty = 1.0;
        grid.anchor = GridBagConstraints.PAGE_START;
        receiptPanel.add(receiptLabel, grid);
        
        JButton yesButton = new JButton("[A]   Ja");
        yesButton.setFont(new Font("Roboto", Font.BOLD, 25));
        grid.gridx = 0;
        grid.gridy = 1;
        grid.fill = GridBagConstraints.HORIZONTAL;
        receiptPanel.add(yesButton, grid);

        JButton noButton = new JButton("[B]   Nee");
        noButton.setFont(new Font("Roboto", Font.BOLD, 25));
        grid.gridx = 2;
        grid.gridy = 1;
        grid.fill = GridBagConstraints.HORIZONTAL;
        receiptPanel.add(noButton, grid);
        mainFrame.setVisible(true);

        yesButton.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){

                mainFrame.remove(receiptPanel);
                amountPressed = 0;
                thanksPanel = null;
                thanks();
            }
        });

        noButton.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){

                mainFrame.remove(receiptPanel);
                amountPressed = 0;
                thanksPanel = null;
                thanks();
            }
        });

    }

    public void thanks(){

        thanksPanel = new JPanel();
        mainFrame.add(thanksPanel);
        GridBagConstraints grid = new GridBagConstraints();
        thanksPanel.setLayout(new GridBagLayout());

        thanksLabel = new JLabel();
        thanksLabel.setFont(new Font("Roboto", Font.BOLD, 80));
        thanksLabel.setText("Bedankt voor uw transactie.");
        grid.gridx = 0;
        grid.gridy = 0;
        grid.anchor = GridBagConstraints.CENTER;
        thanksPanel.add(thanksLabel, grid);
        mainFrame.setVisible(true);

        int delay = 5000;

        ActionListener taskPerformer = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            mainFrame.remove(thanksPanel);
            startingScreenPanel = null;
            startingScreen();
        }
        };
        Timer timer = new Timer(delay, taskPerformer);
        timer.setRepeats(false);
        timer.start();
        
    }

    public String optionOne(int amount){

        String s = "";
        int[] bills = {50, 20, 10};


        for(int i = 0; i < 3; i++){
                if(amount >= bills[i]){
                    noOfBills[i] = Math.floorDiv(amount, bills[i]);
                    amount = amount - bills[i] * noOfBills[i];
                }
        }

        if(noOfBills[0] != 0){
            s += Integer.toString(noOfBills[0]) + " X \u20BD 50 ";
        }
        if(noOfBills[1] != 0){
            s += Integer.toString(noOfBills[1]) + " X \u20BD 20 ";
        }
        if(noOfBills[2] != 0){
            s += Integer.toString(noOfBills[2]) + " X \u20BD 10 ";
        }

        return s;
    }

    public String optionTwo(int amount){
        
        String s = "";
        int[] bills = {50, 20, 10};
        int[] noOfBills = {0, 0, 0};

        if(amount > 150){
            noOfBills[0] = (int) Math.ceil((amount - 150) / bills[0]);
            amount = amount - bills[0] * noOfBills[0];    
        }
        if(amount > 50){
            noOfBills[1] = Math.round((amount - 50) / bills[1]);
            amount = amount - bills[1] * noOfBills[1];
        }
        if(amount >= bills[2]){
            noOfBills[2] = amount / bills[2];
            amount = amount - bills[2] * noOfBills[2];
        }

        if(noOfBills[0] != 0){
            s += Integer.toString(noOfBills[0]) + " X \u20BD 50 ";
        }
        if(noOfBills[1] != 0){
            s += Integer.toString(noOfBills[1]) + " X \u20Bd 20 ";
        }
        if(noOfBills[2] != 0){
            s += Integer.toString(noOfBills[2]) + " X \u20BD 10 ";
        }

        return s;
    }        
}