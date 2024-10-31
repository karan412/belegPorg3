package listener.uploaderListener;

import domainLogic.Admin;
import handler.uploaderEvents.InsertUploaderEvent;
import listener.Listener;

public class InsertUploaderEventListener implements Listener<InsertUploaderEvent> {
    private final Admin admin;

    public InsertUploaderEventListener(Admin admin) {
        this.admin = admin;
    }

    @Override
    public void onEvent(InsertUploaderEvent event) {
        this.admin.insertUploader(event.getUploader());
    }

}
