import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class InfrastructureAgent extends Agent {
    @Override
    protected void setup() {
        System.out.println("InfrastructureAgent started.");

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage message = receive();
                if (message != null) {
                    System.out.println("\n\nInfrastructureAgent received service: " + message.getContent());

                    // Simulate resource allocation
                    System.out.println("Allocating resources for: " + message.getContent() + "\n");
                } else {
                    block();
                }
            }
        });
    }
}
