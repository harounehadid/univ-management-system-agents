import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class InfrastructureAgent extends Agent {
    @Override
    protected void setup() {
        System.out.println("InfrastructureAgent started.");

        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                // Simulate resource allocation
                System.out.println("Allocating resources for the composite service...");
                System.out.println("Simulation complete: Resources allocated successfully.");
            }
        });
    }
}
