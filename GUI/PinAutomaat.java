import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.beans.*; 

public class PinAutomaat{

    private JFrame mainFrame;
    private JPanel startingScreenPanel;
    private JPanel enterPinPanel;
    private JPanel mainMenuPanel;
    private JPanel withdrawPanel;
    private JPanel amountPanel;
    private JPanel balancePanel;
    private JPanel billsPanel;
    private JPanel billChoicePanel;
    private JPanel receiptPanel;
    private JPanel thanksPanel;
    
    private JPasswordField passwordField;
    private JFormattedTextField amountField;
    private NumberFormat amountFormat;
    private int amount;
    private int amountPressed = 0;
    private float accountBalance;

    private JButton abortButton;
    private JButton enterButton;
    private JButton withdrawButton;
    private JButton enterAmountButton;
    private JButton balanceButton;
    private JButton quickSeventyButton;
    private JButton backToMainMenuButton;
    private JButton optionOneButton;
    private JButton optionTwoButton;
    private JButton optionThreeButton;
    
    private JLabel startingScreenLabel;
    private JLabel enterPinLabel;
    private JLabel mainMenuLabel;
    private JLabel withdrawLabel;
    private JLabel amountLabel;
    private JLabel balanceLabel;
    private JLabel accountBalanceLabel;
    private JLabel billsLabel;
    private JLabel billChoiceLabel;
    private JLabel receiptLabel;
    private JLabel thanksLabel;

    public PinAutomaat(){

        mainFrame = new JFrame("Pinautomaat GUI");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(480, 320);
        mainFrame.setResizable(false);

        startingScreen();
        mainFrame.setVisible(true);
    }

        public void startingScreen(){
            
            startingScreenPanel = new JPanel();
            mainFrame.add(startingScreenPanel);
            GridBagConstraints grid = new GridBagConstraints();
            startingScreenPanel.setLayout(new GridBagLayout());

            startingScreenLabel = new JLabel();
            startingScreenLabel.setFont(new Font("Roboto", Font.BOLD, 16));
            startingScreenLabel.setText("Houd uw pinpas voor de lezer.");
            grid.gridx = 1;
            grid.gridy = 0;
            grid.weighty = 1.0;
            grid.anchor = GridBagConstraints.PAGE_START;
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
            enterPinLabel.setFont(new Font("Roboto", Font.BOLD, 16));
            enterPinLabel.setText("Voer uw pincode in.");
            grid.gridx = 1;
            grid.gridy = 0;
            grid.weighty = 1.0;
            grid.anchor = GridBagConstraints.PAGE_START;
            enterPinPanel.add(enterPinLabel, grid);

            passwordField = new JPasswordField(4);
            // passwordField.setActionCommand(command);
            grid.gridx = 0;
            grid.gridy = 2;
            grid.gridwidth = 3;
            enterPinPanel.add(passwordField, grid);

            enterButton = new JButton("Enter");
            grid.gridx = 0;
            grid.gridy = 3;
            grid.gridwidth = 1;
            enterPinPanel.add(enterButton, grid);

            abortButton = new JButton("Afbreken");
            grid.gridx = 2;
            grid.gridy = 3;
            grid.gridwidth = 1;
            enterPinPanel.add(abortButton, grid);
            mainFrame.setVisible(true);

            enterButton.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    
                        mainFrame.remove(enterPinPanel);
                        mainMenuPanel = null;
                        mainMenu();
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
            mainMenuLabel.setFont(new Font("Roboto", Font.BOLD, 16));
            mainMenuLabel.setText("Kies wat u wil doen.");
            grid.gridx = 1;
            grid.gridy = 0;
            grid.weighty = 1.0;
            grid.anchor = GridBagConstraints.PAGE_START;
            mainMenuPanel.add(mainMenuLabel, grid);

            withdrawButton = new JButton("Opnemen");
            grid.gridx = 0;
            grid.gridy = 1;
            grid.fill = GridBagConstraints.HORIZONTAL;
            grid.insets = new Insets(5, 5, 5, 0);
            mainMenuPanel.add(withdrawButton, grid);

            balanceButton = new JButton("Saldo");
            grid.gridx = 0;
            grid.gridy = 2;
            grid.fill = GridBagConstraints.HORIZONTAL;
            grid.insets = new Insets(5, 5, 5, 0);
            mainMenuPanel.add(balanceButton, grid);

            quickSeventyButton = new JButton("Snel 70");
            grid.gridx = 3;
            grid.gridy = 1;
            grid.fill = GridBagConstraints.HORIZONTAL;
            grid.insets = new Insets(5, 5, 5, 0);
            mainMenuPanel.add(quickSeventyButton, grid);

            abortButton = new JButton("Afbreken");
            grid.gridx = 3;
            grid.gridy = 2;
            grid.fill = GridBagConstraints.HORIZONTAL;
            grid.insets = new Insets(5, 5, 5, 0);
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

        public void withdraw(){

            withdrawPanel = new JPanel();
            mainFrame.add(withdrawPanel);
            GridBagConstraints grid = new GridBagConstraints();
            withdrawPanel.setLayout(new GridBagLayout());

            withdrawLabel = new JLabel();
            withdrawLabel.setFont(new Font("Helvetica", Font.BOLD, 16));
            withdrawLabel.setText("Kies het bedrag dat u wil pinnen.");
            grid.gridx = 1;
            grid.gridy = 0;
            grid.weighty = 1.0;
            grid.anchor = GridBagConstraints.PAGE_START;
            withdrawPanel.add(withdrawLabel, grid);

            JButton tenButton = new JButton(" \u20BD 10");
            grid.gridx = 0;
            grid.gridy = 1;
            grid.fill = GridBagConstraints.HORIZONTAL;
            grid.fill = GridBagConstraints.VERTICAL;
            grid.insets = new Insets(5, 5, 5, 0);
            withdrawPanel.add(tenButton, grid);

            JButton twentyButton = new JButton("\u20BD 20");
            grid.gridx = 0;
            grid.gridy = 2;
            grid.fill = GridBagConstraints.HORIZONTAL;
            grid.fill = GridBagConstraints.VERTICAL;
            grid.insets = new Insets(5, 5, 5, 0);
            withdrawPanel.add(twentyButton, grid);

            JButton fiftyButton = new JButton("\u20BD 50");
            grid.gridx = 0;
            grid.gridy = 3;
            grid.fill = GridBagConstraints.HORIZONTAL;
            grid.fill = GridBagConstraints.VERTICAL;
            grid.insets = new Insets(5, 5, 5, 0);
            withdrawPanel.add(fiftyButton, grid);

            backToMainMenuButton = new JButton("Hoofdmenu");
            grid.gridx = 0;
            grid.gridy = 4;
            grid.fill = GridBagConstraints.HORIZONTAL;
            grid.fill = GridBagConstraints.VERTICAL;
            grid.insets = new Insets(5, 5, 5, 0);
            withdrawPanel.add(backToMainMenuButton, grid);

            JButton hundredButton = new JButton("\u20BD 100");
            grid.gridx = 3;
            grid.gridy = 1;
            grid.fill = GridBagConstraints.HORIZONTAL;
            //grid.insets = new Insets(5, 0, 5, 5);
            withdrawPanel.add(hundredButton, grid);

            enterAmountButton = new JButton("Zelf invoeren");
            grid.gridx = 3;
            grid.gridy = 2;
            grid.fill = GridBagConstraints.HORIZONTAL;
            //grid.insets = new Insets(5, 0, 5, 5);
            withdrawPanel.add(enterAmountButton, grid);

            abortButton = new JButton("Afbreken");
            grid.gridx = 3;
            grid.gridy = 4;
            grid.fill = GridBagConstraints.HORIZONTAL;
            // grid.insets = new Insets(5, 0, 5, 5);
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
            amountLabel.setFont(new Font("Roboto", Font.BOLD, 16));
            amountLabel.setText("Voer het bedrag in dat u wilt pinnen.");
            grid.gridx = 1;
            grid.gridy = 0;
            grid.weighty = 1.0;
            grid.anchor = GridBagConstraints.PAGE_START;
            amountPanel.add(amountLabel, grid);
            
            amountField = new JFormattedTextField(amountFormat);
            amountField.setValue(amount);
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
            grid.anchor = GridBagConstraints.CENTER;
            grid.weightx = 1.0;
            // grid.gridwidth = 2;
            grid.weighty = 0.0;
            amountPanel.add(amountField, grid);

            enterButton = new JButton("Enter");
            grid.gridx = 2;
            grid.gridy = 1;
            grid.gridwidth = 1;
            amountPanel.add(enterButton, grid);

            abortButton = new JButton("Afbreken");
            grid.gridx = 2;
            grid.gridy = 2;
            grid.gridwidth = 1;
            grid.weighty = 1.0;
            amountPanel.add(abortButton, grid);

            backToMainMenuButton = new JButton("Hoofdmenu");
            grid.gridx = 0;
            grid.gridy = 2;
            grid.gridwidth = 1;
            amountPanel.add(backToMainMenuButton, grid);
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
            balanceLabel.setFont(new Font("Roboto", Font.BOLD, 16));
            balanceLabel.setText("Uw saldo is: "/*+saldo*/);
            grid.gridx = 1;
            grid.gridy = 0;
            grid.weighty = 1.0;
            grid.anchor = GridBagConstraints.PAGE_START;
            balancePanel.add(balanceLabel, grid);

            accountBalanceLabel = new JLabel(Float.toString(+accountBalance));
            grid.gridx = 1;
            grid.gridy = 1;
            grid.weighty = 0.0;
            grid.anchor = GridBagConstraints.CENTER;
            balancePanel.add(accountBalanceLabel, grid);

            backToMainMenuButton = new JButton("Hoofdmenu");
            grid.gridx = 0;
            grid.gridy = 2;
            grid.weighty = 1.0;            
            balancePanel.add(backToMainMenuButton, grid);

            abortButton = new JButton("Afbreken");
            grid.gridx = 2;
            grid.gridy = 2;
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
            billsLabel.setFont(new Font("Roboto", Font.BOLD, 16));
            billsLabel.setText("Maak uw biljetkeuze.");
            grid.gridx = 1;
            grid.gridy = 0;
            grid.weighty = 1.0;
            grid.anchor = GridBagConstraints.PAGE_START;
            billsPanel.add(billsLabel, grid);

            if(amountPressed == 20){
                optionOneButton = new JButton("2 x \u20BD 10");
                grid.gridx = 1;
                grid.gridy = 1;
                billsPanel.add(optionOneButton, grid);

                optionTwoButton = new JButton("1 x \u20BD 20");
                grid.gridx = 1;
                grid.gridy = 2;
                billsPanel.add(optionTwoButton, grid);
            }

            else if(amountPressed == 50){
                optionOneButton = new JButton("5 x \u20BD 10");
                grid.gridx = 1;
                grid.gridy = 1;
                billsPanel.add(optionOneButton, grid);

                optionTwoButton = new JButton("2 x \u20BD 20 + 1 x \u20BD 10");
                grid.gridx = 1;
                grid.gridy = 2;
                billsPanel.add(optionTwoButton, grid);

                // optionThreeButton = new JButton("1 x \u20BD 50");
                // grid.gridx = 1;
                // grid.gridy = 3;
                // billsPanel.add(optionThreeButton, grid);
            }

            else if(amountPressed == 100){
                optionOneButton = new JButton("3 x \u20BD 20 + 4 x \u20BD 10");
                grid.gridx = 1;
                grid.gridy = 1;
                billsPanel.add(optionOneButton, grid);

                optionTwoButton = new JButton("5 x \u20BD 20");
                grid.gridx = 1;
                grid.gridy = 2;
                billsPanel.add(optionTwoButton, grid);

                // optionThreeButton = new JButton("2 x \u20BD 50");
                // grid.gridx = 1;
                // grid.gridy = 3;
                // billsPanel.add(optionThreeButton, grid);
            }

            backToMainMenuButton = new JButton("Hoofdmenu");
            grid.gridx = 0;
            grid.gridy = 4;
            billsPanel.add(backToMainMenuButton, grid);

            abortButton = new JButton("Afbreken");
            grid.gridx = 2;
            grid.gridy = 4;
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

            // optionThreeButton.addMouseListener(new MouseAdapter(){
            //     public void mouseClicked(MouseEvent e){

            //         mainFrame.remove(billsPanel);
            //         receiptPanel = null;
            //         receipt();
            //     }
            // });

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
            billChoiceLabel.setFont(new Font("Roboto", Font.BOLD, 16));
            billChoiceLabel.setText("Maak uw biljetkeuze.");
            grid.gridx = 1;
            grid.gridy = 0;
            grid.weighty = 1.0;
            grid.anchor = GridBagConstraints.PAGE_START;
            billChoicePanel.add(billChoiceLabel, grid);

            optionOneButton = new JButton(optionOne(amount));
            grid.gridx = 1;
            grid.gridy = 1;
            billChoicePanel.add(optionOneButton, grid);

            optionTwoButton = new JButton(optionTwo(amount));
            grid.gridx = 1;
            grid.gridy = 2;
            billChoicePanel.add(optionTwoButton, grid);

            optionThreeButton = new JButton(optionThree(amount));
            grid.gridx = 1;
            grid.gridy = 3;
            billChoicePanel.add(optionThreeButton, grid);

            backToMainMenuButton = new JButton("Hoofdmenu");
            grid.gridx = 0;
            grid.gridy = 4;
            billChoicePanel.add(backToMainMenuButton, grid);

            abortButton = new JButton("Afbreken");
            grid.gridx = 2;
            grid.gridy = 4;
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

            optionThreeButton.addMouseListener(new MouseAdapter(){
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
            receiptLabel.setFont(new Font("Roboto", Font.BOLD, 16));
            receiptLabel.setText("Wilt u een bonnetje?");
            grid.gridx = 1;
            grid.gridy = 0;
            grid.weighty = 1.0;
            grid.anchor = GridBagConstraints.PAGE_START;
            receiptPanel.add(receiptLabel, grid);
            
            JButton yesButton = new JButton("Ja");
            grid.gridx = 0;
            grid.gridy = 1;
            receiptPanel.add(yesButton, grid);

            JButton noButton = new JButton("Nee");
            grid.gridx = 2;
            grid.gridy = 1;
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
            thanksLabel.setFont(new Font("Roboto", Font.BOLD, 16));
            thanksLabel.setText("Bedankt voor uw transactie.");
            grid.gridx = 1;
            grid.gridy = 0;
            thanksPanel.add(thanksLabel, grid);
            mainFrame.setVisible(true);

            /*Functie werkt niet vanwege 'undefined for type' error
            Verder onderzoek nodig*/
            // ActionListener taskperformer = new ActionListener(){
            //     public void actionPerformed(ActionEvent e){

            //         mainFrame.remove(thanksPanel);
            //         startingScreenPanel = null;
            //         startingScreen();
            //     }
            // };

            // Timer timer = new Timer(5000, taskperformer);
            // timer.setRepeats(false);
            // timer.start();

            // Timer timer = new Timer(1000, new ActionListener() {
            //     @Override
            //     public void actionPerformed(ActionEvent e) {
            //         x += 110;
            //         if (x >= 1000) {
            //             x = 1000;
            //             ((Timer)e.getSource()).stop();
            //         }
            //         startingScreen();
            //     }
            // });
            // timer.start();

            int delay = 5000; //milliseconds
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
            int[] noOfBills = {0, 0, 0};

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

        public String optionThree(int amount){

            String s = "";
            int[] bills = {50, 20, 10};
            int[] noOfBills = {0, 0, 0};

            if(amount == 50){
                noOfBills[1] = 2;
                noOfBills[2] = 1;
            }
            else{
                while(amount > 0){
                    for(int i = 0; i < 3; i++){
                        if(amount >= noOfBills[i]){
                            noOfBills[i]++;
                            amount = amount - bills[i];
                        }
                    }
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
        
}
        