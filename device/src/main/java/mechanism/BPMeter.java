package mechanism;

import com.google.gson.Gson;
import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.response.ReadResponse;

public class BPMeter extends BaseInstanceEnabler implements Runnable{

	private Measurement m;
	private String jsonString;
	private Gson gson;
	private Measurement m1;
	private Measurement m2;
	private Measurement m3;
	private Measurement m4;
	private Measurement m5;
	private Measurement m6;

	public BPMeter(){
		gson = new Gson();
		new Thread(this).start();
	}

	void sendMeasurement() {
		this.jsonString = this.gson.toJson(this.m);
		fireResourcesChange(5700);
	}

	@Override
	public ReadResponse read(int i) {
		switch(i){
			case 5700:
				return ReadResponse.success(i,this.jsonString);
		}
		return ReadResponse.notFound();
	}

	@Override
	public void run() {
		m1 = new Measurement(148, 78, 70, "Bill");
		m2 = new Measurement(124,80, 90, "Deckard");
		m3 = new Measurement(122, 55, 67, "Jill");
		m4 = new Measurement(150, 100, 140, "Joel");
		m5 = new Measurement(140, 68, 89, "Bill");
		m6 = new Measurement(138, 65, 78, "Becky");
		Measurement[] l = {m1,m2,m3,m4,m5,m6};
		this.m = null;
		this.jsonString = this.gson.toJson(this.m);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(int i = 0 ; i <l.length ; i++){
			this.m = l[i];
			sendMeasurement();
			System.out.println("Measurement sent");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
