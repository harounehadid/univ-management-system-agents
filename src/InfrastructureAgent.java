import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import java.util.List;

public class InfrastructureAgent extends Agent {
    private List<VM> vmList; // List of VMs
    private int totalTasks; // Total number of tasks to execute
    private int numVMs;
    private int maxTasksPerVM;
    private int tasksAssigned; // Number of tasks already assigned
    private String service; // Service to host VMs

    @Override
    protected void setup() {
        System.out.println("InfrastructureAgent started.");

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage message = receive();

                if (message != null) {
                    System.out.println("\n\n");

                    System.out.println("InfrastructureAgent received input: " + message.getContent() + "\n");

                    String content = message.getContent();
                    String[] parts = content.split(",");
                    totalTasks = Integer.parseInt(parts[0]);
                    numVMs = Integer.parseInt(parts[1]);
                    maxTasksPerVM = Integer.parseInt(parts[2]);
                    service = parts[3];

                    // Initialize the VM list
                    vmList = new ArrayList<>();

                    for (int i = 0; i < numVMs; i++) {
                        vmList.add(new VM("VM" + (i + 1), 4, 8, "available", maxTasksPerVM));
                    }
                    
                    // Asign tasks to VMs
                    tasksAssigned = 0;

                    while (tasksAssigned < totalTasks) {
                        String result = assignTask("Task" + (tasksAssigned + 1), maxTasksPerVM);
                        System.out.println(result);
                    }

                    System.out.println("\nAll tasks have been assigned and hosted on " + service + "!");
                    
                    System.out.println("\n");
                } else {
                    block();
                }
            }
        });
    }

    private String assignTask(String taskName, int maxTasksPerVM) {
        for (VM vm : vmList) {
            if (vm.canHandleMoreTasks()) {
                vm.incrementTasks();
                tasksAssigned++;
                return "Assigned " + taskName + " to " + vm.getName() + " (Tasks: " + vm.getCurrentTasks() + "/" + vm.getMaxTasks() + ")";
            }
        }

        // No VM can handle more tasks, create a new VM
        VM newVm = createNewVM(maxTasksPerVM);
        vmList.add(newVm);
        newVm.incrementTasks();
        tasksAssigned++;
        return "No available VM. Created " + newVm.getName() + " and assigned " + taskName;
    }

    private VM createNewVM(int maxTasksPerVM) {
        String vmName = "VM" + (vmList.size() + 1);
        System.out.println("Creating new VM: " + vmName);
        return new VM(vmName, 4, 8, "available", maxTasksPerVM); // New VM handles 4 tasks
    }
}

class VM {
    private String name;
    private int cpu; // Number of CPUs
    private int ram; // Amount of RAM in GB
    private String status; // available, occupied, failed
    private int maxTasks; // Maximum tasks this VM can handle
    private int currentTasks; // Current number of tasks assigned

    public VM(String name, int cpu, int ram, String status, int maxTasks) {
        this.name = name;
        this.cpu = cpu;
        this.ram = ram;
        this.status = status;
        this.maxTasks = maxTasks;
        this.currentTasks = 0;
    }

    public String getName() {
        return name;
    }

    public int getCpu() {
        return cpu;
    }

    public int getRam() {
        return ram;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMaxTasks() {
        return maxTasks;
    }

    public int getCurrentTasks() {
        return currentTasks;
    }

    public void incrementTasks() {
        if (currentTasks < maxTasks) {
            currentTasks++;
            if (currentTasks == maxTasks) {
                setStatus("occupied");
            }
        }
    }

    public boolean canHandleMoreTasks() {
        return currentTasks < maxTasks;
    }
}
