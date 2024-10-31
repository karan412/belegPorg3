package listener.MediaEventListener;

import domainLogic.Admin;
import handler.MediaEvents.UpdateMediaEvent;
import listener.Listener;

public class UpdateMediaEventListener implements Listener<UpdateMediaEvent> {
    private Admin admin;

    public UpdateMediaEventListener(Admin admin) {
        this.admin = admin;
    }

    @Override
    public void onEvent(UpdateMediaEvent event) {
        this.admin.update(event.getAddress());
    }

}
