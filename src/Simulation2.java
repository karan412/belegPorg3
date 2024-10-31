import ObserverPattern.CapacityObserver;
import ObserverPattern.GLObserver;
import domainLogic.Admin;

public class Simulation2 {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Arguments: capacity, number of threads missing");
            return;
        }

        // Parse arguments: capacity and number of threads (n)
        long capacity = Long.parseLong(args[0]); // Capacity of the Admin
        int n = Integer.parseInt(args[1]);  // Number of threads to start


        Admin admin = new Admin(capacity);

        // Register a GLObserver and CapacityObserver to observe the Admin changes
        CapacityObserver observer = new CapacityObserver(admin);
        admin.registerObserver(observer);
        GLObserver glObserver = new GLObserver();
        admin.registerObserver(glObserver);

        SimulationAdmin simAdmin = new SimulationAdmin(admin);

        // Start n inserter and n deleter threads
        for (int i = 0; i < n; i++) {
            Thread inserter = simAdmin.InserterThread();
            Thread deleter = simAdmin.DeleterThread();

            inserter.start();
            deleter.start();
        }
    }
}
