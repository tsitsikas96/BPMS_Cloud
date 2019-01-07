package handlers;

import java.awt.event.*;

import javax.swing.table.DefaultTableModel;

import mechanism.App;
//import mechanism.AppTest;
import mechanism.MeasurementList;

public class RefreshHandler implements ActionListener{
	
	private App app;
	private DefaultTableModel model;
	
	public RefreshHandler(App a,DefaultTableModel m) {
		this.app = a;
		this.model = m;
	}
	public void actionPerformed(ActionEvent e) {
		app.clearList();
		String query = "SELECT * FROM MEASUREMENTS;";
		app.getMeasurements(query);
		updateTable();
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
