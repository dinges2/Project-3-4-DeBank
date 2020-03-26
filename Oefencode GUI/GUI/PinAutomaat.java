import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PinAutomaat{

    private JFrame mainFrame;
    private JPanel startingScreenPanel;
    private JPanel enterPinPanel;
    private JPanel mainMenuPanel;
    private JPanel withdrawPanel;
    private JPanel balancePanel;
    private JPanel billChoicePanel;
    private JPanel receiptPanel;
    private JPanel thanksPanel;
    
    private JPasswordField passwordField;

    private JButton abortButton;
    private JButton enterButton;
    private JButton withdrawButton;
    private JButton balanceButton;
    private JButton quick70Button;
    private JButton backToMainMenuButton;
    
    private JLabel startingScreenLabel;
    private JLabel enterPinLabel;
    private JLabel mainMenuLabel;
    private JLabel withdrawLabel;
    private JLabel balanceLabel;
    private JLabel billChoiceLabel;
    private JLabel receiptLabel;
    private JLabel thanksLabel;
    
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

            startingScreenLabel = new JLabel();
            startingScreenLabel.setFont(new Font("Roboto", Font.BOLD, 18));
            startingScreenLabel.setText("Houd uw pinpas voor de lezer.");
            grid.gridx = 5;
            grid.gridy = 0;
            grid.anchor = GridBagConstraints.PAGE_START;
            startingScreenPanel.add(startingScreenLabel, grid);

            JButton OkButton = new JButton("OK");
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

            enterPinLabel = new JLabel();
            enterPinLabel.setFont(new Font("Roboto", Font.BOLD, 18));
            enterPinLabel.setText("Voer uw pincode in.");
            grid.gridx = 5;
            grid.gridy = 0;
            grid.anchor = GridBagConstraints.PAGE_START;
            enterPinPanel.add(enterPinLabel, grid);

            passwordField = new JPasswordField(10);
            // passwordField.setActionCommand(command);
            grid.gridx = 4;
            grid.gridy = 2;
            grid.gridwidth = 3;
            enterPinPanel.add(passwordField, grid);

            enterButton = new JButton("Enter");
            grid.gridx = 4;
            grid.gridy = 3;
            grid.gridwidth = 1;
            enterPinPanel.add(enterButton, grid);

            abortButton = new JButton("Afbreken");
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

            mainMenuLabel = new JLabel();
            mainMenuLabel.setFont(new Font("Roboto", Font.BOLD, 18));
            mainMenuLabel.setText("Kies wat u wil doen.");
            grid.gridx = 3;
            grid.gridy = 0;
            grid.anchor = GridBagConstraints.PAGE_START;
            mainMenuPanel.add(mainMenuLabel, grid);

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

            withdrawButton.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {

                    mainFrame.remove(mainMenuPanel);
                    startingScreenPanel = null;
                    withdraw();
                }
            });
        }

        public void withdraw(){

            withdrawPanel =  new JPanel();
            mainFrame.add(withdrawPanel);
            GridBagConstraints grid = new GridBagConstraints();
            withdrawPanel.setLayout(new GridBagLayout());

            withdrawLabel = new JLabel();
            withdrawLabel.setFont(new Font("Roboto", Font.BOLD, 18));
            withdrawLabel.setText("Kies het bedrag dat u wilt pinnen.");
            grid.gridx = 5;
            grid.gridy = 0;
            grid.anchor = GridBagConstraints.PAGE_START;
            withdrawPanel.add(withdrawLabel, grid);
            
            JTextField textField = new JTextField(10);
            grid.gridx = 4;
            grid.gridy = 2;
            // grid.gridwidth = 3;
            withdrawPanel.add(textField, grid);

            enterButton = new JButton("Enter");
            grid.gridx = 8;
            grid.gridy = 2;
            // grid.gridwidth = 1;
            withdrawPanel.add(enterButton, grid);

            abortButton = new JButton("Afbreken");
            grid.gridx = 4;
            grid.gridy = 3;
            // grid.gridwidth = 1;
            withdrawPanel.add(abortButton, grid);

            backToMainMenuButton = new JButton();
            grid.gridx = 6;
            grid.gridy = 3;
            // grid.gridwidth = 1;
            withdrawPanel.add(backToMainMenuButton, grid);
            mainFrame.setVisible(true);

            abortButton.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {

                    mainFrame.remove(withdrawPanel);
                    startingScreenPanel = null;
                    startingSCreen();
                }
            });

            backToMainMenuButton.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {

                    mainFrame.remove(withdrawPanel);
                    mainMenuPanel = null;
                    mainMenu();
                }
            });
        }
}