package listener.MediaEventListener;

import domainLogic.Admin;
import domainLogic.MediaContentImpl;
import handler.MediaEvents.FilterMediaEvent;
import listener.Listener;

import java.util.List;


public class FilterMediaEventListener implements Listener<FilterMediaEvent> {

    private Admin admin;

    private List<MediaContentImpl> list;

    public FilterMediaEventListener(Admin admin) {
        this.admin = admin;
    }

    @Override
    public void onEvent(FilterMediaEvent event) {
        if (event.getFilterType().equals("content")) {
            list = admin.list();
        } else {
             list = this.admin.filterMedia(event.getFilterType());
        }
        int index = 1;
        for (MediaContentImpl media : list) {
            System.out.println(index + ". Content: " + media.getMediaContentType() +
                    "\n  Tags:  " + media.getTags() +
                    "\n  Uploader: " + media.getUploader().getName() +
                    "\n  Address: " + media.getAddress() +
                    "\n  AccessCount: " + media.getAccessCount() +
                    "\n  Size: " + media.getSize() +
                    "\n  Availability: " + media.getAvailability() +
                    "\n  Cost: " + media.getCost());
            index++;
        }
        if (list.isEmpty()) {
            System.out.println("No content found");
        }
    }

}
