package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import handlers.*;
import mechanism.App;

@SuppressWarnings("serial")
public class AppGui extends JFrame{
	
	private static enum Sort_Messages{
		NAME_ASC(0),
		NAME_DESC(1),
		DATETIME_ASC(2),
		DATETIME_DESC(3);
		
		private final int messageValue;
		
		private Sort_Messages(int val) {
			this.messageValue = val;
		}
		
		public int getMessageValue() {
			return this.messageValue;
		}
	}
	
	private App app;
	private DefaultTableModel model;

	
	public AppGui(App app) {
		super("Blood Pressure Monitoring Application");
		this.app = app;
		gui_init();
	}


	
	private void gui_init(){
		
		create_table();
		create_menuBar();
		
		this.setSize(800, 800);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private void create_menuBar() {
		JMenuBar menu = new JMenuBar();
		
//---------------Menus-----------------//
		
		JMenu file = new JMenu("FIle");
		JMenu view = new JMenu("View");
		JMenu submenu_name = new JMenu("Sort by Name");
		JMenu submenu_datetime = new JMenu("Sort by Datetime");
		
//---------------File Menu Items--------------//
		
		JMenuItem newItem = new JMenuItem("Refresh");
		newItem.setToolTipText("Get new Measurments from Server");
		newItem.addActionListener(new RefreshHandler(this.app,this.model));
		
		JMenuItem clearItem = new JMenuItem("Clear Table");
		clearItem.setToolTipText("Clears the Measurements loaded from Server");
		clearItem.addActionListener(new ClearHandler(app, model));
		
		JMenuItem searchItem = new JMenuItem("Search");
		searchItem.setToolTipText("Search measurement in server");
		searchItem.addActionListener(new SearchHandler(app, model));
		
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setToolTipText("Exit Application");
		exitItem.addActionListener((ActionEvent event) -> {
			app.disconnect();;
			System.exit(1);});
		
//---------------View Menu Items----------------//
		
		JMenuItem sortAscName = new JMenuItem("Ascending");
		sortAscName.setToolTipText("Sort A-Z");
		sortAscName.addActionListener(new SortHandler(this.app,this.model,Sort_Messages.NAME_ASC.getMessageValue()));
		
		JMenuItem sortDescName = new JMenuItem("Descending");
		sortDescName.setToolTipText("Sort Z-A");
		sortDescName.addActionListener(new SortHandler(this.app,this.model,Sort_Messages.NAME_DESC.getMessageValue()));
		
		JMenuItem sortAscDatetime = new JMenuItem("Ascending");
		sortAscDatetime.setToolTipText("Sort Oldest First");
		sortAscDatetime.addActionListener(new SortHandler(this.app,this.model,Sort_Messages.DATETIME_ASC.getMessageValue()));
		
		JMenuItem sortDescDatetime = new JMenuItem("Descending");
		sortDescDatetime.setToolTipText("Sort Newest First");
		sortDescDatetime.addActionListener(new SortHandler(this.app,this.model,Sort_Messages.DATETIME_DESC.getMessageValue()));
		
		file.add(newItem);
		file.add(clearItem);
		file.add(searchItem);
		file.add(exitItem);
		submenu_name.add(sortAscName);
		submenu_name.add(sortDescName);
		submenu_datetime.add(sortAscDatetime);
		submenu_datetime.add(sortDescDatetime);
		view.add(submenu_name);
		view.add(submenu_datetime);
		menu.add(file);
		menu.add(view);
		setJMenuBar(menu);
	}
	
	private void create_table() {
		String[] cols = {"Date","Systolic Pressure","Diastolic Pressure","Heart Rate","User"};
		int numRows = 0;
		model = new DefaultTableModel(numRows, cols.length) ;
		model.setColumnIdentifiers(cols);
		JTable table = new JTable(model);
		table.setRowHeight(50);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.setDefaultRenderer(Object.class, centerRenderer);
		table.setVisible(true);
	   	JScrollPane sp = new JScrollPane(table);
		this.add(sp);
	}
}
