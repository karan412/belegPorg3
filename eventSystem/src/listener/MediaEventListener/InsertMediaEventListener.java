package listener.MediaEventListener;

import domainLogic.Admin;
import handler.MediaEvents.InsertMediaEvent;
import listener.Listener;

public class InsertMediaEventListener implements Listener<InsertMediaEvent> {
    private Admin admin;

    public InsertMediaEventListener(Admin admin) {
        this.admin = admin;
    }

    @Override
    public void onEvent(InsertMediaEvent event) {
        if (event.getMedia() != null) {
           String res = this.admin.insert(event.getMedia());
           System.out.println(res);
        }
    }

}
