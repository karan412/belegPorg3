package listener.MediaEventListener;

import domainLogic.Admin;
import handler.MediaEvents.DeleteMediaEvent;
import listener.Listener;

public class DeleteMediaListener implements Listener<DeleteMediaEvent> {
    private Admin admin;

    public DeleteMediaListener(Admin admin) {
        this.admin = admin;
    }

    @Override
    public void onEvent(DeleteMediaEvent event) {
        if (admin.delete(event.getAddress())) {
            System.out.println("Media deleted successfully");
        } else {
            System.out.println("Media not found");
        }
    }

}
