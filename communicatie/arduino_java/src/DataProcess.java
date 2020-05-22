
import com.fazecast.jSerialComm.*;

import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Eki
 */
public final class DataProcess {
    
    static List<Character> buf = new ArrayList<>();
    static String dataReceive;
    static String accountNumber;
    static PinAutomaat pinAutomaat = new PinAutomaat();
    static String passBuf = "";
    static StringBuilder s = new StringBuilder();
    static SerialPort comPort;
    static Random delay = new Random();
    static PhpCode phpData = new PhpCode();
    
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
           
            System.out.println("Received data of size: " + newData.length);
            for (int i = 0; i < newData.length; ++i) {
                append((char) newData[i]);
                
            }
            System.out.println("\n");
            
        }
        });
        
    }
    
    
    static void storeBuffer() {
        
        for(int i = 0; i<buf.size(); i++) {
            s.append(buf.get(i));   
        }
        dataReceive = s.toString();
        System.out.println(dataReceive);
        s.delete(0, buf.size());
        
    }
    
    static void append(char c) {
        if(c == '\u0000') {
            storeBuffer();
            accountNumber = dataReceive;
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
            System.out.println("option20");
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
        
        buf.add(c);
    }
    
    static void card() {
        
        if(dataReceive.equals(phpData.account(dataReceive))) {
            writeBytes("ok");
            dataReceive = "";
            pinAutomaat.enterPin();
        }
        else {
            System.out.println("wrong");
            writeBytes("wrong");
            
        }
       
    
    }
    
    static void pin() {
        
        if(dataReceive.equals("*")) {
            System.out.println("delete");
           pinAutomaat.setPasswordField(removeLastCharacter(pinAutomaat.getPasswordField())); 
        }
        else if(dataReceive.equals("#")) {
            if(passBuf.equals(phpData.pincode(accountNumber, passBuf))) {
                writeBytes("pinOk");
                pinAutomaat.mainMenu();
                passBuf = "1";
            }
            else {
                writeBytes("pinWrong");
                pinAutomaat.startingScreen();
                passBuf = "1";
            }
        }
        
        else {
            passBuf = pinAutomaat.getPasswordField() + dataReceive;
            System.out.println(passBuf);
            pinAutomaat.setPasswordField(passBuf);
            
            
        }
  
    }
    
    static void mainMenu() {
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
            PinAutomaat.setBalance(phpData.saldos(accountNumber));
            pinAutomaat.balance();
        }
        else if(dataReceive.equals("C")) {
            writeBytes("snel70");
            pinAutomaat.thanks();
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
            writeBytes("yes");
            pinAutomaat.thanks();
        }
        else if(dataReceive.equals("B")) {
            writeBytes("no");
            pinAutomaat.thanks();
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
            writeBytes("option1");
            pinAutomaat.receipt();
        }
        else if(dataReceive.equals("B")) {
            writeBytes("option2");
            pinAutomaat.receipt();
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
            writeBytes("option1");
            pinAutomaat.receipt();
        }
        else if(dataReceive.equals("B")) {
            writeBytes("option2");
            pinAutomaat.receipt();
        }
        else if(dataReceive.equals("C")) {
            writeBytes("option3");
            pinAutomaat.receipt();
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
            writeBytes("option1");
            pinAutomaat.receipt();
        }
        else if(dataReceive.equals("B")) {
            writeBytes("option2");
            pinAutomaat.receipt();
        }
        else if(dataReceive.equals("C")) {
            writeBytes("option3");
            pinAutomaat.receipt();
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
    
   
}
