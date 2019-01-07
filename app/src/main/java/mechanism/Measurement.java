package mechanism;

import java.text.SimpleDateFormat;
import java.sql.Timestamp;

public class Measurement {
	private Timestamp date;
	private int systolicBP;
	private int diastolicBP;
	private int heartRate;
	private String userID;
	
	public Measurement(Timestamp date, int systolicBP, int diastolicBP, int heartRate, String userID) {
		this.date = date;
		this.systolicBP = systolicBP;
		this.diastolicBP = diastolicBP;
		this.heartRate = heartRate;
		this.userID = userID;
	}
	
	private String getDate() {
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm");
		return ft.format(this.date);
	}
	
	private int getSystolicBP() {
		return this.systolicBP;
	}
	
	private int getDiastolicBP() {
		return this.diastolicBP;
	}
	
	private int getheartRate() {
		return this.heartRate;
	}
	
	private String getUserId() {
		return this.userID;
	}
	
	public Object[] getMeasurmentData() {
		Object[] Data = {getDate(),getDiastolicBP(),getSystolicBP(),getheartRate(),getUserId()};
		return Data;
	}
	
	public void showMeasurement() {
		System.out.println("Date: " + getDate() +  "\nSystolic Pressure: "+ getDiastolicBP()
			+ "\nDiastolicPressure: "+ getSystolicBP() + "\nHeart Rate: "+ getheartRate() + "\nUser: "+ getUserId() );
	}
}
