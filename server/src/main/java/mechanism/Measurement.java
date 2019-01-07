package mechanism;

import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
//import java.io.Serializable;

//@SuppressWarnings("serial")
public class Measurement{
	private int systolicBP;
	private int diastolicBP;
	private int heartRate;
	private String userID;
	
	public Measurement( int month, int day ,int year,int hour, int mins, int systolicBP, int diastolicBP, int heartRate, String userID) {
		this.systolicBP = systolicBP;
		this.diastolicBP = diastolicBP;
		this.heartRate = heartRate;
		this.userID = userID;
	}
	
	public Measurement getMeasurement() {
		return this;
	}

	
	public int getSystolicBP() {
		return this.systolicBP;
	}
	
	public int getDiastolicBP() {
		return this.diastolicBP;
	}
	
	public int getheartRate() {
		return this.heartRate;
	}
	
	public String getUserId() {
		return this.userID;
	}
	
	public void showMeasurement() {
		System.out.println("\nSystolic Pressure: "+ getDiastolicBP()
			+ "\nDiastolicPressure: "+ getSystolicBP() + "\nHeart Rate: "+ getheartRate() + "\nUser: "+ getUserId() );
	}
}
