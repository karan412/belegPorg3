package listener.uploaderListener;

import domainLogic.Admin;
import handler.uploaderEvents.ListUploaderMediaCountEvent;
import listener.Listener;

public class ListUploaderMediaCountEventListener implements Listener<ListUploaderMediaCountEvent> {

    private final Admin admin;

    public ListUploaderMediaCountEventListener(Admin admin) {
        this.admin = admin;
    }

    @Override
    public void onEvent(ListUploaderMediaCountEvent event) {
        admin.getProducersMediaCount();
    }

}
