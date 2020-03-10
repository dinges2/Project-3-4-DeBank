import javax.swing.*;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

import com.sun.org.apache.xpath.internal.operations.Bool;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

/**
 * Created by Raber on 7-3-2017.
 */
public class Pinautomaat extends JFrame implements SerialPortEventListener {
    private JFrame mainFrame;
    private JPanel beginschermPanel;
    private JPanel invoerPinPanel;
    private JPanel menuPanel;
    private JPanel saldoInfoPanel;
    private JPanel geldOpnemenPanel;
    private JPanel kiesBedragPanel;
    private JPanel invoerBedragPanel;
    private JPanel soortBiljettenPanel;
    private JPanel transactieBonPanel;
    private JPanel verwerkingsTekstPanel;

    //invoerpincode
    private JPasswordField passwordField;
    //menu knoppen
    private JButton opnemenGeld;
    private JButton infoSaldo;
    private JButton breekaf;
    private JButton OkButton;
    private JButton snelOpnemen;
    private JButton bedragkiezen;
    private JButton bedragInvoeren;
    private JButton knop50;
    private JButton knop20;
    private JButton eigenBedrag;
    private JButton knop10;
    private JButton nee;

    private JLabel beginscherm;
    private JLabel invoerPin;
    private JLabel menu;
    private JLabel saldoInfo;
    private JLabel geldOpnemen;
    private JLabel kiesBedrag;
    private JLabel invoerBedrag;
    private JLabel soortBiljetten;
    private JLabel transactieBon;
    private JLabel verwerkingsTekst;

    private JTextArea eigenbedrag;

    private int pincode ;
    private String pas1 = "5496E35F";
    private String pas2 = "D485CC5F";
    private Boolean p1 ;
    private Boolean p2;
    private int saldo1 = 1000 ;
    private int saldo2 = 3000;
    private int saldo ;
    ///////////////////////////////////////////////////
    SerialPort serialPort;


    //Automaat automaat=null;

    /**
     * The port we're normally going to use.
     */

    private static final String PORT_NAMES[] = {

            "COM7", // Windows

    };

    private BufferedReader input;

    /**
     * The output stream to the port
     */

    private OutputStream output;

    /**
     * Milliseconds to block while waiting for port open
     */

    private static final int TIME_OUT = 2000;

    /**
     * Default bits per second for COM port.
     */

    private static final int DATA_RATE = 9600;

    public void initialize() {

        CommPortIdentifier portId = null;

        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        //First, Find an instance of serial port as set in PORT_NAMES.

        while (portEnum.hasMoreElements()) {

            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();

            for (String portName : PORT_NAMES) {

                if (currPortId.getName().equals(portName)) {

                    portId = currPortId;

                    break;

                }

            }

        }

        if (portId == null) {

            System.out.println("Could not find COM port.");

            return;

        }

        try {

            // open serial port, and use class name for the appName.

            serialPort = (SerialPort) portId.open(this.getClass().getName(),

                    TIME_OUT);

            // set port parameters

            serialPort.setSerialPortParams(DATA_RATE,

                    SerialPort.DATABITS_8,

                    SerialPort.STOPBITS_1,

                    SerialPort.PARITY_NONE);

            // open the streams

            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));

            output = serialPort.getOutputStream();

            // add event listeners

            serialPort.addEventListener(this);

            serialPort.notifyOnDataAvailable(true);

        } catch (Exception e) {

            System.err.println(e.toString());

        }

    }

    //////////////

    public Pinautomaat() {
        //main frame
        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1920, 1080);
        mainFrame.setResizable(false);

        //begin van gui
        beginscherm();
        mainFrame.setVisible(true);

    }

    public void beginscherm() {
        initialize();
        beginschermPanel = new JPanel();
        mainFrame.add(beginschermPanel);
        beginschermPanel.setLayout(null);

        beginscherm = new JLabel();
        beginscherm.setHorizontalAlignment(SwingConstants.CENTER);
        beginscherm.setFont(new Font("Tahoma", Font.BOLD, 51));
        beginscherm.setText("Houd uw pinpas voor de scanner");
        beginscherm.setBounds(12, 189, 1890, 232);
        beginschermPanel.add(beginscherm);

        OkButton = new JButton("OK");
        OkButton.setBounds(787, 463, 326, 74);
        beginschermPanel.add(OkButton);
        mainFrame.setVisible(true);

        OkButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                mainFrame.remove(beginschermPanel);
                invoerPinPanel = null;
                invoerPin();
            }
        });


    }

    public void invoerPin() {
        initialize();
        invoerPinPanel = new JPanel();
        mainFrame.add(invoerPinPanel);
        invoerPinPanel.setLayout(null);

        invoerPin = new JLabel();
        invoerPin.setHorizontalAlignment(SwingConstants.CENTER);
        invoerPin.setFont(new Font("Tahoma", Font.BOLD, 51));
        invoerPin.setText("Voer uw pincode in");
        invoerPin.setBounds(12, 189, 1890, 232);
        invoerPinPanel.add(invoerPin);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 99));
        passwordField.setBounds(792, 463, 194, 109);
        invoerPinPanel.add(passwordField);

        OkButton = new JButton("OK");
        OkButton.setBounds(1300, 463, 326, 74);
        invoerPinPanel.add(OkButton);

        breekaf = new JButton("Afbreken");
        breekaf.setBounds(1300, 557, 326, 74);
        invoerPinPanel.add(breekaf);
        mainFrame.setVisible(true);


        //listeners
        OkButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                //if (passwordField.getText().equals(Integer.toString(pincode))) {
                    mainFrame.remove(invoerPinPanel);
                    menuPanel = null;
                    menu();
               // }
            }
        });

        breekaf.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                mainFrame.remove(invoerPinPanel);
                beginschermPanel = null;
                beginscherm();


            }
        });


    }

    public void menu() {
        initialize();
        menuPanel = new JPanel();
        mainFrame.add(menuPanel);
        menuPanel.setLayout(null);

        menu = new JLabel();
        menu.setHorizontalAlignment(SwingConstants.CENTER);
        menu.setFont(new Font("Tahoma", Font.BOLD, 51));
        menu.setText("U wilt?");
        menu.setBounds(12, 189, 1890, 232);
        menuPanel.add(menu);

        opnemenGeld = new JButton("Geld opnemen");
        opnemenGeld.setBounds(1300, 463, 326, 74);
        menuPanel.add(opnemenGeld);

        infoSaldo = new JButton("Saldo opvragen");
        infoSaldo.setBounds(1300, 557, 326, 74);
        menuPanel.add(infoSaldo);

        breekaf = new JButton("Afbreken");
        breekaf.setBounds(1300, 651, 326, 74);
        menuPanel.add(breekaf);

        mainFrame.setVisible(true);

        infoSaldo.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent s) {

                mainFrame.remove(menuPanel);
                saldoInfoPanel = null;
                saldoInfo();

            }
        });

        opnemenGeld.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent s) {

                mainFrame.remove(menuPanel);
                geldOpnemenPanel = null;
                geldOpnemen();

            }
        });

        breekaf.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent s) {

                mainFrame.remove(menuPanel);
                beginschermPanel = null;
                beginscherm();

            }
        });

    }

    public void saldoInfo() {
        initialize();
        saldoInfoPanel = new JPanel();
        mainFrame.add(saldoInfoPanel);
        saldoInfoPanel.setLayout(null);

        saldoInfo = new JLabel();
        saldoInfo.setHorizontalAlignment(SwingConstants.CENTER);
        saldoInfo.setFont(new Font("Tahoma", Font.BOLD, 51));
        saldoInfo.setText("Uw saldo is ...");
        saldoInfo.setBounds(12, 189, 1890, 232);
        saldoInfoPanel.add(saldoInfo);

        opnemenGeld = new JButton("Geld opnemen");
        opnemenGeld.setBounds(1300, 463, 326, 74);
        saldoInfoPanel.add(opnemenGeld);

        breekaf = new JButton("Afbreken");
        breekaf.setBounds(1300, 557, 326, 74);
        saldoInfoPanel.add(breekaf);

        mainFrame.setVisible(true);
        saldoInfo.updateUI();

        opnemenGeld.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent s) {

                mainFrame.remove(saldoInfoPanel);
                geldOpnemenPanel = null;
                geldOpnemen();

            }
        });

        breekaf.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent s) {

                mainFrame.remove(saldoInfoPanel);
                beginschermPanel = null;
                beginscherm();


            }
        });

    }

    public void geldOpnemen() {
        initialize();
        geldOpnemenPanel = new JPanel();
        mainFrame.add(geldOpnemenPanel);
        geldOpnemenPanel.setLayout(null);

        geldOpnemen = new JLabel();
        geldOpnemen.setHorizontalAlignment(SwingConstants.CENTER);
        geldOpnemen.setFont(new Font("Tahoma", Font.BOLD, 51));
        geldOpnemen.setText("Wilt u?");
        geldOpnemen.setBounds(12, 189, 1890, 232);
        geldOpnemenPanel.add(geldOpnemen);

        bedragkiezen = new JButton("Bedrag kiezen");
        bedragkiezen.setBounds(1300, 463, 326, 74);
        geldOpnemenPanel.add(bedragkiezen);

        bedragInvoeren = new JButton("Bedrag invoeren");
        bedragInvoeren.setBounds(1300, 557, 326, 74);
        geldOpnemenPanel.add(bedragInvoeren);

        infoSaldo = new JButton("Saldo opvragen");
        infoSaldo.setBounds(1300, 651, 326, 74);
        geldOpnemenPanel.add(infoSaldo);

        breekaf = new JButton("Afbreken");
        breekaf.setBounds(1300, 745, 326, 74);
        geldOpnemenPanel.add(breekaf);

        mainFrame.setVisible(true);

        bedragkiezen.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent s) {

                mainFrame.remove(geldOpnemenPanel);
                kiesBedragPanel = null;
                bedragKiezen();

            }
        });

        bedragInvoeren.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent s) {

                mainFrame.remove(geldOpnemenPanel);
                invoerBedragPanel = null;
                bedragInvoer();

            }
        });

        breekaf.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent s) {

                mainFrame.remove(geldOpnemenPanel);
                beginschermPanel = null;
                beginscherm();

            }
        });

        infoSaldo.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent s) {

                mainFrame.remove(geldOpnemenPanel);
                saldoInfoPanel = null;
                saldoInfo();

            }
        });

    }

    public void bedragKiezen() {
        initialize();
        kiesBedragPanel = new JPanel();
        mainFrame.add(kiesBedragPanel);
        kiesBedragPanel.setLayout(null);

        kiesBedrag = new JLabel();
        kiesBedrag.setHorizontalAlignment(SwingConstants.CENTER);
        kiesBedrag.setFont(new Font("Tahoma", Font.BOLD, 51));
        kiesBedrag.setText("Kies uw bedrag");
        kiesBedrag.setBounds(12, 189, 1890, 232);
        kiesBedragPanel.add(kiesBedrag);

        knop20 = new JButton("€ 20");
        knop20.setBounds(1300, 463, 326, 74);
        kiesBedragPanel.add(knop20);

        knop50 = new JButton("€ 50");
        knop50.setBounds(1300, 557, 326, 74);
        kiesBedragPanel.add(knop50);

        eigenBedrag = new JButton("Bedrag invoeren");
        eigenBedrag.setBounds(1300, 651, 326, 74);
        kiesBedragPanel.add(eigenBedrag);

        breekaf = new JButton("Afbreken");
        breekaf.setBounds(1300, 745, 326, 74);
        kiesBedragPanel.add(breekaf);

        mainFrame.setVisible(true);

        knop20.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent s) {

                mainFrame.remove(kiesBedragPanel);
                kiesBedragPanel = null;
                kiesBiljet();

            }
        });

        knop50.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent s) {

                mainFrame.remove(kiesBedragPanel);
                soortBiljettenPanel = null;
                kiesBiljet();

            }
        });

        eigenBedrag.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent s) {

                mainFrame.remove(kiesBedragPanel);
                invoerBedragPanel = null;
                bedragInvoer();

            }
        });

        breekaf.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent s) {

                mainFrame.remove(kiesBedragPanel);
                beginschermPanel = null;
                beginscherm();

            }
        });

    }

    public void bedragInvoer() {
        initialize();
        invoerBedragPanel = new JPanel();
        mainFrame.add(invoerBedragPanel);
        invoerBedragPanel.setLayout(null);

        invoerBedrag = new JLabel();
        invoerBedrag.setHorizontalAlignment(SwingConstants.CENTER);
        invoerBedrag.setFont(new Font("Tahoma", Font.BOLD, 51));
        invoerBedrag.setText("Voer uw bedrag in. Let op beperkt tot tientallen!");
        invoerBedrag.setBounds(12, 189, 1890, 232);
        invoerBedragPanel.add(invoerBedrag);


        OkButton = new JButton("OK");
        OkButton.setBounds(1300, 463, 326, 74);
        invoerBedragPanel.add(OkButton);

        breekaf = new JButton("Afbreken");
        breekaf.setBounds(1300, 557, 326, 74);
        invoerBedragPanel.add(breekaf);

        eigenbedrag = new JTextArea();
        eigenbedrag.setFont(new Font("Tahoma", Font.PLAIN, 99));
        eigenbedrag.setBounds(792, 463, 250, 109);
        invoerBedragPanel.add(eigenbedrag);

        mainFrame.setVisible(true);

        OkButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent s) {

                mainFrame.remove(invoerBedragPanel);
                soortBiljettenPanel = null;
                kiesBiljet();

            }
        });

        breekaf.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent s) {

                mainFrame.remove(invoerBedragPanel);
                beginschermPanel = null;
                beginscherm();

            }
        });

    }

    public void kiesBiljet() {
        initialize();
        soortBiljettenPanel = new JPanel();
        mainFrame.add(soortBiljettenPanel);
        soortBiljettenPanel.setLayout(null);

        soortBiljetten = new JLabel();
        soortBiljetten.setHorizontalAlignment(SwingConstants.CENTER);
        soortBiljetten.setFont(new Font("Tahoma", Font.BOLD, 51));
        soortBiljetten.setText("Welke soort biljetten wilt u?");
        soortBiljetten.setBounds(12, 189, 1890, 232);
        soortBiljettenPanel.add(soortBiljetten);

        knop10 = new JButton("Biljetten van 10");
        knop10.setBounds(1300, 463, 326, 74);
        soortBiljettenPanel.add(knop10);

        knop20 = new JButton("Biljetten van 20");
        knop20.setBounds(1300, 557, 326, 74);
        soortBiljettenPanel.add(knop20);

        knop50 = new JButton("Biljetten van 50");
        knop50.setBounds(1300, 651, 326, 74);
        soortBiljettenPanel.add(knop50);

        mainFrame.setVisible(true);

        knop10.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent s) {

                mainFrame.remove(soortBiljettenPanel);
                transactieBonPanel = null;
                vraagOmTransactieBon();

            }
        });

        knop20.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent s) {

                mainFrame.remove(soortBiljettenPanel);
                transactieBonPanel = null;
                vraagOmTransactieBon();

            }
        });

        knop50.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent s) {

                mainFrame.remove(soortBiljettenPanel);
                transactieBonPanel = null;
                vraagOmTransactieBon();

            }
        });
    }

    public void vraagOmTransactieBon() {
        initialize();
        transactieBonPanel = new JPanel();
        mainFrame.add(transactieBonPanel);
        transactieBonPanel.setLayout(null);

        transactieBon = new JLabel();
        transactieBon.setHorizontalAlignment(SwingConstants.CENTER);
        transactieBon.setFont(new Font("Tahoma", Font.BOLD, 51));
        transactieBon.setText("Wilt u de transactie bon?");
        transactieBon.setBounds(12, 189, 1890, 232);
        transactieBonPanel.add(transactieBon);

        OkButton = new JButton("Ja");
        OkButton.setBounds(1300, 463, 326, 74);
        transactieBonPanel.add(OkButton);

        nee = new JButton("Nee");
        nee.setBounds(1300, 557, 326, 74);
        transactieBonPanel.add(nee);

        mainFrame.setVisible(true);

        OkButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent s) {

                mainFrame.remove(transactieBonPanel);
                verwerkingsTekstPanel = null;
                verwerkingsTekst();

            }
        });

        nee.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent s) {

                mainFrame.remove(transactieBonPanel);
                verwerkingsTekstPanel = null;
                verwerkingsTekst();
            }
        });
    }

    public void verwerkingsTekst() {

        verwerkingsTekstPanel = new JPanel();
        mainFrame.add(verwerkingsTekstPanel);
        verwerkingsTekstPanel.setLayout(null);

        JLabel txtpnHoudUwPinpas = new JLabel();
        txtpnHoudUwPinpas.setHorizontalAlignment(SwingConstants.CENTER);
        txtpnHoudUwPinpas.setFont(new Font("Tahoma", Font.BOLD, 51));
        txtpnHoudUwPinpas.setText("Uw transactie wordt verwerkt..");
        txtpnHoudUwPinpas.setBounds(12, 189, 1890, 232);
        verwerkingsTekstPanel.add(txtpnHoudUwPinpas);

        mainFrame.setVisible(true);

        mainFrame.remove(verwerkingsTekstPanel);
        beginschermPanel = null;
        beginscherm();
    }

    public void serialEvent(SerialPortEvent spe) {

        if (spe.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            /*
            try {
                String inputLine=input.readLine();
                if (inputLine.equals("#")) {
                    passwordField.setText("*");
                }
                else{
                    passwordField.setText(passwordField.getText()+inputLine);
                    if (passwordField.getText().length()>4) passwordField.setText(inputLine);
                    //System.out.println(inputLine);
                }

            } catch (Exception e) {

                System.err.println(e.toString());

            }
            */

            try {
                String inputLine = input.readLine();
                if (beginschermPanel != null) {


                    System.out.println(inputLine);
                    System.out.println(inputLine.length());
                    if (inputLine.equals(pas1)) {
                        pincode = 1234;
                        saldo = saldo1;
                        p1 = true;
                        mainFrame.remove(beginschermPanel);
                        invoerPinPanel = null;
                        beginschermPanel = null;
                        invoerPin();
                        invoerPinPanel.updateUI();
                        inputLine = "";
                    }
                    if (inputLine.equals(pas2)) {
                        pincode = 3333;
                        saldo = saldo2;
                        p2 = true;
                        mainFrame.remove(beginschermPanel);
                        invoerPinPanel = null;
                        beginschermPanel = null;
                        invoerPin();
                        invoerPinPanel.updateUI();
                        inputLine = "";
                    }


                }

                if (invoerPinPanel != null) {

                    if (inputLine.equals("*")) {
                        inputLine = null;
                        passwordField.setText("");
                    }

                    if (inputLine.equals("A")) {
                        if (passwordField.getText().equals(Integer.toString(pincode))) {
                        mainFrame.remove(invoerPinPanel);
                        invoerPinPanel= null;
                        menuPanel = null;
                        menu();
                        }
                    }
                    if (inputLine.equals("B")) {
                        mainFrame.remove(invoerPinPanel);
                        invoerPinPanel = null;
                        beginschermPanel = null;
                        beginscherm();
                    }
                    else {


                        passwordField.setText(passwordField.getText() + inputLine);
                        if (passwordField.getText().length() > 4) {
                            inputLine= null;
                            passwordField.setText(inputLine);
                        }
                        //System.out.println(inputLine);

                    }
                }


                if (menuPanel != null) {

                    if (inputLine.equals("A")) {
                        mainFrame.remove(menuPanel);
                        menuPanel = null;
                        geldOpnemenPanel = null;
                        geldOpnemen();
                        inputLine = "";
                    }

                    if (inputLine.equals("C")) {
                        mainFrame.remove(menuPanel);
                        menuPanel = null;
                        beginschermPanel = null;
                        beginscherm();
                        inputLine = "";
                    }
                    if(inputLine.equals("B")){
                        mainFrame.remove(menuPanel);
                        menuPanel=null;
                        saldoInfoPanel = null;
                        saldoInfo();
                        inputLine= "";
                    }
                }

                if(saldoInfoPanel != null){
                    saldoInfo.setText("Uw huidige saldo is "+saldo+" Euro");
                    saldoInfo.updateUI();
                    if (inputLine.equals("A")){
                        mainFrame.remove(saldoInfoPanel);
                        saldoInfoPanel = null;
                        geldOpnemenPanel = null;
                        geldOpnemen();
                        inputLine = "";

                    }
                    if (inputLine.equals("B")) {
                        mainFrame.remove(saldoInfoPanel);
                        saldoInfoPanel = null;
                        beginschermPanel = null;
                        beginscherm();
                        inputLine = "";

                    }
                }

                if(geldOpnemenPanel != null){
                    if(inputLine.equals("A")){
                        mainFrame.remove(geldOpnemenPanel);
                        geldOpnemenPanel= null;
                        kiesBedragPanel = null;
                        bedragKiezen();
                        inputLine= "";

                    }
                    if(inputLine.equals("B")){
                        mainFrame.remove(geldOpnemenPanel);
                        geldOpnemenPanel= null;
                        invoerBedragPanel = null;
                        bedragInvoer();
                        inputLine= "";

                    }
                    if(inputLine.equals("C")){
                        mainFrame.remove(geldOpnemenPanel);
                        geldOpnemenPanel= null;
                        saldoInfoPanel = null;
                        saldoInfo();
                        inputLine= "";

                    }
                    if(inputLine.equals("D")){
                        mainFrame.remove(geldOpnemenPanel);
                        geldOpnemenPanel= null;
                        beginschermPanel = null;
                        beginscherm();
                        inputLine= "";
                    }

                }

                if(kiesBedragPanel != null){
                    if(inputLine.equals("A")){
                        if(saldo > 20){
                            if(p1 = true){
                                saldo1 -= 20;
                                p2 = false;
                                p1 = false;


                            }
                            if(p2 = true){
                                saldo2 -= 20;

                                p2 = false;
                                p1 = false;
                            }

                            mainFrame.remove(kiesBedragPanel);
                            kiesBedragPanel = null;
                            beginschermPanel = null;
                            beginscherm();
                        }
                        else{
                            kiesBedrag.setText("u heeft niet genoeg saldo");
                            kiesBedrag.updateUI();
                        }
                    }
                    if(inputLine.equals("B")){
                        if(saldo > 50){
                            if(p1 = true){
                                saldo1 -= 50;
                                p2 = false;
                                p1 = false;
                            }
                            if(p2 = true){
                                saldo2-= 50;
                                p2 = false;
                                p1 = false;
                            }
                            mainFrame.remove(kiesBedragPanel);
                            kiesBedragPanel = null;
                            beginschermPanel = null;
                            beginscherm();
                        }
                        else{
                            kiesBedrag.setText("u heeft niet genoeg saldo");
                            kiesBedragPanel.updateUI();
                        }
                    }
                    if(inputLine.equals("C")){
                        mainFrame.remove(kiesBedrag);
                        kiesBedrag= null;
                        invoerBedrag = null;
                        bedragInvoer();
                        inputLine= "";
                    }
                    if(inputLine.equals("D")){
                        mainFrame.remove(kiesBedrag);
                        kiesBedrag= null;
                        beginschermPanel = null;
                        beginscherm();
                        inputLine= "";
                    }

                }
                if(invoerBedragPanel != null){
                    if(inputLine.equals("A")){

                        }
                    }
                    if(inputLine.equals("B")) {
                    }


                    if(inputLine.equals("C") == false || inputLine.equals("D") == false){
                        eigenbedrag.setText(eigenbedrag.getText() + inputLine);
                        if (eigenbedrag.getText().length() > 3) {
                            inputLine = null;
                            eigenbedrag.setText(inputLine);
                        }
                    }
                if(soortBiljettenPanel != null){

                }
                if(transactieBonPanel != null){


                }

                }
            catch (Exception e) {
                System.err.println(e.toString());
            }

            }

        }
    }
