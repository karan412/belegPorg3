package ObserverPattern;

import domainLogic.Admin;
import interfaces.Observer;

import java.io.Serializable;

/**
 * An Observer that observes the Admin for changes in the capacity.
 */

public class CapacityObserver implements Serializable, Observer {

    private final Admin admin;

    public CapacityObserver(Admin admin) {
        this.admin = admin;
    }

    @Override
    public void update() {
        double currentCapacity = admin.getCAPACITY();
        double maxCapacity = admin.getMAX_CAPACITY();
        if ((currentCapacity / maxCapacity) > 0.9) {
            System.out.println("List Almost Full, Capacity exceeds 90%");
        }
    }
}
