package ObserverPattern;

import interfaces.Observer;

/**
 * An Observer that observes the simulation for changes.
 */
public class GLObserver implements Observer {
    @Override
    public void update() {
        System.out.println("Simulation changes have been made");
    }
}
