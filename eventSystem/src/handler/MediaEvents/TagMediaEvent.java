package handler.MediaEvents;

import java.util.EventObject;

public class TagMediaEvent extends EventObject {

    private String mode;

    public TagMediaEvent(Object source, String mode) {
        super(source);
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

}
