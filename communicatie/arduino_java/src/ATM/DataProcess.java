//Onderbrengen in de overkoepelende package
package ATM;

//De benodigde libraries importeren
import com.fazecast.jSerialComm.*;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import static ATM.PinAutomaat.noOfBills;
import java.lang.Integer;

public final class DataProcess {
    
    //De benodigde globale variabelen aanmaken
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
    
    //De constructor van de Dataprocess class
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
    
    //Methode die de GUI aanstuurt op basis van de RFID
    static void card() {
        
        //Controleert met de database of de gescande pas er in voorkomt
        if(dataReceive.equals(phpData.account(dataReceive))) {
            //Gaat naar het volgende scherm als de pas herkend wordt
            information();
            writeBytes("ok");
            dataReceive = "";            
            pinAutomaat.remove(pinAutomaat.startingScreenPanel);
            pinAutomaat.enterPin();
            pinAutomaat.revalidate();

        }
        else {
            //Krijgt een melding als de pas geblokkeerd is en blijft op het startscherm
            writeBytes("wrong");
            pinAutomaat.remove(pinAutomaat.startingScreenPanel);
            pinAutomaat.messageBlock();
            pinAutomaat.revalidate();
        }
    }
    
    //Methode die de GUI aanstuurt op basis van de ingevoerde pincode
    static void pin() {
        
        //Als er een hekje wordt ingevoerd kan men het invoerveld weer leegmaken
        if(dataReceive.equals("D")) {
           pinAutomaat.setPasswordField(removeLastCharacter(pinAutomaat.getPasswordField())); 
        }
        //Als er een sterretje wordt ingevoerd, wordt gecontroleerd of de pincode overeenkomt met de database
        else if(dataReceive.equals("*")) {
            if(passBuf.equals(phpData.pincode(accountNumber, passBuf))) {
                //Gaat naar het volgende scherm als de pincode klopt
                writeBytes("pinOk");
                pinAutomaat.remove(pinAutomaat.enterPinPanel);
                pinAutomaat.mainMenu();
                pinAutomaat.revalidate();
                passBuf = "1";
            }
            else if(Integer.valueOf(phpData.getInlogPoging()) < 3) {
                //Geeft een melding dat de pincode verkeerd is
                writeBytes("pinWrong");
                passBuf = "1";
                pinAutomaat.remove(pinAutomaat.enterPinPanel);
                pinAutomaat.messagePin();
                pinAutomaat.revalidate();
            }
            else {
                //Geeft een melding als de pincode te vaak verkeerd ingevoerd is, dat de pas geblokkeerd is
                writeBytes("block");
                passBuf = "1";
                pinAutomaat.remove(pinAutomaat.enterPinPanel);
                pinAutomaat.messageBlock();
                pinAutomaat.revalidate();
            }
        }
        //Pakt de input van de keypad en stopt dit in een variabel
        else if(dataReceive.equals("#")) {
            writeBytes("abort");
            pinAutomaat.remove(pinAutomaat.enterPinPanel);
            pinAutomaat.startingScreen();
            pinAutomaat.revalidate();
        }
        else {
            passBuf = pinAutomaat.getPasswordField() + dataReceive;
            //System.out.println(passBuf);
            pinAutomaat.setPasswordField(passBuf);

        }  
    }
    
    //Methode die de GUI verder aanstuurt op basis van de gekozen knop op de keypad
    static void mainMenu() {

        //Het saldo van de betreffende rekening wordt van de database gehaald en in een variabel gestopt
        saldo = phpData.saldos(accountNumber);

        //D.m.v. verschillende knoppen op de keypad gaat de GUI verder naar een ander scherm, of wordt de transactie afgebroken
        if(dataReceive.equals("#")) {
            writeBytes("abort");
            pinAutomaat.remove(pinAutomaat.mainMenuPanel);
            pinAutomaat.startingScreen();
            pinAutomaat.revalidate();
        }
        else if(dataReceive.equals("A")) {
            writeBytes("opnemen");
            pinAutomaat.remove(pinAutomaat.mainMenuPanel);
            pinAutomaat.withdraw();
            pinAutomaat.revalidate();
        }
        else if(dataReceive.equals("B")) {
            writeBytes("saldo");
            PinAutomaat.setBalance(saldo);
            pinAutomaat.remove(pinAutomaat.mainMenuPanel);
            pinAutomaat.balance();
            pinAutomaat.revalidate();
        }
        else if(dataReceive.equals("C")) {
            if(moneyCheck.optionSeventy(saldo).equals("ok")) {
                //Als de "C" wordt ingedrukt, pint de gebruiker gelijk 70 Roebel zonder naar een ander scherm te gaan. 
                //Mits het saldo toereikend is en de gebruikte biljetten aanwezig
                writeBytes("snel70");
                phpData.collectMoney(accountNumber, 70);
                pinAutomaat.remove(pinAutomaat.mainMenuPanel);
                pinAutomaat.thanks();
                pinAutomaat.revalidate();
            }
            else if(moneyCheck.optionSeventy(saldo).equals("biljet false")) {
                //Als de biljetten niet aanwezig zijn, wordt deze melding op het scherm weergegeven
                pinAutomaat.remove(pinAutomaat.mainMenuPanel);
                pinAutomaat.messageInsufficient("Er zijn niet genoeg biljetten aanwezig voor deze keuze.");
                pinAutomaat.revalidate();
            }
            else if(moneyCheck.optionSeventy(saldo).equals("saldo false")) {
                //Als het saldo neit toereikend is, wordt deze melding op het scherm weergegeven
                pinAutomaat.remove(pinAutomaat.mainMenuPanel);
                pinAutomaat.messageInsufficient("U heeft niet genoeg saldo voor deze keuze.");
                pinAutomaat.revalidate();
            }
        }
    }
    
    //Methode die de GUI aanstuurt op basis van de geselecteerde optie in het "Geld opnemen"-scherm
    static void withdraw() {

       if(dataReceive.equals("#")) {
           writeBytes("abort");
           pinAutomaat.remove(pinAutomaat.withdrawPanel);
           pinAutomaat.startingScreen();
           pinAutomaat.revalidate();
       }
       else if(dataReceive.equals("*")) {
           writeBytes("backToMain");
           pinAutomaat.remove(pinAutomaat.withdrawPanel);
           pinAutomaat.mainMenu();
           pinAutomaat.revalidate();
       }
       else if(dataReceive.equals("1")) {
           writeBytes("ten");
           pinAutomaat.remove(pinAutomaat.withdrawPanel);
           pinAutomaat.receipt();
           pinAutomaat.revalidate();
       }
       else if(dataReceive.equals("2")) {
           writeBytes("twenty");
           PinAutomaat.setAmountPressed(20);
           pinAutomaat.remove(pinAutomaat.withdrawPanel);
           pinAutomaat.bills();
           pinAutomaat.revalidate();
       }
       else if(dataReceive.equals("3")) {
           writeBytes("fifty");
           PinAutomaat.setAmountPressed(50);
           pinAutomaat.remove(pinAutomaat.withdrawPanel);
           pinAutomaat.bills();
           pinAutomaat.revalidate();
       }
       else if(dataReceive.equals("4")) {
           writeBytes("hundred");
           PinAutomaat.setAmountPressed(100);
           pinAutomaat.remove(pinAutomaat.withdrawPanel);
           pinAutomaat.bills();
           pinAutomaat.revalidate();
       }
       else if(dataReceive.equals("5")) {
           writeBytes("enterAmount");
           pinAutomaat.remove(pinAutomaat.withdrawPanel);
           pinAutomaat.enterAmount();
           pinAutomaat.revalidate();
       }
    }
    
    //Methode die de gebruiker zijn saldo laat zien in de GUI
    static void balance() {

        if(dataReceive.equals("#")) {
            writeBytes("abort");
            pinAutomaat.remove(pinAutomaat.balancePanel);
            pinAutomaat.startingScreen();
            pinAutomaat.revalidate();
        }
        else if(dataReceive.equals("*")) {
            writeBytes("backToMain");
            pinAutomaat.remove(pinAutomaat.balancePanel);
            pinAutomaat.mainMenu();
            pinAutomaat.revalidate();
        }
    }

    //Methode die controleert of de gebruiker genoeg saldo heeft en of de betreffende biljetten aanwezig zijn
    //als de gebruiker gekozen heeft om de voorgestelde 10 Roebels te pinnen
    static void optionTen() {

        if(dataReceive.equals("A")) {
            if(moneyCheck.optionTen(saldo).equals("ok")) {
                writeBytes("yes");
                phpData.collectMoney(accountNumber, 10);
                pinAutomaat.remove(pinAutomaat.receiptPanel);
                pinAutomaat.thanks();
                pinAutomaat.revalidate();
                writeToFile(halfAccountNumber, "10");
            }
            else if(moneyCheck.optionTen(saldo).equals("biljet false")) {
                writeBytes("withdraw");
                pinAutomaat.remove(pinAutomaat.receiptPanel);
                pinAutomaat.messageInsufficient("Er zijn niet genoeg biljetten aanwezig voor deze keuze.");
                pinAutomaat.revalidate();

            }
            else if(moneyCheck.optionTen(saldo).equals("saldo false")) {
                writeBytes("withdraw");
                pinAutomaat.remove(pinAutomaat.receiptPanel);
                pinAutomaat.messageInsufficient("U heeft niet genoeg saldo voor deze transactie.");
                pinAutomaat.revalidate();
            }
        }
        else if(dataReceive.equals("B")) {
            if(moneyCheck.optionTen(saldo).equals("ok")) {
                writeBytes("no");
                phpData.collectMoney(accountNumber, 10);
                pinAutomaat.remove(pinAutomaat.receiptPanel);
                pinAutomaat.thanks();
                pinAutomaat.revalidate();
            }
            else if(moneyCheck.optionTen(saldo).equals("biljet false")) {
                writeBytes("withdraw");
                pinAutomaat.remove(pinAutomaat.receiptPanel);
                pinAutomaat.messageInsufficient("Er zijn niet genoeg biljetten aanwezig voor deze keuze.");
                pinAutomaat.revalidate();

            }
            else if(moneyCheck.optionTen(saldo).equals("saldo false")) {
                writeBytes("withdraw");
                pinAutomaat.remove(pinAutomaat.receiptPanel);
                pinAutomaat.messageInsufficient("U heeft niet genoeg saldo voor deze transactie.");
                pinAutomaat.revalidate();
            }
        }
    }

    //Methode die controleert of de gebruiker genoeg saldo heeft en of de betreffende biljetten aanwezig zijn
    //als de gebruiker gekozen heeft om de voorgestelde 20 Roebels te pinnen.
    //Ook krijgt de gebruiker hier 2 opties van verschillende biljetkeuzes
    static void optionTwenty() {
        if(dataReceive.equals("#")) {
            writeBytes("abort");
            pinAutomaat.remove(pinAutomaat.billsPanel);
            pinAutomaat.startingScreen();
            pinAutomaat.revalidate();
        }
        else if(dataReceive.equals("*")) {
            writeBytes("backToMain");
            pinAutomaat.remove(pinAutomaat.billsPanel);
            pinAutomaat.mainMenu();
            pinAutomaat.revalidate();
        }
        else if(dataReceive.equals("A")) {
            if(moneyCheck.optionTwenty("option1", saldo).equals("ok")) {
                geldGepind = 20;
                writeBytes("option1");
                phpData.collectMoney(accountNumber, 20);
                pinAutomaat.remove(pinAutomaat.billsPanel);
                pinAutomaat.receipt();
                pinAutomaat.revalidate();
            }
            else if(moneyCheck.optionTwenty("option1", saldo).equals("biljet false")) {
                writeBytes("withdraw");
                pinAutomaat.remove(pinAutomaat.billsPanel);
                pinAutomaat.messageInsufficient("Er zijn niet genoeg biljetten aanwezig voor deze keuze.");
                pinAutomaat.revalidate();
            }
            else if(moneyCheck.optionTwenty("option1", saldo).equals("saldo false")) {
                writeBytes("withdraw");
                pinAutomaat.remove(pinAutomaat.billsPanel);
                pinAutomaat.messageInsufficient("U heeft niet genoeg saldo voor deze transactie.");
                pinAutomaat.revalidate();
            }

        }
        else if(dataReceive.equals("B")) {
            if(moneyCheck.optionTwenty("option2", saldo).equals("ok")) {
                geldGepind = 20;
                writeBytes("option2");
                phpData.collectMoney(accountNumber, 20);
                pinAutomaat.remove(pinAutomaat.billsPanel);
                pinAutomaat.receipt();
                pinAutomaat.revalidate();
            }
            else if(moneyCheck.optionTwenty("option2", saldo).equals("biljet false")) {
                writeBytes("withdraw");
                pinAutomaat.remove(pinAutomaat.billsPanel);
                pinAutomaat.messageInsufficient("Er zijn niet genoeg biljetten aanwezig voor deze keuze.");
                pinAutomaat.revalidate();
            }
            else if(moneyCheck.optionTwenty("option2", saldo).equals("saldo false")) {
                writeBytes("withdraw");
                pinAutomaat.remove(pinAutomaat.billsPanel);
                pinAutomaat.messageInsufficient("U heeft niet genoeg saldo voor deze transactie.");
                pinAutomaat.revalidate();
            }
        }
    }

    // Methode die controleert of de gebruiker genoeg saldo heeft en of de betreffende biljetten aanwezig zijn
    // als de gebruiker gekozen heeft om de voorgestelde 50 Roebels te pinnen.
    //Ook krijgt de gebruiker hier 2 opties van verschillende biljetkeuzes
    static void optionFifty() {

        if(dataReceive.equals("#")) {
            writeBytes("abort");
            pinAutomaat.remove(pinAutomaat.billsPanel);
            pinAutomaat.startingScreen();
            pinAutomaat.revalidate();
        }
        else if(dataReceive.equals("*")) {
            writeBytes("backToMain");
            pinAutomaat.remove(pinAutomaat.billsPanel);
            pinAutomaat.mainMenu();
            pinAutomaat.revalidate();
        }
        else if(dataReceive.equals("A")) {
            if(moneyCheck.optionFifty("option1", saldo).equals("ok")) {
                geldGepind = 50;
                writeBytes("option1");
                phpData.collectMoney(accountNumber, 50);
                pinAutomaat.remove(pinAutomaat.billsPanel);
                pinAutomaat.receipt();
                pinAutomaat.revalidate();
            }
            else if(moneyCheck.optionFifty("option1", saldo).equals("biljet false")) {
                writeBytes("withdraw");
                pinAutomaat.remove(pinAutomaat.billsPanel);
                pinAutomaat.messageInsufficient("Er zijn niet genoeg biljetten aanwezig voor deze keuze.");
                pinAutomaat.revalidate();
            }
            else if(moneyCheck.optionFifty("option1", saldo).equals("saldo false")) {
                writeBytes("withdraw");
                pinAutomaat.remove(pinAutomaat.billsPanel);
                pinAutomaat.messageInsufficient("U heeft niet genoeg saldo voor deze transactie.");
                pinAutomaat.revalidate();
            }
        }
        else if(dataReceive.equals("B")) {
            if(moneyCheck.optionFifty("option2", saldo).equals("ok")) {
                geldGepind = 50;
                writeBytes("option2");
                phpData.collectMoney(accountNumber, 50);
                pinAutomaat.remove(pinAutomaat.billsPanel);
                pinAutomaat.receipt();
                pinAutomaat.revalidate();
            }
            else if(moneyCheck.optionFifty("option2", saldo).equals("biljet false")) {
                writeBytes("withdraw");
                pinAutomaat.remove(pinAutomaat.billsPanel);
                pinAutomaat.messageInsufficient("Er zijn niet genoeg biljetten aanwezig voor deze keuze.");
                pinAutomaat.revalidate();
            }
            else if(moneyCheck.optionFifty("option2", saldo).equals("saldo false")) {
                writeBytes("withdraw");
                pinAutomaat.remove(pinAutomaat.billsPanel);
                pinAutomaat.messageInsufficient("U heeft niet genoeg saldo voor deze transactie.");
                pinAutomaat.revalidate();
            }
        }
    }

    // Methode die controleert of de gebruiker genoeg saldo heeft en of de betreffende biljetten aanwezig zijn
    // als de gebruiker gekozen heeft om de voorgestelde 100 Roebels te pinnen.
    //Ook krijgt de gebruiker hier 2 opties van verschillende biljetkeuzes
    static void optionHundred() {

        if(dataReceive.equals("#")) {
            writeBytes("abort");
            pinAutomaat.remove(pinAutomaat.billsPanel);
            pinAutomaat.startingScreen();
            pinAutomaat.revalidate();
        }
        else if(dataReceive.equals("*")) {
            writeBytes("backToMain");
            pinAutomaat.remove(pinAutomaat.billsPanel);
            pinAutomaat.mainMenu();
            pinAutomaat.revalidate();
        }
        else if(dataReceive.equals("A")) {
            if(moneyCheck.optionHundred("option1", saldo).equals("ok")) {
                geldGepind = 100;
                writeBytes("option1");
                phpData.collectMoney(accountNumber, 100);
                pinAutomaat.remove(pinAutomaat.billsPanel);
                pinAutomaat.receipt();
                pinAutomaat.revalidate();
            }
            else if(moneyCheck.optionHundred("option1", saldo).equals("biljet false")) {
                writeBytes("withdraw");
                pinAutomaat.remove(pinAutomaat.billsPanel);
                pinAutomaat.messageInsufficient("Er zijn niet genoeg biljetten aanwezig voor deze keuze.");
                pinAutomaat.revalidate();
            }
            else if(moneyCheck.optionHundred("option1", saldo).equals("saldo false")) {
                writeBytes("withdraw");
                pinAutomaat.remove(pinAutomaat.billsPanel);
                pinAutomaat.messageInsufficient("U heeft niet genoeg saldo voor deze transactie.");
                pinAutomaat.revalidate();
            }
        }
        else if(dataReceive.equals("B")) {
            if(moneyCheck.optionHundred("option2", saldo).equals("ok")) {
                geldGepind = 100;
                writeBytes("option2");
                phpData.collectMoney(accountNumber, 100);
                pinAutomaat.remove(pinAutomaat.billsPanel);
                pinAutomaat.receipt();
                pinAutomaat.revalidate();
            }
            else if(moneyCheck.optionHundred("option2", saldo).equals("biljet false")) {
                writeBytes("withdraw");
                pinAutomaat.remove(pinAutomaat.billsPanel);
                pinAutomaat.messageInsufficient("Er zijn niet genoeg biljetten aanwezig voor deze keuze.");
                pinAutomaat.revalidate();
            }
            else if(moneyCheck.optionHundred("option2", saldo).equals("saldo false")) {
                writeBytes("withdraw");
                pinAutomaat.remove(pinAutomaat.billsPanel);
                pinAutomaat.messageInsufficient("U heeft niet genoeg saldo voor deze transactie.");
                pinAutomaat.revalidate();
            }
        }
    }

    //Methode die de GUI aanstuurt vanuit het "zelf invoeren"-scherm
    public static void amount() {

        if(dataReceive.equals("#")) {
            writeBytes("abort");
            pinAutomaat.remove(pinAutomaat.amountPanel);
            pinAutomaat.startingScreen();
            pinAutomaat.revalidate();
            amountBuffer = "";
        }
        else if(dataReceive.equals("*")) {
            writeBytes("backToMain");
            pinAutomaat.remove(pinAutomaat.amountPanel);
            pinAutomaat.mainMenu();
            pinAutomaat.revalidate();
            amountBuffer = "";
        }
        else if(dataReceive.equals("A")) {
            writeBytes("enter");
            pinAutomaat.setAmount(Integer.valueOf(amountBuffer));
            pinAutomaat.remove(pinAutomaat.amountPanel);
            pinAutomaat.billChoice();
            pinAutomaat.revalidate();
        }
        else if(dataReceive.equals("D")) {
            pinAutomaat.setAmountField(0);
            amountBuffer = "";
        }
        else {
            //Hier wordt de invoer in het invoerveld in een variabel gestopt
            amountBuffer = amountBuffer + dataReceive;
            pinAutomaat.setAmountField(Integer.valueOf(amountBuffer));
        }
    }

    //Methode die controleert of de gebruiker genoeg saldo heeft en of de betreffende biljetten aanwezig zijn
    //als de gebruiker op enter heeft gedrukt na het invoeren van een eigen bedrag.
    //Ook krijgt de gebruiker hier 2 opties van verschillende biljetkeuzes
    public static void billChoice() {

        if(dataReceive.equals("A")) {
            if(moneyCheck.optionChoice("option1", saldo, Integer.parseInt(amountBuffer)).equals("ok")) {
                String t = String.valueOf(noOfBills[0]) + String.valueOf(noOfBills[1]) + String.valueOf(noOfBills[2]);
                geldGepind = Integer.parseInt(amountBuffer);
                writeBytes("option1");
                delay.tijd(100, 100);
                writeBytes(t);
                phpData.collectMoney(accountNumber, geldGepind);
                pinAutomaat.remove(pinAutomaat.billChoicePanel);
                pinAutomaat.receipt();
                pinAutomaat.revalidate();
                amountBuffer = "";
            }
            else if(moneyCheck.optionChoice("option1", saldo, Integer.parseInt(amountBuffer)).equals("biljet false")) {
                writeBytes("withdraw");
                pinAutomaat.remove(pinAutomaat.billChoicePanel);
                pinAutomaat.messageInsufficient("Er zijn niet genoeg biljetten aanwezig voor deze keuze.");
                pinAutomaat.revalidate();
                amountBuffer = "";
            }
            else if(moneyCheck.optionChoice("option1", saldo, Integer.parseInt(amountBuffer)).equals("saldo false")) {
                writeBytes("withdraw");
                pinAutomaat.remove(pinAutomaat.billChoicePanel);
                pinAutomaat.messageInsufficient("U heeft niet genoeg saldo voor deze transactie.");
                pinAutomaat.revalidate();
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
                pinAutomaat.remove(pinAutomaat.billChoicePanel);
                pinAutomaat.receipt();
                pinAutomaat.revalidate();
                amountBuffer = "";
            }
            else if(moneyCheck.optionChoice("option2", saldo, Integer.parseInt(amountBuffer)).equals("biljet false")) {
                writeBytes("withdraw");
                pinAutomaat.remove(pinAutomaat.billChoicePanel);
                pinAutomaat.messageInsufficient("Er zijn niet genoeg biljetten aanwezig voor deze keuze.");
                pinAutomaat.revalidate();
                amountBuffer = "";
            }
            else if(moneyCheck.optionChoice("option2", saldo, Integer.parseInt(amountBuffer)).equals("saldo false")) {
                writeBytes("withdraw");
                pinAutomaat.remove(pinAutomaat.billChoicePanel);
                pinAutomaat.messageInsufficient("U heeft niet genoeg saldo voor deze transactie.");
                pinAutomaat.revalidate();
                amountBuffer = "";
            }
        }
        else if(dataReceive.equals("#")) {
            writeBytes("abort");
            pinAutomaat.remove(pinAutomaat.billChoicePanel);
            pinAutomaat.startingScreen();
            pinAutomaat.revalidate();
            amountBuffer = "";
        }
        else if(dataReceive.equals("*")) {
            writeBytes("backToMain");
            pinAutomaat.remove(pinAutomaat.billChoicePanel);
            pinAutomaat.mainMenu();
            pinAutomaat.revalidate();
            amountBuffer = "";
        }

        for(int i = 0; i<3; i++) {
            noOfBills[i] = 0;
        }
    }

    //Methode die de gebruiker vraagt of deze een bonnetje wil en gaat verder op basis van het ingevoerde antwoord
    public static void receipt() {
        if(dataReceive.equals("A")) {
            writeBytes("yes");
            pinAutomaat.remove(pinAutomaat.receiptPanel);
            pinAutomaat.thanks();
            pinAutomaat.revalidate();
            writeToFile(halfAccountNumber, String.valueOf(geldGepind));
        }
        else if(dataReceive.equals("B")) {
            writeBytes("no");
            pinAutomaat.remove(pinAutomaat.receiptPanel);
            pinAutomaat.thanks();
            pinAutomaat.revalidate();
        }
    }    
    
    //Methode die zorgt dat de laatste character te verwijderen is uit een String
    public static String removeLastCharacter(String str) {
        String result = null;
        if ((str != null) && (str.length() > 0)) {
            result = str.substring(0, str.length() - 1);
        }
        return result;
    }
    
    //Methode die informatie terugstuurd naar de Arduino op basis van voorafingestelde modi in de Arduino code
    public static void writeBytes(String line) {
        byte[] bytes = line.getBytes();
        comPort.writeBytes(bytes, bytes.length);
        delay.tijd(1000, 1000);
    }

    //Methode die uitprint in de terminal hoeveel biljetten aanwezig zijn in de pinautomaat
    public static void information() {
        System.out.println("10 biljet: "+ moneyCheck.getTenCounter() +" stuks");
        System.out.println("20 biljet: "+ moneyCheck.getTwentyCounter() +" stuks");
        System.out.println("50 biljet: "+ moneyCheck.getFiftyCounter() +" stuks");
    }

    //Methode die de bon wegschrijft naar een .txt bestand
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
