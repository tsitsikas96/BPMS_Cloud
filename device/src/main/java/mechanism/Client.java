package mechanism;

import static org.eclipse.leshan.LwM2mId.*;
import static org.eclipse.leshan.client.object.Security.*;

import java.io.File;
import java.util.List;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.leshan.LwM2m;
import org.eclipse.leshan.LwM2mId;
import org.eclipse.leshan.client.californium.LeshanClient;
import org.eclipse.leshan.client.californium.LeshanClientBuilder;
import org.eclipse.leshan.client.object.Device;
import org.eclipse.leshan.client.object.Server;
import org.eclipse.leshan.client.resource.LwM2mObjectEnabler;
import org.eclipse.leshan.client.resource.ObjectsInitializer;
import org.eclipse.leshan.core.model.LwM2mModel;
import org.eclipse.leshan.core.model.ObjectLoader;
import org.eclipse.leshan.core.model.ObjectModel;
import org.eclipse.leshan.core.request.BindingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {
    private static final Logger LOG = LoggerFactory.getLogger(Client.class);

    private final static String[] modelPaths = new String[] { "5000.xml" };

    private static final int BPMETER = 5000;
    private final static String DEFAULT_ENDPOINT = "BP Meter";
    private final static String endpoint = DEFAULT_ENDPOINT;
    private final static String serverURI = "coap://52.236.144.142:" + LwM2m.DEFAULT_COAP_PORT;
    private final static String localAddress = null;
    private final static int localPort = 0;


    public static void main(final String[] args) {

        createAndStartClient(endpoint, localAddress, localPort, serverURI);
    }

    public static void createAndStartClient(String endpoint, String localAddress, int localPort,
                                            String serverURI) {


        // Initialize model
        List<ObjectModel> models = ObjectLoader.loadDefault();
        models.addAll(ObjectLoader.loadDdfResources("/models", modelPaths));

        // Initialize object list
        ObjectsInitializer initializer = new ObjectsInitializer(new LwM2mModel(models));

        initializer.setInstancesForObject(SECURITY, noSec(serverURI, 123));
        initializer.setInstancesForObject(SERVER, new Server(123, 30, BindingMode.U, false));
        initializer.setInstancesForObject(LwM2mId.DEVICE, new Device("Eclipse Leshan", "model12345", "12345", "U"));
        initializer.setInstancesForObject(BPMETER, new BPMeter());
        List<LwM2mObjectEnabler> enablers = initializer.create(SECURITY, SERVER, DEVICE, BPMETER);

        // Create CoAP Config
        NetworkConfig coapConfig;
        File configFile = new File(NetworkConfig.DEFAULT_FILE_NAME);
        if (configFile.isFile()) {
            coapConfig = new NetworkConfig();
            coapConfig.load(configFile);
        } else {
            coapConfig = LeshanClientBuilder.createDefaultNetworkConfig();
            coapConfig.store(configFile);
        }

        // Create client
        LeshanClientBuilder builder = new LeshanClientBuilder(endpoint);
        builder.setLocalAddress(localAddress, localPort);
        builder.setObjects(enablers);
        builder.setCoapConfig(coapConfig);
        final LeshanClient client = builder.build();


        // Start the client
        client.start();

        // De-register on shutdown and stop client.
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                client.destroy(true); // send de-registration request before destroy
            }
        });
    }

}