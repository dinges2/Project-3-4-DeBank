import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PinAutomaat{

    private JFrame mainFrame;
    private JPanel startingScreenPanel;
    private JPanel enterPinPanel;
    private JPanel mainMenuPanel;
    
    private JPasswordField passwordField;

    private JButton abortButton;
    private JButton enterButton;

    private JLabel startingScreen;
    private JLabel enterPin;

    private int pincode;

    public PinAutomaat(){

        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(480, 320);
        mainFrame.setResizable(false);

        startingSCreen();
        mainFrame.setVisible(true);
    }

        public void startingSCreen(){
            
            startingScreenPanel = new JPanel();
            mainFrame.add(startingScreenPanel);
            startingScreenPanel.setLayout(null);

            startingScreen = new JLabel();
            startingScreen.setHorizontalAlignment(SwingConstants.CENTER);
            startingScreen.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
            startingScreen.setText("Houd uw pinpas voor de lezer.");
            startingScreen.setBounds(100, 100, 300, 50);
            startingScreenPanel.add(startingScreen);

            JButton OkButton = new JButton("OK");
            OkButton.setBounds(175, 160, 80, 30);
            startingScreenPanel.add(OkButton);
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
            enterPinPanel.setLayout(null);

            enterPin = new JLabel();
            enterPin.setHorizontalAlignment(SwingConstants.CENTER);
            enterPin.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
            enterPin.setText("Voer uw pincode in.");
            enterPin.setBounds(100, 10, 300, 50);
            enterPinPanel.add(enterPin);

            passwordField = new JPasswordField();
            passwordField.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
            passwordField.setBounds(100, 100, 100, 50);
            enterPinPanel.add(passwordField);

            enterButton = new JButton("Enter");
            enterButton.setBounds(60, 160, 100, 30);
            enterPinPanel.add(enterButton);

            abortButton = new JButton("Abort");
            abortButton.setBounds(170, 160, 100, 30);
            enterPinPanel.add(abortButton);
            mainFrame.setVisible(true);
        }
}