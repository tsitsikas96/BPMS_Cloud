package mechanism;

import com.google.gson.Gson;
import org.eclipse.leshan.core.model.ObjectLoader;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.observation.Observation;
import org.eclipse.leshan.core.request.ObserveRequest;
import org.eclipse.leshan.core.response.ObserveResponse;
import org.eclipse.leshan.server.californium.LeshanServerBuilder;
import org.eclipse.leshan.server.californium.impl.LeshanServer;
import org.eclipse.leshan.server.model.LwM2mModelProvider;
import org.eclipse.leshan.server.model.StaticModelProvider;
import org.eclipse.leshan.server.observation.ObservationListener;
import org.eclipse.leshan.server.registration.Registration;
import org.eclipse.leshan.server.registration.RegistrationListener;
import org.eclipse.leshan.server.registration.RegistrationUpdate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

public class Server {


	private static Measurement m;
	private static Connection con = null;
	private static Statement statement = null;
	private static Gson gson;

	private final static String[] modelPaths = new String[] { "5000.xml"};

	public Server(){
		connectDB("bpms_user","Sseg2019");
		gson = new Gson();
		createAndStartServer();
	}

	private static void createAndStartServer(){

		LeshanServerBuilder builder = new LeshanServerBuilder();

		List<ObjectModel> models = ObjectLoader.loadDefault();
		models.addAll(ObjectLoader.loadDdfResources("/models/", modelPaths));
		LwM2mModelProvider modelProvider = new StaticModelProvider(models);
		builder.setObjectModelProvider(modelProvider);

		final LeshanServer server = builder.build();

		server.getRegistrationService().addListener(new RegistrationListener() {

			public void registered(Registration registration, Registration previousReg,
								   Collection<Observation> previousObsersations) {
				System.out.println("New Device: " + registration.getEndpoint());
				try {
					ObserveResponse response = server.send(registration, new ObserveRequest(5000,0,5700));
					if (response.isSuccess()) {
						System.out.println("Observing: " + registration.getEndpoint() + "@" + registration.getAddress());
					}else {
						System.out.println("Failed to observe: " + response.getCode() + " " + response.getErrorMessage());
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			public void updated(RegistrationUpdate update, Registration updatedReg, Registration previousReg) {
				System.out.println("Device is still here: " + updatedReg.getEndpoint());
			}

			public void unregistered(Registration registration, Collection<Observation> observations, boolean expired,
									 Registration newReg) {
				System.out.println("Device left: " + registration.getEndpoint());
			}
		});

		server.getObservationService().addListener(new ObservationListener() {
			@Override
			public void newObservation(Observation observation, Registration registration) {}

			@Override
			public void cancelled(Observation observation) {}

			@Override
			public void onResponse(Observation observation, Registration registration, ObserveResponse observeResponse) {
				if (observeResponse.isSuccess()) {
					String jsonString = ((LwM2mResource)observeResponse.getContent()).getValue().toString();
					m = gson.fromJson(jsonString,Measurement.class);
					System.out.println("Measurement received");
					String query = "if not exists (select * from sysobjects where name='measurements' and xtype='U') " +
							"    create table measurements(" +
							"	[date] datetime not null primary key," +
							"    [systolicPressure] int not null," +
							"    [diastolicPressure] int not null," +
							"    [heartRate] int not null," +
							"    [user] varchar(30) not null" +
							");" +
							"insert dbo.measurements ([date], [systolicPressure], [diastolicPressure], [heartRate], [user]) " +
							"values(GETDATE()," + m.getSystolicBP() + "," + m.getDiastolicBP() + "," + m.getheartRate() + ",'" + m.getUserId() + "');";
					try {
						statement = con.createStatement();
						statement.executeUpdate(query);
					}
					catch (SQLException e) {
						System.out.println("Can't send query");
					}
				}else {
					System.out.println("Failed to read: " + observeResponse.getCode() + " " + observeResponse.getErrorMessage());
				}
			}

			@Override
			public void onError(Observation observation, Registration registration, Exception e) {

			}
		});

		server.start();
	}

	private static void connectDB(String username , String password ){
		String url= String.format("jdbc:sqlserver://bpms-measurements.database.windows.net:1433;database=bpms-db;" +
				"user=%s@bpms-measurements;password=%s;encrypt=true;trustServerCertificate=false;" +
				"hostNameInCertificate=*.database.windows.net;loginTimeout=30;",username,password);
		try {
			con = DriverManager.getConnection(url);
			System.out.println("Connected to DB");
		} catch (SQLException e) {
			System.out.println("Can't connect to database");
		}
	}

	public static void main(String[] args) {
		Server s = new Server();
	}
	
}