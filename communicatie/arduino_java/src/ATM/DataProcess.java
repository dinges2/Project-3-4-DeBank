package ATM;

import com.fazecast.jSerialComm.*;

import java.util.*;

import java.io.FileWriter;
import java.io.IOException;

import static ATM.PinAutomaat.noOfBills;


public final class DataProcess {
    
    static List<Character> buf = new ArrayList<>();
    static Date date = new Date();
    static String dataReceive;
    static String accountNumber;
    static String halfAccountNumber;
    static String saldo;
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
            //System.out.println("\n");
            
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
        
        buf.add(c);
    }
    
    static void card() {
        
        if(dataReceive.equals(phpData.account(dataReceive))) {
            information();
            writeBytes("ok");
            dataReceive = "";
            
            pinAutomaat.enterPin();

        }
        else {
            System.out.println("wrong");
            writeBytes("wrong");
            pinAutomaat.messageBlock();
        }
       
    
    }
    
    static void pin() {
        
        if(dataReceive.equals("*")) {
           pinAutomaat.setPasswordField(removeLastCharacter(pinAutomaat.getPasswordField())); 
        }
        else if(dataReceive.equals("#")) {
            if(passBuf.equals(phpData.pincode(accountNumber, passBuf))) {
                writeBytes("pinOk");
                pinAutomaat.mainMenu();
                passBuf = "1";
            }
            else if(Integer.valueOf(phpData.getInlogPoging()) < 3) {
                writeBytes("pinWrong");
                System.out.println("wrong");
                System.out.println("Inlogpoging: "+Integer.valueOf(phpData.getInlogPoging()));
                passBuf = "1";
                pinAutomaat.messagePin();
            }
            else {
                writeBytes("block");
                System.out.println("block");
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
            pinAutomaat.startingScreen();
        }
        else if(dataReceive.equals("A")) {
            writeBytes("opnemen");
            pinAutomaat.withdraw();
        }
        else if(dataReceive.equals("B")) {
            writeBytes("saldo");
            PinAutomaat.setBalance(saldo);
            pinAutomaat.balance();
        }
        else if(dataReceive.equals("C")) {
            if(moneyCheck.optionSeventy(saldo).equals("ok")) {
                writeBytes("snel70");
                phpData.collectMoney(accountNumber, 70);
                pinAutomaat.thanks();
            }
            else if(moneyCheck.optionSeventy(saldo).equals("biljet false")) {
                System.out.println("geen biljetten meer");
            }
            else if(moneyCheck.optionSeventy(saldo).equals("saldo false")) {
                System.out.println("niet genoeg saldo");
            }

        }
        
    }
    
    static void withdraw() {
       if(dataReceive.equals("#")) {
           writeBytes("abort");
           pinAutomaat.startingScreen();
       }
       else if(dataReceive.equals("*")) {
           writeBytes("backToMain");
           pinAutomaat.mainMenu();
       }
       else if(dataReceive.equals("1")) {
           writeBytes("ten");

           pinAutomaat.receipt();
       }
       else if(dataReceive.equals("2")) {
           writeBytes("twenty");
           PinAutomaat.setAmountPressed(20);
           pinAutomaat.bills();
       }
       else if(dataReceive.equals("3")) {
           writeBytes("fifty");
           PinAutomaat.setAmountPressed(50);
           pinAutomaat.bills();
       }
       else if(dataReceive.equals("4")) {
           writeBytes("hundred");
           PinAutomaat.setAmountPressed(100);
           pinAutomaat.bills();
       }
       else if(dataReceive.equals("5")) {
           writeBytes("enterAmount");
           pinAutomaat.enterAmount();
       }
    }
    
    static void balance() {
        if(dataReceive.equals("#")) {
            writeBytes("abort");
            pinAutomaat.startingScreen();
        }
        else if(dataReceive.equals("*")) {
            writeBytes("backToMain");
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
                System.out.println("geen 10 biljetten meer");
                pinAutomaat.messageInsufficient("geen 10 biljet");

            }
            else if(moneyCheck.optionTen(saldo).equals("saldo false")) {
                writeBytes("withdraw");
                System.out.println("niet genoeg saldo");
                pinAutomaat.messageInsufficient("geen saldo");
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
                System.out.println("geen 10 biljetten meer");
                pinAutomaat.messageInsufficient("geen 10 biljet");

            }
            else if(moneyCheck.optionTen(saldo).equals("saldo false")) {
                writeBytes("withdraw");
                System.out.println("niet genoeg saldo");
                pinAutomaat.messageInsufficient("geen saldo");
            }
        }
    }

    static void optionTwenty() {
        if(dataReceive.equals("#")) {
            writeBytes("abort");
            pinAutomaat.startingScreen();
        }
        else if(dataReceive.equals("*")) {
            writeBytes("backToMain");
            pinAutomaat.mainMenu();
        }
        else if(dataReceive.equals("A")) {
            if(moneyCheck.optionTwenty("option1", saldo).equals("ok")) {
                geldGepind = 20;
                writeBytes("option1");
                phpData.collectMoney(accountNumber, 20);
                pinAutomaat.receipt();
            }
            else if(moneyCheck.optionTwenty("option1", saldo).equals("biljet false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("geen biljet");
                System.out.println("geen 10 biljetten meer");
            }
            else if(moneyCheck.optionTwenty("option1", saldo).equals("saldo false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("geen saldo");
                System.out.println("niet genoeg saldo");
            }

        }
        else if(dataReceive.equals("B")) {
            if(moneyCheck.optionTwenty("option2", saldo).equals("ok")) {
                geldGepind = 20;
                writeBytes("option2");
                phpData.collectMoney(accountNumber, 20);
                pinAutomaat.receipt();
            }
            else if(moneyCheck.optionTwenty("option2", saldo).equals("biljet false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("geen biljet");
                System.out.println("geen 20 biljetten meer");
            }
            else if(moneyCheck.optionTwenty("option2", saldo).equals("saldo false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("geen saldo");
                System.out.println("niet genoeg saldo");
            }

        }
    }

    static void optionFifty() {
        if(dataReceive.equals("#")) {
            writeBytes("abort");
            pinAutomaat.startingScreen();
        }
        else if(dataReceive.equals("*")) {
            writeBytes("backToMain");
            pinAutomaat.mainMenu();
        }
        else if(dataReceive.equals("A")) {
            if(moneyCheck.optionFifty("option1", saldo).equals("ok")) {
                geldGepind = 50;
                writeBytes("option1");
                phpData.collectMoney(accountNumber, 50);
                pinAutomaat.receipt();
            }
            else if(moneyCheck.optionFifty("option1", saldo).equals("biljet false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("geen biljet");
                System.out.println("geen 10 biljetten meer");
            }
            else if(moneyCheck.optionFifty("option1", saldo).equals("saldo false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("geen saldo");
                System.out.println("niet genoeg saldo");
            }

        }
        else if(dataReceive.equals("B")) {
            if(moneyCheck.optionFifty("option2", saldo).equals("ok")) {
                geldGepind = 50;
                writeBytes("option2");
                phpData.collectMoney(accountNumber, 50);
                pinAutomaat.receipt();
            }
            else if(moneyCheck.optionFifty("option2", saldo).equals("biljet false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("geen biljet");
                System.out.println("geen biljetten meer");
            }
            else if(moneyCheck.optionFifty("option2", saldo).equals("saldo false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("geen saldo");
                System.out.println("niet genoeg saldo");
            }

        }
        else if(dataReceive.equals("C")) {
            if(moneyCheck.optionFifty("option3", saldo).equals("ok")) {
                geldGepind = 50;
                writeBytes("option3");
                phpData.collectMoney(accountNumber, 50);
                pinAutomaat.receipt();
            }
            else if(moneyCheck.optionFifty("option3", saldo).equals("biljet false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("geen biljet");
                System.out.println("geen biljetten meer");
            }
            else if(moneyCheck.optionFifty("option3", saldo).equals("saldo false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("geen saldo");
                System.out.println("niet genoeg saldo");
            }

        }
    }

    static void optionHundred() {
        if(dataReceive.equals("#")) {
            writeBytes("abort");
            pinAutomaat.startingScreen();
        }
        else if(dataReceive.equals("*")) {
            writeBytes("backToMain");
            pinAutomaat.mainMenu();
        }
        else if(dataReceive.equals("A")) {
            if(moneyCheck.optionHundred("option1", saldo).equals("ok")) {
                geldGepind = 100;
                writeBytes("option1");
                phpData.collectMoney(accountNumber, 100);
                pinAutomaat.receipt();
            }
            else if(moneyCheck.optionHundred("option1", saldo).equals("biljet false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("geen biljet");
                System.out.println("geen biljetten meer");
            }
            else if(moneyCheck.optionHundred("option1", saldo).equals("saldo false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("geen saldo");
                System.out.println("niet genoeg saldo");
            }

        }
        else if(dataReceive.equals("B")) {
            if(moneyCheck.optionHundred("option2", saldo).equals("ok")) {
                geldGepind = 100;
                writeBytes("option2");
                phpData.collectMoney(accountNumber, 100);
                pinAutomaat.receipt();
            }
            else if(moneyCheck.optionHundred("option2", saldo).equals("biljet false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("geen biljet");
                System.out.println("geen biljetten meer");
            }
            else if(moneyCheck.optionHundred("option2", saldo).equals("saldo false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("geen saldo");
                System.out.println("niet genoeg saldo");
            }

        }
        else if(dataReceive.equals("C")) {
            if(moneyCheck.optionHundred("option3", saldo).equals("ok")) {
                geldGepind = 100;
                writeBytes("option3");
                phpData.collectMoney(accountNumber, 100);
                pinAutomaat.receipt();
            }
            else if(moneyCheck.optionHundred("option3", saldo).equals("biljet false")) {
                System.out.println("geen 50 biljetten meer");
            }
            else if(moneyCheck.optionHundred("option3", saldo).equals("saldo false")) {
                System.out.println("niet genoeg saldo");
            }

        }
    }

    public static void amount() {


        if(dataReceive.equals("#")) {
            writeBytes("abort");
            pinAutomaat.startingScreen();
            amountBuffer = "";
        }
        else if(dataReceive.equals("*")) {
            writeBytes("backToMain");
            pinAutomaat.mainMenu();
            amountBuffer = "";
        }
        else if(dataReceive.equals("A")) {
            writeBytes("enter");
            pinAutomaat.setAmount(Integer.valueOf(amountBuffer));
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
                geldGepind = Integer.parseInt(amountBuffer);
                writeBytes("option1");
                phpData.collectMoney(accountNumber, geldGepind);
                pinAutomaat.receipt();
                amountBuffer = "";
            }
            else if(moneyCheck.optionChoice("option1", saldo, Integer.parseInt(amountBuffer)).equals("biljet false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("geen biljet");
                System.out.println("geen biljetten meer");
                amountBuffer = "";
            }
            else if(moneyCheck.optionChoice("option1", saldo, Integer.parseInt(amountBuffer)).equals("saldo false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("geen saldo");
                System.out.println("niet genoeg saldo");
                amountBuffer = "";
            }

        }
        else if(dataReceive.equals("B")) {
            if(moneyCheck.optionChoice("option2", saldo, Integer.parseInt(amountBuffer)).equals("ok")) {
                geldGepind = Integer.parseInt(amountBuffer);
                writeBytes("option2");
                phpData.collectMoney(accountNumber, geldGepind);
                pinAutomaat.receipt();
                amountBuffer = "";
            }
            else if(moneyCheck.optionChoice("option2", saldo, Integer.parseInt(amountBuffer)).equals("biljet false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("geen biljet");
                System.out.println("geen biljetten meer");
                amountBuffer = "";
            }
            else if(moneyCheck.optionChoice("option2", saldo, Integer.parseInt(amountBuffer)).equals("saldo false")) {
                writeBytes("withdraw");
                pinAutomaat.messageInsufficient("geen saldo");
                System.out.println("niet genoeg saldo");
                amountBuffer = "";
            }
        }
        else if(dataReceive.equals("#")) {
            writeBytes("abort");
            pinAutomaat.startingScreen();
            amountBuffer = "";
        }
        else if(dataReceive.equals("*")) {
            writeBytes("backToMain");
            pinAutomaat.mainMenu();
            amountBuffer = "";
        }

        for(int i = 0; i<3; i++) {
            noOfBills[i] = 0;
        }
    }

    public static void receipt() {
        if(dataReceive.equals("#")) {
            writeBytes("abort");
            pinAutomaat.startingScreen();
        }
        else if(dataReceive.equals("*")) {
            writeBytes("backToMain");
            pinAutomaat.mainMenu();
        }
        else if(dataReceive.equals("A")) {
            writeBytes("yes");
            pinAutomaat.thanks();
            writeToFile(halfAccountNumber, String.valueOf(geldGepind));
        }
        else if(dataReceive.equals("B")) {
            writeBytes("no");
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

    // public static String bon(String bankNummer, String geldGepind, int saldo) {

    //     String s;

    //     s += "\n";
    //     s += "BON:";
    //     s += "Banknummer: "+ bankNummer;
    //     s += "Opgenomen bedrag: "+ geldGepind;
    //     s += "Saldo nu: "+ saldo;
    //     s += "\n";

    //     return s;
    // }

    public static void writeToFile(String bankNummer, String geldGepind) {

        int var = 1;
        String s;

        s += "\n";
        s += "BON:\n";
        s += "Banknummer: " +bankNummer+ "\n";
        s += "Opgenomen bedrag: " +geldGepind+ "\n";
        s += date.toString()+ "\n";
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
