package mechanism;

import java.sql.*;
import gui.ConnectionGui;

public class App {
	
	private Connection con = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private MeasurementList ml;
	
	public App() {
		ml = new MeasurementList();
	}
	
	public void connect(String username,String password) throws SQLException {

		String url= String.format("jdbc:sqlserver://bpms-measurements.database.windows.net:1433;database=bpms-db;" +
				"user=%s@bpms-measurements;password=%s;encrypt=true;trustServerCertificate=false;" +
				"hostNameInCertificate=*.database.windows.net;loginTimeout=30",username,password);
		con = DriverManager.getConnection(url);
		System.out.println("Connected");
	}
	
	public void disconnect() {
		try {
			con.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void getMeasurements(String query) {
		ml.clear();
		try {
			statement = con.createStatement();
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				ml.appendEntry(new Measurement(resultSet.getTimestamp(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4),
						resultSet.getString(5)));
			}
		} 
		catch (SQLException e) {
			System.out.println("Can't create statement");
		}
		
	}

	public MeasurementList getMeasurmentList() {
		return ml;
	}
	
	public void clearList() {
		ml.clearList();
	}

	public static void main(String[] args) {
		App app = new App();
		ConnectionGui congui = new ConnectionGui(app);
	}
}
