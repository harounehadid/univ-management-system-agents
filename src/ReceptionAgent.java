import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class ReceptionAgent extends Agent {
    @Override
    protected void setup() {
        System.out.println("ReceptionAgent started.");

        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                // Simulate receiving a client request
                String clientRequest = "Request: Low-latency video streaming + High-availability storage";
                System.out.println("\n\n" + "Received client request: " + clientRequest);

                // Send request to DiscoveryAgent via ACLMessage
                ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
                message.addReceiver(getAID("DiscoveryAgent"));
                message.setContent(clientRequest);
                send(message);
                System.out.println("Sent request to DiscoveryAgent.\n");
            }
        });
    }
}
