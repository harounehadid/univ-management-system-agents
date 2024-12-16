import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfaceAgent extends Agent {
    private JTextField taskCountField;
    private JTextField vmCountField;
    private JTextField vmTaskLimitField;

    @Override
    protected void setup() {
        System.out.println("InterfaceAgent started.");

        // Create the GUI interface for user input
        JFrame frame = new JFrame("Task and VM Configuration");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        // Total tasks input
        frame.add(new JLabel("Total Number of Tasks:"));
        taskCountField = new JTextField(10);
        frame.add(taskCountField);

        // VM count input
        frame.add(new JLabel("Number of VMs:"));
        vmCountField = new JTextField(10);
        frame.add(vmCountField);

        // VM task limit input
        frame.add(new JLabel("Max Tasks per VM:"));
        vmTaskLimitField = new JTextField(10);
        frame.add(vmTaskLimitField);

        // Submit button
        JButton submitButton = new JButton("Submit");
        frame.add(submitButton);

        // Action listener for submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get user inputs
                int totalTasks = Integer.parseInt(taskCountField.getText());
                int numVMs = Integer.parseInt(vmCountField.getText());
                int maxTasksPerVM = Integer.parseInt(vmTaskLimitField.getText());

                // Send these inputs to the ReceptionAgent
                System.out.println("\n\n");

                ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                message.addReceiver(getAID("ReceptionAgent"));
                message.setContent(totalTasks + "," + numVMs + "," + maxTasksPerVM);
                send(message);
                System.out.println("Sent input to ReceptionAgent: " + totalTasks + " tasks, " + numVMs + " VMs, " + maxTasksPerVM + " tasks per VM.");

                frame.setVisible(false); // Close the window after submission
            }
        });

        frame.setVisible(true);
    }
}
