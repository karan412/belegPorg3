package handler.uploaderEvents;

import java.util.EventObject;

public class DeleteUploaderEvent extends EventObject {
    private String name;

    public DeleteUploaderEvent(Object source, String address) {
        super(source);
        this.name = name;
    }

    public String getUploaderName() {
        return name;
    }
}
