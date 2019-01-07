package handlers;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import mechanism.App;
import mechanism.MeasurementList;

public class SearchHandler implements ActionListener{
	
	private App app;
	private DefaultTableModel model;
	private JLabel label1,label2;
	private JTextField tf1,tf2;
	private JFrame f;
	
	public SearchHandler(App a,DefaultTableModel m) {
		this.app = a;
		this.model = m;
	}
	
	public void actionPerformed(ActionEvent e) {
		JPanel textfields = new JPanel();
		JPanel buttonPanel = new JPanel();
		JLabel[] labels = {label1,label2};
		String[] texts = {"Date (yyyy-mm-dd)","Name"};
		tf1 = new JTextField();
		tf2 = new JTextField();
		JTextField[] tfs = {tf1,tf2};
		f = new JFrame("Search..."); 
		textfields.setLayout(new GridLayout(0,2));
		for(int i = 0 ; i<labels.length;i++) {
			labels[i] = new JLabel();
			labels[i].setText(texts[i]);
			labels[i].setHorizontalAlignment(JLabel.CENTER);
			labels[i].setFont(new Font(Font.SANS_SERIF,Font.BOLD,12));
			textfields.add(labels[i]);
		}
		for(int i = 0 ; i<labels.length;i++) {
			tfs[i].setEditable(true);
			tfs[i].setHorizontalAlignment(JLabel.CENTER);
			tfs[i].setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
			textfields.add(tfs[i]);
		}
		JButton button = new JButton("Search");
		buttonPanel.add(button);
		button.addActionListener(new ButtonHandler(tf1,tf2,f));
		f.add(textfields,BorderLayout.NORTH);
		f.add(buttonPanel, BorderLayout.CENTER);
		
		f.setSize(800, 135);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setVisible(true);
	}
	class ButtonHandler implements ActionListener{
		
		private JTextField tf1,tf2;
		private JFrame f;
		
		public ButtonHandler(JTextField tf1, JTextField tf2,JFrame f) {
			this.tf1 = tf1;
			this.tf2 = tf2;
			this.f = f;
		}
		public void actionPerformed(ActionEvent e) {
			String date,name;
			app.clearList();
			date = tf1.getText();
			name = tf2.getText();
			if(date.matches("") || name.matches("")) {
				tf1.setText("Field can't be empty");
				tf2.setText("Field can't be empty");
				return;
			}
			String query = "SELECT * FROM MEASUREMENTS WHERE [user] = '" + name + "' AND CAST([date] AS DATE) = '" + date + "';";
			app.getMeasurements(query);
			updateTable();
			f.dispose();
		}
		
		private void updateTable() {
			Object[] data;
			MeasurementList l = app.getMeasurmentList();
			for(int i = model.getRowCount() - 1 ;i>=0;i--) {
				model.removeRow(i);
			}
			for(int i = 0;i<l.size();i++) {
				data = l.get(i).getMeasurmentData();
				model.addRow(data);
			}
			model.fireTableDataChanged();
		}
	}
}
