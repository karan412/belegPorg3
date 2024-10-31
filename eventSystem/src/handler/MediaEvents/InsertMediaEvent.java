package handler.MediaEvents;

import domainLogic.MediaContentImpl;

import java.util.EventObject;

public class InsertMediaEvent extends EventObject {
    private MediaContentImpl media;

    public InsertMediaEvent(Object source,MediaContentImpl media) {
        super(source);
        this.media = media;
    }

    public MediaContentImpl getMedia() {
        return media;
    }
}
