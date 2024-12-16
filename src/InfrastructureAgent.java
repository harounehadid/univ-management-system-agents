import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import java.util.List;

public class InfrastructureAgent extends Agent {
    private List<VM> vmList; // List of VMs
    private int totalTasks; // Total number of tasks to execute
    private int tasksAssigned; // Number of tasks already assigned

    @Override
    protected void setup() {
        System.out.println("InfrastructureAgent started.");
        vmList = new ArrayList<>();
        tasksAssigned = 0;

        // Initialize some VMs with a task limit
        vmList.add(new VM("VM1", 2, 4, "available", 3)); // Handles 3 tasks
        vmList.add(new VM("VM2", 4, 8, "available", 5)); // Handles 5 tasks

        // Example: User specifies total tasks
        totalTasks = 10; // Replace with dynamic input if needed

        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                while (tasksAssigned < totalTasks) {
                    String result = assignTask("Task" + (tasksAssigned + 1));
                    System.out.println(result);
                }
                System.out.println("All tasks have been assigned!");
            }
        });
    }

    private String assignTask(String taskName) {
        for (VM vm : vmList) {
            if (vm.canHandleMoreTasks()) {
                vm.incrementTasks();
                tasksAssigned++;
                return "Assigned " + taskName + " to " + vm.getName() + " (Tasks: " + vm.getCurrentTasks() + "/" + vm.getMaxTasks() + ")";
            }
        }

        // No VM can handle more tasks, create a new VM
        VM newVm = createNewVM();
        vmList.add(newVm);
        newVm.incrementTasks();
        tasksAssigned++;
        return "No available VM. Created " + newVm.getName() + " and assigned " + taskName;
    }

    private VM createNewVM() {
        String vmName = "VM" + (vmList.size() + 1);
        System.out.println("Creating new VM: " + vmName);
        return new VM(vmName, 4, 8, "available", 4); // New VM handles 4 tasks
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
