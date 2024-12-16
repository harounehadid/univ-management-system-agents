import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class VerificationAgent extends Agent {
    @Override
    protected void setup() {
        System.out.println("VerificationAgent started.");

        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                // Simulate QoS verification
                System.out.println("Verifying QoS parameters...");
                String compositeService = "Composite Service: AWS + Google Cloud";

                // Forward composite service to Infrastructure Node
                sendToInfrastructureNode(compositeService);
            }
        });
    }

    private void sendToInfrastructureNode(String compositeService) {
        System.out.println("Forwarding composite service to InfrastructureAgent: " + compositeService);
    }
}
