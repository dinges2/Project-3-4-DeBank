import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PinAutomaat{

    private JFrame mainFrame;
    private JPanel startingScreenPanel;
    private JPanel enterPinPanel;
    private JPanel mainMenuPanel;
    
    private JPasswordField passwordField;

    private JButton abortButton;
    private JButton enterButton;
    private JButton withdrawButton;
    private JButton balanceButton;
    private JButton quick70Button;
    
    private JLabel startingScreen;
    private JLabel enterPin;
    private JLabel mainMenu;

    private int pincode;

    public PinAutomaat(){

        mainFrame = new JFrame("Pinautomaat GUI");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(480, 320);
        mainFrame.setResizable(false);

        startingSCreen();
        mainFrame.setVisible(true);
    }

        public void startingSCreen(){
            
            startingScreenPanel = new JPanel();
            mainFrame.add(startingScreenPanel);
            GridBagConstraints grid = new GridBagConstraints();
            startingScreenPanel.setLayout(new GridBagLayout());
            // startingScreenPanel.setLayout(null);

            startingScreen = new JLabel();
            // startingScreen.setHorizontalAlignment(SwingConstants.CENTER);
            startingScreen.setFont(new Font("Roboto", Font.BOLD, 18));
            startingScreen.setText("Houd uw pinpas voor de lezer.");
            // startingScreen.setBounds(100, 100, 300, 50);
            grid.gridx = 5;
            grid.gridy = 0;
            grid.anchor = GridBagConstraints.PAGE_START;
            startingScreenPanel.add(startingScreen, grid);

            JButton OkButton = new JButton("OK");
            // OkButton.setBounds(175, 160, 80, 30);
            grid.gridx = 5;
            grid.gridy = 5;
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

            enterPin = new JLabel();
            // enterPin.setHorizontalAlignment(SwingConstants.CENTER);
            enterPin.setFont(new Font("Roboto", Font.BOLD, 18));
            enterPin.setText("Voer uw pincode in.");
            // enterPin.setBounds(100, 10, 300, 50);
            grid.gridx = 5;
            grid.gridy = 0;
            grid.anchor = GridBagConstraints.PAGE_START;
            enterPinPanel.add(enterPin, grid);

            // JTextField textField = new JTextField(10);
            // textField.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
            // grid.gridx = 5;
            // grid.gridy = 1;
            // enterPinPanel.add(textField, grid);

            passwordField = new JPasswordField(10);
            passwordField.setActionCommand(command);
            // passwordField.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
            // passwordField.setBounds(100, 100, 100, 50);
            grid.gridx = 4;
            grid.gridy = 2;
            grid.gridwidth = 3;
            enterPinPanel.add(passwordField, grid);

            enterButton = new JButton("Enter");
            // enterButton.setBounds(60, 160, 100, 30);
            grid.gridx = 4;
            grid.gridy = 3;
            grid.gridwidth = 1;
            enterPinPanel.add(enterButton, grid);

            abortButton = new JButton("Afbreken");
            // abortButton.setBounds(170, 160, 100, 30);
            grid.gridx = 6;
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
                    startingSCreen();  
                }
            });
        }

        public void mainMenu(){

            mainMenuPanel = new JPanel();
            mainFrame.add(mainMenuPanel);
            GridBagConstraints grid = new GridBagConstraints();
            mainMenuPanel.setLayout(new GridBagLayout());

            mainMenu = new JLabel();
            // mainMenu.setHorizontalAlignment(SwingConstants.CENTER);
            mainMenu.setFont(new Font("Roboto", Font.BOLD, 18));
            mainMenu.setText("Kies wat u wil doen.");
            // mainMenu.setBounds(100, 10, 300, 50);
            grid.gridx = 3;
            grid.gridy = 0;
            grid.anchor = GridBagConstraints.PAGE_START;
            mainMenuPanel.add(mainMenu, grid);

            withdrawButton = new JButton("Opnemen");
            grid.gridx = 0;
            grid.gridy = 3;
            grid.insets = new Insets(5, 5, 5, 0);
            mainMenuPanel.add(withdrawButton, grid);

            balanceButton = new JButton("Saldo");
            grid.gridx = 0;
            grid.gridy = 5;
            grid.insets = new Insets(5, 5, 5, 0);
            mainMenuPanel.add(balanceButton, grid);

            quick70Button = new JButton("Snel 70");
            grid.gridx = 6;
            grid.gridy = 3;
            grid.insets = new Insets(5, 5, 5, 0);
            mainMenuPanel.add(quick70Button, grid);

            abortButton = new JButton("Afbreken");
            grid.gridx = 6;
            grid.gridy = 5;
            grid.insets = new Insets(5, 5, 5, 0);
            mainMenuPanel.add(abortButton, grid);
            mainFrame.setVisible(true);

            abortButton.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {

                    mainFrame.remove(mainMenuPanel);
                    startingScreenPanel = null;
                    startingSCreen();
                }
            });
        }
}