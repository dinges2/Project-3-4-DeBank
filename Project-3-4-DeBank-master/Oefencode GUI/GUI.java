import javax.swing.*;

public class GUI{

    public static void main(String[] args){

        JFrame frame1 = new JFrame("GetBank");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setSize(320, 480);
        JButton button1 = new JButton("Get");
        frame1.getContentPane().add(button1);
        frame1.setVisible(true);
    }
}