package ATM;

import com.fazecast.jSerialComm.*;

import java.util.*;

import java.io.FileWriter;
import java.io.IOException;

import static ATM.PinAutomaat.noOfBills;
import java.lang.Integer;
import ATM.PinAutomaat.*;


public final class DataProcess {
    
    static List<Character> buf = new ArrayList<>();
    static Date date = new Date();
    static String dataReceive;
    static String accountNumber;
    static String halfAccountNumber;
    static String saldo;
    static int var = 1;
    static int geldGepind;
    static PinAutomaat pinAutomaat = new PinAutomaat();
    static String passBuf = "";
    static String amountBuffer = "";
    static StringBuilder s = new StringBuilder();
    static SerialPort comPort;
    static Random delay = new Random();
    static PhpCode phpData = new PhpCode();
    static withdrawProcess moneyCheck = new withdrawProcess();
    
    public DataProcess() {

        this.read();
    }
    
    public void read() {

        comPort = SerialPort.getCommPorts()[2];
        comPort.openPort();
        comPort.addDataListener(new SerialPortDataListener() {
        
        @Override
        public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_RECEIVED; }
        
        @Override
        public void serialEvent(SerialPortEvent event)
        {
            byte[] newData = event.getReceivedData();
           
            //System.out.println("Received data of size: " + newData.length);
            for (int i = 0; i < newData.length; ++i) {
                append((char) newData[i]);
            }
        }
        });
    }
    
    static void storeBuffer() {
        
        for(int i = 0; i<buf.size(); i++) {
            s.append(buf.get(i));   
        }
        dataReceive = s.toString();
        //System.out.println(dataReceive);
        s.delete(0, buf.size());
        
    }
    
    static void append(char c) {

        if(c == '\u0000') {
            storeBuffer();
            accountNumber = dataReceive;
            halfAccountNumber = accountNumber.substring(12, 16);
            card();
            buf.clear();
            return;
        }
        else if(c == '!') {
            storeBuffer();
            pin();
            buf.clear();
            return;
        }
        else if(c == '&') {
            storeBuffer();
            mainMenu();
            buf.clear();
            return;
        }
        else if(c == '$') {
            storeBuffer();
            withdraw();
            buf.clear();
            return;
        }
        else if(c == '%') {
            storeBuffer();
            balance();
            buf.clear();
            return;
        }
        else if(c == '@') {
            storeBuffer();
            optionTen();
            buf.clear();
            return;
        }
        else if(c == '+') {
            storeBuffer();
            optionTwenty();
            buf.clear();
            return;
        }
        else if(c == '(') {
            storeBuffer();
            optionFifty();
            buf.clear();
            return;
        }
        else if(c == '_') {
            storeBuffer();
            optionHundred();
            buf.clear();
            return;
        }
        else if(c == ',') {
            storeBuffer();
            receipt();
            buf.clear();
            return;
        }
        else if(c == '?') {
            storeBuffer();
            amount();
            buf.clear();
            return;
        }
        else if(c == '`') {
            storeBuffer();
            billChoice();
            buf.clear();
            return;
        }
        else if(c == '.') {
            storeBuffer();
            buf.clear();
            return;
        }
        
        buf.add(c);
    }
    
    static void card() {
        
        if(dataReceive.equals(phpData.account(dataReceive))) {
            information();
            writeBytes("ok");
            dataReceive = "";
            
            pinAutomaat.remove(pinAutomaat.startingScreenPanel, pinAutomaat.enterPinPanel);
            pinAutomaat.enterPin();

        }
        else {
            writeBytes("wrong");
            pinAutomaat.messageBlock();
        }
    }
    
    static void pin() {
        
        if(dataReceive.equals("#")) {
           pinAutomaat.setPasswordField(removeLastCharacter(pinAutomaat.getPasswordField())); 
        }
        else if(dataReceive.equals("*")) {
            if(passBuf.equals(phpData.pincode(accountNumber, passBuf))) {
                writeBytes("pinOk");
                pinAutomaat.remove(pinAutomaat.enterPinPanel, pinAutomaat.mainMenuPanel);
                pinAutomaat.mainMenu();
                passBuf = "1";
            }
            else if(Integer.valueOf(phpData.getInlogPoging()) < 3) {
                writeBytes("pinWrong");
                passBuf = "1";
                pinAutomaat.messagePin();
            }
            else {
                writeBytes("block");
                passBuf = "1";
                pinAutomaat.messageBlock();
            }
        }
        else {
            passBuf = pinAutomaat.getPasswordField() + dataReceive;
            //System.out.println(passBuf);
            pinAutomaat.setPasswordField(passBuf);

        }  
    }
    
    static void mainMenu() {

        saldo = phpData.saldos(accountNumber);

        if(dataReceive.equals("#")) {
            writeBytes("abort");
            pinAutomaat.remove(pinAutomaat.mainMenuPanel, pinAutomaat.startingScreenPanel);
            pinAutomaat.startingScreen();
        }
        else if(dataReceive.equals("A")) {
            writeBytes("opnemen");
            pinAutomaat.remove(pinAutomaat.mainMenuPanel, pinAutomaat.withdrawPanel);
            pinAutomaat.withdraw();
        }
        else if(dataReceive.equals("B")) {
            writeBytes("saldo");
            PinAutomaat.setBalance(saldo);
            pinAutomaat.remove(pinAutomaat.mainMenuPanel, pinAutomaat.balancePanel);
            pinAutomaat.balance();
        }
        else if(dataReceive.equals("C")) {
            if(moneyCheck.optionSeventy(saldo).equals("ok")) {
                writeBytes("snel70");
                phpData.collectMoney(accountNumber, 70);
                pinAutomaat.remove(pinAutomaat.mainMenuPanel, pinAutomaat.thanksPanel);
                pinAutomaat.thanks();
            }
            else if(moneyCheck.optionSeventy(saldo).equals("biljet false")) {
                pinAutomaat.messageInsufficient("Er zijn niet genoeg biljetten aanwezig voor deze keuze.");
            }
            else if(moneyCheck.optionSeventy(saldo).equals("saldo false")) {
                pinAutomaat.messageInsufficient("U heeft niet genoeg saldo voor deze keuze.");
            }
        }
    }
    
    static void withdraw() {

       if(dataReceive.equals("#")) {
           writeBytes("abort");
           pinAutomaat.remove(pinAutomaat.withdrawPanel, pinAutomaat.startingScreenPanel);
           pinAutomaat.startingScreen();
       }
       else if(dataReceive.equals("*")) {
           writeBytes("backToMain");
           pinAutomaat.remove(pinAutomaat.withdrawPanel, pinAutomaat.mainMenuPanel);
           pinAutomaat.mainMenu();
       }
       else if(dataReceive.equals("1")) {
           writeBytes("ten");
           pinAutomaat.remove(pinAutomaat.withdrawPanel, pinAutomaat.receiptPanel);
           pinAutomaat.receipt();
       }
       else if(dataReceive.equals("2")) {
           writeBytes("twenty");
           PinAutomaat.setAmountPressed(20);
           pinAutomaat.remove(pinAutomaat.withdrawPanel, pinAutomaat.billsPanel);
           pinAutomaat.bills();
       }
       else if(dataReceive.equals("3")) {
           writeBytes("fifty");
           PinAutomaat.setAmountPressed(50);
           pinAutomaat.remove(pinAutomaat.withdrawPanel, pinAutomaat.billsPanel);
           pinAutomaat.bills();
       }
       else if(dataReceive.equals("4")) {
           writeBytes("hundred");
           PinAutomaat.setAmountPressed(100);
           pinAutomaat.remove(pinAutomaat.withdrawPanel, pinAutomaat.billsPanel);
           pinAutomaat.bills();
       }
       else if(dataReceive.equals("5")) {
           writeBytes("enterAmount");
           pinAutomaat.remove(pinAutomaat.withdrawPanel, pinAutomaat.amountPanel);
           pinAutomaat.enterAmount();
       }
    }
    
    static void balance() {

        if(dataReceive.equals("#")) {
            writeBytes("abort");
            pinAutomaat.remove(pinAutomaat.balancePanel, pinAutomaat.startingScreenPanel);
            pinAutomaat.startingScreen();
        }
        else if(dataReceive.equals("*")) {
            writeBytes("backToMain");
            pinAutomaat.remove(pinAutomaat.balancePanel, pinAutomaat.mainMenuPanel);
            pinAutomaat.mainMenu();
        }
    }

    static void optionTen() {

        if(dataReceive.equals("#")) {
            writeBytes("abort");
            pinAutomaat.startingScreen();
        }
        else if(dataReceive.equals("*")) {
            writeBytes("backToMain");
            pinAutomaat.mainMenu();
        }
        else if(dataReceive.equals("A")) {
            if(moneyCheck.optionTen(saldo).equals("ok")) {
                writeBytes("yes");
                phpData.collectMoney(accountNumber, 10);
                pinAutomaat.thanks();
                writeToFile(halfAccountNumber, "10");
            }
            else if(moneyCheck.optionTen(saldo).equals("biljet false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("Er zijn niet genoeg biljetten aanwezig voor deze keuze.");

            }
            else if(moneyCheck.optionTen(saldo).equals("saldo false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("U heeft niet genoeg saldo voor deze transactie.");
            }
        }
        else if(dataReceive.equals("B")) {
            if(moneyCheck.optionTen(saldo).equals("ok")) {
                writeBytes("no");
                phpData.collectMoney(accountNumber, 10);
                pinAutomaat.thanks();
            }
            else if(moneyCheck.optionTen(saldo).equals("biljet false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("Er zijn niet genoeg biljetten aanwezig voor deze keuze.");

            }
            else if(moneyCheck.optionTen(saldo).equals("saldo false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("U heeft niet genoeg saldo voor deze transactie.");
            }
        }
    }

    static void optionTwenty() {
        if(dataReceive.equals("#")) {
            writeBytes("abort");
            pinAutomaat.remove(pinAutomaat.billsPanel, pinAutomaat.startingScreenPanel);
            pinAutomaat.startingScreen();
        }
        else if(dataReceive.equals("*")) {
            writeBytes("backToMain");
            pinAutomaat.remove(pinAutomaat.billsPanel, pinAutomaat.mainMenuPanel);
            pinAutomaat.mainMenu();
        }
        else if(dataReceive.equals("A")) {
            if(moneyCheck.optionTwenty("option1", saldo).equals("ok")) {
                geldGepind = 20;
                writeBytes("option1");
                phpData.collectMoney(accountNumber, 20);
                pinAutomaat.remove(pinAutomaat.billsPanel, pinAutomaat.receiptPanel);
                pinAutomaat.receipt();
            }
            else if(moneyCheck.optionTwenty("option1", saldo).equals("biljet false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("Er zijn niet genoeg biljetten aanwezig voor deze keuze.");
            }
            else if(moneyCheck.optionTwenty("option1", saldo).equals("saldo false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("U heeft niet genoeg saldo voor deze transactie.");
            }

        }
        else if(dataReceive.equals("B")) {
            if(moneyCheck.optionTwenty("option2", saldo).equals("ok")) {
                geldGepind = 20;
                writeBytes("option2");
                phpData.collectMoney(accountNumber, 20);
                pinAutomaat.remove(pinAutomaat.billsPanel, pinAutomaat.receiptPanel);
                pinAutomaat.receipt();
            }
            else if(moneyCheck.optionTwenty("option2", saldo).equals("biljet false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("Er zijn niet genoeg biljetten aanwezig voor deze keuze.");
            }
            else if(moneyCheck.optionTwenty("option2", saldo).equals("saldo false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("U heeft niet genoeg saldo voor deze transactie.");
            }
        }
    }

    static void optionFifty() {

        if(dataReceive.equals("#")) {
            writeBytes("abort");
            pinAutomaat.remove(pinAutomaat.billsPanel, pinAutomaat.startingScreenPanel);
            pinAutomaat.startingScreen();
        }
        else if(dataReceive.equals("*")) {
            writeBytes("backToMain");
            pinAutomaat.remove(pinAutomaat.billsPanel, pinAutomaat.mainMenuPanel);
            pinAutomaat.mainMenu();
        }
        else if(dataReceive.equals("A")) {
            if(moneyCheck.optionFifty("option1", saldo).equals("ok")) {
                geldGepind = 50;
                writeBytes("option1");
                phpData.collectMoney(accountNumber, 50);
                pinAutomaat.remove(pinAutomaat.billsPanel, pinAutomaat.receiptPanel);
                pinAutomaat.receipt();
            }
            else if(moneyCheck.optionFifty("option1", saldo).equals("biljet false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("Er zijn niet genoeg biljetten aanwezig voor deze keuze.");
            }
            else if(moneyCheck.optionFifty("option1", saldo).equals("saldo false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("U heeft niet genoeg saldo voor deze transactie.");
            }
        }
        else if(dataReceive.equals("B")) {
            if(moneyCheck.optionFifty("option2", saldo).equals("ok")) {
                geldGepind = 50;
                writeBytes("option2");
                phpData.collectMoney(accountNumber, 50);
                pinAutomaat.remove(pinAutomaat.billsPanel, pinAutomaat.receiptPanel);
                pinAutomaat.receipt();
            }
            else if(moneyCheck.optionFifty("option2", saldo).equals("biljet false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("Er zijn niet genoeg biljetten aanwezig voor deze keuze.");
            }
            else if(moneyCheck.optionFifty("option2", saldo).equals("saldo false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("U heeft niet genoeg saldo voor deze transactie.");
            }
        }
    }

    static void optionHundred() {

        if(dataReceive.equals("#")) {
            writeBytes("abort");
            pinAutomaat.remove(pinAutomaat.billsPanel, pinAutomaat.startingScreenPanel);
            pinAutomaat.startingScreen();
        }
        else if(dataReceive.equals("*")) {
            writeBytes("backToMain");
            pinAutomaat.remove(pinAutomaat.billsPanel, pinAutomaat.mainMenuPanel);
            pinAutomaat.mainMenu();
        }
        else if(dataReceive.equals("A")) {
            if(moneyCheck.optionHundred("option1", saldo).equals("ok")) {
                geldGepind = 100;
                writeBytes("option1");
                phpData.collectMoney(accountNumber, 100);
                pinAutomaat.remove(pinAutomaat.billsPanel, pinAutomaat.receiptPanel);
                pinAutomaat.receipt();
            }
            else if(moneyCheck.optionHundred("option1", saldo).equals("biljet false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("Er zijn niet genoeg biljetten aanwezig voor deze keuze.");
            }
            else if(moneyCheck.optionHundred("option1", saldo).equals("saldo false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("U heeft niet genoeg saldo voor deze transactie.");
            }
        }
        else if(dataReceive.equals("B")) {
            if(moneyCheck.optionHundred("option2", saldo).equals("ok")) {
                geldGepind = 100;
                writeBytes("option2");
                phpData.collectMoney(accountNumber, 100);
                pinAutomaat.remove(pinAutomaat.billsPanel, pinAutomaat.receiptPanel);
                pinAutomaat.receipt();
            }
            else if(moneyCheck.optionHundred("option2", saldo).equals("biljet false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("Er zijn niet genoeg biljetten aanwezig voor deze keuze.");
            }
            else if(moneyCheck.optionHundred("option2", saldo).equals("saldo false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("U heeft niet genoeg saldo voor deze transactie.");
            }
        }
    }

    public static void amount() {

        if(dataReceive.equals("#")) {
            writeBytes("abort");
            pinAutomaat.remove(pinAutomaat.amountPanel, pinAutomaat.startingScreenPanel);
            pinAutomaat.startingScreen();
            amountBuffer = "";
        }
        else if(dataReceive.equals("*")) {
            writeBytes("backToMain");
            pinAutomaat.remove(pinAutomaat.amountPanel, pinAutomaat.mainMenuPanel);
            pinAutomaat.mainMenu();
            amountBuffer = "";
        }
        else if(dataReceive.equals("A")) {
            writeBytes("enter");
            pinAutomaat.setAmount(Integer.valueOf(amountBuffer));
            pinAutomaat.remove(pinAutomaat.amountPanel, pinAutomaat.billChoicePanel);
            pinAutomaat.billChoice();
        }
        else if(dataReceive.equals("D")) {
            pinAutomaat.setAmountField(0);
            amountBuffer = "";
        }
        else {
            amountBuffer = amountBuffer + dataReceive;
            pinAutomaat.setAmountField(Integer.valueOf(amountBuffer));
        }
    }

    public static void billChoice() {

        if(dataReceive.equals("A")) {
            if(moneyCheck.optionChoice("option1", saldo, Integer.parseInt(amountBuffer)).equals("ok")) {
                String t = String.valueOf(noOfBills[0]) + String.valueOf(noOfBills[1]) + String.valueOf(noOfBills[2]);
                geldGepind = Integer.parseInt(amountBuffer);
                writeBytes("option1");
                delay.tijd(100, 100);
                writeBytes(t);
                phpData.collectMoney(accountNumber, geldGepind);
                pinAutomaat.remove(pinAutomaat.billChoicePanel, pinAutomaat.receiptPanel);
                pinAutomaat.receipt();
                amountBuffer = "";
            }
            else if(moneyCheck.optionChoice("option1", saldo, Integer.parseInt(amountBuffer)).equals("biljet false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("Er zijn niet genoeg biljetten aanwezig voor deze keuze.");
                amountBuffer = "";
            }
            else if(moneyCheck.optionChoice("option1", saldo, Integer.parseInt(amountBuffer)).equals("saldo false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("U heeft niet genoeg saldo voor deze transactie.");
                amountBuffer = "";
            }

        }
        else if(dataReceive.equals("B")) {
            if(moneyCheck.optionChoice("option2", saldo, Integer.parseInt(amountBuffer)).equals("ok")) {
                String t = String.valueOf(noOfBills[0]) + String.valueOf(noOfBills[1]) + String.valueOf(noOfBills[2]);
                geldGepind = Integer.parseInt(amountBuffer);
                writeBytes("option2");
                delay.tijd(100, 100);
                writeBytes(t);
                phpData.collectMoney(accountNumber, geldGepind);
                pinAutomaat.remove(pinAutomaat.billChoicePanel, pinAutomaat.receiptPanel);
                pinAutomaat.receipt();
                amountBuffer = "";
            }
            else if(moneyCheck.optionChoice("option2", saldo, Integer.parseInt(amountBuffer)).equals("biljet false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("Er zijn niet genoeg biljetten aanwezig voor deze keuze.");
                amountBuffer = "";
            }
            else if(moneyCheck.optionChoice("option2", saldo, Integer.parseInt(amountBuffer)).equals("saldo false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("U heeft niet genoeg saldo voor deze transactie.");
                amountBuffer = "";
            }
        }
        else if(dataReceive.equals("#")) {
            writeBytes("abort");
            pinAutomaat.remove(pinAutomaat.billChoicePanel, pinAutomaat.startingScreenPanel);
            pinAutomaat.startingScreen();
            amountBuffer = "";
        }
        else if(dataReceive.equals("*")) {
            writeBytes("backToMain");
            pinAutomaat.remove(pinAutomaat.billChoicePanel, pinAutomaat.mainMenuPanel);
            pinAutomaat.mainMenu();
            amountBuffer = "";
        }

        for(int i = 0; i<3; i++) {
            noOfBills[i] = 0;
        }
    }

    public static void receipt() {
        if(dataReceive.equals("A")) {
            writeBytes("yes");
            pinAutomaat.remove(pinAutomaat.receiptPanel, pinAutomaat.thanksPanel);
            pinAutomaat.thanks();
            writeToFile(halfAccountNumber, String.valueOf(geldGepind));
        }
        else if(dataReceive.equals("B")) {
            writeBytes("no");
            pinAutomaat.remove(pinAutomaat.receiptPanel, pinAutomaat.thanksPanel);
            pinAutomaat.thanks();
        }
    }    
    
    public static String removeLastCharacter(String str) {
        String result = null;
        if ((str != null) && (str.length() > 0)) {
            result = str.substring(0, str.length() - 1);
        }
        return result;
    }
    
    public static void writeBytes(String line) {
        byte[] bytes = line.getBytes();
        comPort.writeBytes(bytes, bytes.length);
        delay.tijd(1000, 1000);
    }

    public static void information() {
        System.out.println("10 biljet: "+ moneyCheck.getTenCounter() +" stuks");
        System.out.println("20 biljet: "+ moneyCheck.getTwentyCounter() +" stuks");
        System.out.println("50 biljet: "+ moneyCheck.getFiftyCounter() +" stuks");
    }

    public static void writeToFile(String bankNummer, String geldGepind) {

        String s = "";

        s += "GETBANK \n";
        s += "BON: \n";
        s += "Banknummer: \n" + "xxxxxxxxxxxx" +bankNummer+ "\n";
        s += "Opgenomen bedrag: \n" +geldGepind+ "\n";
        s += "Datum: \n" +date.toString()+ "\n";
        s += "\n";

        try {
            FileWriter myWriter = new FileWriter("receipt" +var+ ".txt");
            myWriter.write(s);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        var++;
    }
   
}
