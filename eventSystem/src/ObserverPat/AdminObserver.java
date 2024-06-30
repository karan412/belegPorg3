package ObserverPat;

import domainLogic.Admin;

import java.io.Serializable;


public class AdminObserver implements Observer, Serializable {

    static final long serialVersionUID = 1L;

    private final Admin admin;

    public AdminObserver(Admin admin) {
        this.admin = admin;
    }

    @Override
    public void update() {
        System.out.println("AdminObserver: ");
    }
}
