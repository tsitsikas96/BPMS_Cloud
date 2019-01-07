package handlers;

import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import mechanism.App;
import mechanism.MeasurementList;

public class SortHandler implements ActionListener{
	private App app;
	private DefaultTableModel model;
	private int query_select;
	
	public SortHandler(App a, DefaultTableModel m,int query_sel) {
		this.app = a;
		this.model = m;
		this.query_select = query_sel;
	}
	public void actionPerformed(ActionEvent e) {
		String query;
		app.clearList();
		switch(query_select) {
			case 0:
				query = "SELECT * FROM MEASUREMENTS ORDER BY [user] ASC;";
				app.getMeasurements(query);
				break;
			case 1:
				query = "SELECT * FROM MEASUREMENTS ORDER BY [user] DESC;";
				app.getMeasurements(query);
				break;
			case 2:
				query = "SELECT * FROM MEASUREMENTS ORDER BY date ASC;";
				app.getMeasurements(query);
				break;
			case 3:
				query = "SELECT * FROM MEASUREMENTS ORDER BY date DESC;";
				app.getMeasurements(query);
				break;
		}
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
