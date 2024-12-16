import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class ReceptionAgent extends Agent {
    @Override
    protected void setup() {
        System.out.println("ReceptionAgent started.");

        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                // Simulate receiving client request
                String clientRequest = "Request: Low-latency video streaming + High-availability storage";
                System.out.println("Received client request: " + clientRequest);

                // Forward request to Discovery Node
                sendToDiscoveryNode(clientRequest);
            }
        });
    }

    private void sendToDiscoveryNode(String request) {
        // Logic to communicate with DiscoveryAgent
        System.out.println("Forwarding request to DiscoveryAgent: " + request);
    }
}
