import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class Main {
    public static void main(String[] args) {
        Runtime runtime = Runtime.instance();
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        AgentContainer mainContainer = runtime.createMainContainer(profile);

        try {
            // Launch agents
            AgentController receptionAgent = mainContainer.createNewAgent("ReceptionAgent", ReceptionAgent.class.getName(), null);
            AgentController discoveryAgent = mainContainer.createNewAgent("DiscoveryAgent", DiscoveryAgent.class.getName(), null);
            AgentController verificationAgent = mainContainer.createNewAgent("VerificationAgent", VerificationAgent.class.getName(), null);
            AgentController infrastructureAgent = mainContainer.createNewAgent("InfrastructureAgent", InfrastructureAgent.class.getName(), null);

            receptionAgent.start();
            discoveryAgent.start();
            verificationAgent.start();
            infrastructureAgent.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
}
