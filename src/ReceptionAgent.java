import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class ReceptionAgent extends Agent {
    private int totalTasks;
    private int numVMs;
    private int maxTasksPerVM;

    @Override
    protected void setup() {
        System.out.println("ReceptionAgent started.");

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage message = receive();
                if (message != null) {
                    System.out.println("\n\n");

                    String content = message.getContent();
                    String[] parts = content.split(",");
                    totalTasks = Integer.parseInt(parts[0]);
                    numVMs = Integer.parseInt(parts[1]);
                    maxTasksPerVM = Integer.parseInt(parts[2]);

                    System.out.println("Received input: " +
                            totalTasks + " tasks, " + numVMs + " VMs, " + maxTasksPerVM + " tasks per VM.");

                    // Send the data to the DiscoveryAgent
                    sendToDiscoveryAgent();
                    
                    System.out.println("\n");
                }
            }
        });
    }

    private void sendToDiscoveryAgent() {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.addReceiver(getAID("DiscoveryAgent"));
        message.setContent(totalTasks + "," + numVMs + "," + maxTasksPerVM);
        send(message);
        System.out.println("Sent input to DiscoveryAgent.");
    }
}
