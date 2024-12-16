import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class VerificationAgent extends Agent {
    @Override
    protected void setup() {
        System.out.println("VerificationAgent started.");

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage message = receive();
                if (message != null) {
                    System.out.println("\n\n");

                    System.out.println("VerificationAgent received services: " + message.getContent());

                    String content = message.getContent();
                    String[] parts = content.split(",");
                    int totalTasks = Integer.parseInt(parts[0]);
                    int numVMs = Integer.parseInt(parts[1]);
                    int maxTasksPerVM = Integer.parseInt(parts[2]);

                    // Parse QoS data from XML file
                    String bestService = evaluateQoS("qos.xml");
                    System.out.println("Best service selected: " + bestService);

                    // Send the best service to InfrastructureAgent
                    ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
                    reply.addReceiver(getAID("InfrastructureAgent"));
                    reply.setContent(totalTasks + "," + numVMs + "," + maxTasksPerVM + "," + bestService);
                    send(reply);
                    System.out.println("Sent best service to InfrastructureAgent.");
                } else {
                    block();
                }
            }
        });
    }

    private String evaluateQoS(String filePath) {
        try {
            File file = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            NodeList serviceList = doc.getElementsByTagName("service");
            double bestScore = Double.MAX_VALUE;
            String bestService = "";

            for (int i = 0; i < serviceList.getLength(); i++) {
                Node node = serviceList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String name = element.getElementsByTagName("name").item(0).getTextContent();
                    double latency = Double.parseDouble(element.getElementsByTagName("latency").item(0).getTextContent());
                    double availability = Double.parseDouble(element.getElementsByTagName("availability").item(0).getTextContent());
                    double cost = Double.parseDouble(element.getElementsByTagName("cost").item(0).getTextContent());

                    // Calculate a QoS score (lower is better)
                    double score = latency * 0.5 + (100 - availability) * 0.3 + cost * 0.2;
                    if (score < bestScore) {
                        bestScore = score;
                        bestService = name;
                    }
                }
            }

            return bestService;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error evaluating QoS";
        }
    }
}

