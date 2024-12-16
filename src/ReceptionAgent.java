import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class ReceptionAgent extends Agent {
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
                    int totalTasks = Integer.parseInt(parts[0]);
                    int numVMs = Integer.parseInt(parts[1]);
                    int maxTasksPerVM = Integer.parseInt(parts[2]);

                    System.out.println("Received input: " +
                            totalTasks + " tasks, " + numVMs + " VMs, " + maxTasksPerVM + " tasks per VM.");

                    // Send services to DiscoveryAgent
                    ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
                    reply.addReceiver(getAID("DiscoveryAgent"));
                    reply.setContent(totalTasks + "," + numVMs + "," + maxTasksPerVM);
                    send(reply);

                    System.out.println("Sent services to DiscoveryAgent.");
                }
            }
        });
    }
}
