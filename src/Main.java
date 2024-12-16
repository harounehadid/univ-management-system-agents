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

// import jade.core.behaviours.CyclicBehaviour;
// import jade.core.behaviours.OneShotBehaviour;

// import java.util.ArrayList;

// import jade.core.Agent;
// import jade.wrapper.AgentContainer;
// import jade.wrapper.AgentController;
// import jade.wrapper.StaleProxyException;
// import jade.lang.acl.ACLMessage;

// import packages.webscrabber.WebscrabberAgent;

// public class Main extends Agent {
//     private int expectedMessages = 3; // Number of WebscrabberAgent agents
//     private ArrayList<String> messages = new ArrayList<>();

//     @Override
//     protected void setup() {
//         // Add a behavior to receive messages from other agents
//         addBehaviour(new CyclicBehaviour() {
//             @Override
//             public void action() {
//                 ACLMessage msg = receive();

//                 if (msg != null) {
//                     messages.add(msg.getContent());
                    
//                     if (messages.size() == expectedMessages) {
//                         addBehaviour(new OneShotBehaviour() {
//                             @Override
//                             public void action() {
//                                 // ANSI escape code for green text to highlight the output
//                                 String greenBoldText = "\u001B[1;32m";
//                                 String boldText = "\u001B[1m";
//                                 String resetText = "\u001B[0m";
                                
//                                 // Create an Arraylist of extracted information from the messages
//                                 ArrayList<String[]> info = new ArrayList<>();
//                                 int highestRatingProductIndex = 0;
//                                 int curIndex = 0;

//                                 System.out.println("\n\n" + boldText + "HIGHEST RATED PRODUCT: ##############################################################\n" + resetText);
//                                 for (String message : messages) {
//                                     String[] parts = message.split(" \\| ");
//                                     info.add(parts);
//                                     System.out.println(parts[0] + ": " + parts[1]);

//                                     if (Double.parseDouble(parts[2]) > Double.parseDouble(info.get(highestRatingProductIndex)[2])) {
//                                         highestRatingProductIndex = curIndex;
//                                     }

//                                     curIndex++;
//                                 }

//                                 // Print out the highest rated product from all websites
//                                 System.out.print(greenBoldText);

//                                 System.out.println("\n\n" + "The winner product is " + info.get(highestRatingProductIndex)[1] + " from " + info.get(highestRatingProductIndex)[0] + resetText);

//                                 System.out.print(resetText + "\n\n");
//                             }
//                         });
//                     }
//                 }
//                 else {
//                     block();
//                 }
//             }
//         });

//         // Start the other agents
//         startAgents();
//     }

//     private void startAgents() {
//         // Get the container where MainAgent is running
//         AgentContainer ac = getContainerController();

//         try {
//             // Create and start the WebscrabberAgent for Amazon reviews
//             String amazonFilePath = "src/pages/amazon/Amazon.com _ keyboard.html";
//             String amazonReviewRegex = "<span.*>(.*)</span>.*<i data-cy=\"reviews-ratings-slot\".*.<span .*>(.*)</span></i>";
//             Object[] amazonArgs = {amazonFilePath, amazonReviewRegex, false, "Amazon", 500};
//             AgentController amazonAgent = ac.createNewAgent("AmazonWebscrabberAgent", WebscrabberAgent.class.getName(), amazonArgs);
//             amazonAgent.start();

//             // Create and start the WebscrabberAgent for Newegg reviews
//             String neweggFilePath = "src/pages/newegg/keyboard _ Newegg.com.html";
//             String neweggReviewRegex = "<div class=\\\"item-info\\\">.*?<a.*? class=\\\"item-rating\\\" .*>.*?<i class=\\\"rating .*?\\\" aria-label=\\\"(.*?)>.*?</i>.*?<a.*? class=\\\"item-title\\\".*?><span.*?>.*?</span>(.*?)</a>";
//             Object[] neweggArgs = {neweggFilePath, neweggReviewRegex, true, "Newegg", 5000};
//             AgentController neweggAgent = ac.createNewAgent("NeweggWebscrabberAgent", WebscrabberAgent.class.getName(), neweggArgs);
//             neweggAgent.start();

//             // Create and start the WebscrabberAgent for BestBuy reviews
//             String bestbuyFilePath = "src/pages/bestbuy/keyboard - Best Buy.html";
//             String bestbuyReviewRegex = "<h4 class=\\\"sku-title\\\">.*?<a.*?>(.*?)</a>.*?</h4>.*?<div class=\\\"ratings-reviews\\\">.*?<p class=\\\"visually-hidden\\\">(.*?)</p>";
//             Object[] bestbuyArgs = {bestbuyFilePath, bestbuyReviewRegex, false, "Bestbuy", 1000};
//             AgentController bestbuyAgent = ac.createNewAgent("BestBuyWebscrabberAgent", WebscrabberAgent.class.getName(), bestbuyArgs);
//             bestbuyAgent.start();

//         } catch (StaleProxyException e) {
//             e.printStackTrace();
//         }
//     }

//     public static void main(String[] args) {
//         // Start the main agent
//         String[] jadeArgs = {"-gui", "MainAgent:Main"};
//         jade.Boot.main(jadeArgs);
//     }
// }