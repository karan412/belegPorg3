package handler.MediaEvents;

import java.util.EventObject;

public class LoadEvent extends EventObject {
    private final String type;

    public LoadEvent(Object source, String type) {
        super(source);
        this.type = type;
    }

    public String getFileType() {
        return type;
    }
}
