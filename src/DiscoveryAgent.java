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
                    System.out.println("\n\n");

                    System.out.println("DiscoveryAgent received request: " + message.getContent());

                    String content = message.getContent();
                    String[] parts = content.split(",");
                    int totalTasks = Integer.parseInt(parts[0]);
                    int numVMs = Integer.parseInt(parts[1]);
                    int maxTasksPerVM = Integer.parseInt(parts[2]);

                    // Simulate discovering services
                    String discoveredServices = "AWS,Google Cloud";
                    System.out.println("Discovered services: " + discoveredServices);

                    // Send services to VerificationAgent
                    ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
                    reply.addReceiver(getAID("VerificationAgent"));
                    reply.setContent(totalTasks + "," + numVMs + "," + maxTasksPerVM + "," + discoveredServices);
                    send(reply);
                    System.out.println("Sent services to VerificationAgent.");

                    System.out.println("\n");
                } else {
                    block();
                }
            }
        });
    }
}
