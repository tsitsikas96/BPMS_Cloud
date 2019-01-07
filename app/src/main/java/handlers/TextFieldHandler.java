package handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JTextField;

import gui.AppGui;
import mechanism.App;

public class TextFieldHandler implements KeyListener{
	
	private App app;
	private JTextField tf_username,tf_passwd;
	private JFrame f;
	
	public TextFieldHandler(JTextField tf_username,JTextField tf_passwd,App a,JFrame f) {
		this.app = a;
		this.tf_username = tf_username;
		this.tf_passwd = tf_passwd;
		this.f = f;
	}
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			try {
				String username = tf_username.getText();
				String passwd = tf_passwd.getText();
				this.app.connect(username,passwd);
				f.dispose();
				new AppGui(this.app);
			}
			catch(SQLException io) {
				this.tf_username.setText("Insert Valid Username");
				this.tf_passwd.setText("Insert Valid Password");
			}
			catch(NumberFormatException n) {
				this.tf_username.setText("Insert Valid Username");
				this.tf_passwd.setText("Insert Valid Password");
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}
}
