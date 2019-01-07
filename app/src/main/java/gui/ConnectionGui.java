package gui;

import handlers.TextFieldHandler;
import mechanism.App;

import javax.swing.*;
import java.awt.*;

public class ConnectionGui extends JFrame {

    private JFrame address_frame;
    private App app;

    public ConnectionGui(App app){
        this.app = app;
        address_gui();
        while(this.isActive());
    }

    private void address_gui(){
        address_frame = new JFrame("Insert parameters to connect");
        JPanel up = new JPanel();
        JTextField usernametf = new JTextField();
        JPasswordField passwdtf = new JPasswordField();
        JTextField[] tfs = {usernametf,passwdtf};
        JLabel label1 = new JLabel("Username");
        JLabel label2 = new JLabel("Password");
        JLabel[] labels = {label1,label2};
        up.setLayout(new GridLayout(0,2));

        for(int i = 0;i<2;i++ ) {
            for(int j = 0; j<2;j++) {
                if(i==0) {
                    labels[j].setHorizontalAlignment(JLabel.CENTER);
                    labels[j].setFont(new Font(Font.SANS_SERIF,Font.BOLD,12));
                    up.add(labels[j]);
                }
                else{
                    tfs[j].setEditable(true);
                    tfs[j].setHorizontalAlignment(JLabel.CENTER);
                    tfs[j].setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
                    up.add(tfs[j]);
                }
            }
        }

        usernametf.addKeyListener(new TextFieldHandler(usernametf,passwdtf, app, address_frame));
        passwdtf.addKeyListener(new TextFieldHandler(usernametf,passwdtf, app, address_frame));
        address_frame.add(up,BorderLayout.NORTH);
        address_frame.setSize(500, 150);
        address_frame.setLocationRelativeTo(null);
        address_frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        address_frame.setVisible(true);

    }
}
