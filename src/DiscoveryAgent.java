import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class DiscoveryAgent extends Agent {
    @Override
    protected void setup() {
        System.out.println("DiscoveryAgent started.");

        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                // Simulate WSDL file processing
                System.out.println("Processing WSDL files...");
                String selectedServices = "AWS (low-latency streaming), Google Cloud (high-availability storage)";

                // Forward selected services to Verification Node
                sendToVerificationNode(selectedServices);
            }
        });
    }

    private void sendToVerificationNode(String services) {
        System.out.println("Forwarding services to VerificationAgent: " + services);
    }
}
