import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class DiscoveryAgent extends Agent {
    @Override
    protected void setup() {
        System.out.println("DiscoveryAgent started.");

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage message = receive();
                if (message != null) {
                    System.out.println("\n\nDiscoveryAgent received request: " + message.getContent());

                    // Simulate discovering services
                    String discoveredServices = "AWS, Google Cloud";
                    System.out.println("Discovered services: " + discoveredServices);

                    // Send services to VerificationAgent
                    ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
                    reply.addReceiver(getAID("VerificationAgent"));
                    reply.setContent(discoveredServices);
                    send(reply);
                    System.out.println("Sent services to VerificationAgent.\n");
                } else {
                    block();
                }
            }
        });
    }
}
