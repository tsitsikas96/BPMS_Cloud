package mechanism;

import java.util.LinkedList;
import java.io.*;


@SuppressWarnings("serial")
public class MeasurementList extends LinkedList<Measurement> implements Serializable{
	
	public MeasurementList() {
		super();
	}
	
	public void appendEntry(Measurement m) {
		this.add(m);
	}
	
//	public void showEntries() {
//		for(int i = 0; i<this.size();i++) {
//			this.get(i).showMeasurement();
//			System.out.println();
//		}
//	}
	
	public void clearList() {
		this.clear();
	}
	
}
