
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
    static String accountNumber = "SU-GERM-00000001";
    static PinAutomaat pinAutomaat = new PinAutomaat();
    static String passBuf = "";
    static StringBuilder s = new StringBuilder();
    static String password = "1234";
    static SerialPort comPort;
    static Random delay = new Random();
    
    
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
            dataProcess();
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
        
        buf.add(c);
    }
    
    static void dataProcess() {
        
        if(dataReceive.equals(accountNumber)) {
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
            if(passBuf.equals(password)) {
                writeBytes("pinOk");
                pinAutomaat.mainMenu();
            }
            else {
                writeBytes("pinWrong");
                pinAutomaat.startingScreen();
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
           pinAutomaat.bills();
       }
       else if(dataReceive.equals("3")) {
           writeBytes("fifty");
           pinAutomaat.bills();
       }
       else if(dataReceive.equals("4")) {
           writeBytes("hundred");
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