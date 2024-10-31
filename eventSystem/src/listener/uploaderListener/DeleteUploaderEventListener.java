package listener.uploaderListener;

import domainLogic.Admin;
import handler.uploaderEvents.DeleteUploaderEvent;
import listener.Listener;

public class DeleteUploaderEventListener implements Listener<DeleteUploaderEvent> {
    private Admin admin;

    public DeleteUploaderEventListener(Admin admin) {
        this.admin = admin;
    }

    @Override
    public void onEvent(DeleteUploaderEvent event) {
        if (admin.deleteUploader(event.getUploaderName())) {
            System.out.println("Uploader deleted successfully");
        } else {
            System.out.println("Uploader not found");

        }
    }

}
