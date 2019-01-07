package handlers;

import java.awt.event.*;

import javax.swing.table.DefaultTableModel;

import mechanism.App;

public class ClearHandler implements ActionListener{
	
	private App app;
	private DefaultTableModel model;
	
	public ClearHandler(App a,DefaultTableModel m) {
		this.app = a;
		this.model = m;
	}
	public void actionPerformed(ActionEvent e) {
		app.clearList();
		updateTable();
	}
	
	private void updateTable() {
		for(int i = model.getRowCount() - 1 ;i>=0;i--) {
			model.removeRow(i);
		}
		model.fireTableDataChanged();
	}
}
