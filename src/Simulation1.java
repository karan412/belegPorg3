import domainLogic.Admin;

public class Simulation1 {

    private SimulationAdmin admin;

    public static void main(String[] args) {
        long cap = Long.parseLong(args[0]); //10000
        Admin admin = new Admin(cap);

        SimulationAdmin simAdmin = new SimulationAdmin(admin);

        Thread inserter = simAdmin.InserterThread();
        Thread deleter = simAdmin.DeleterThread();

        inserter.start();
        deleter.start();
    }
}
