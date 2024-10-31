package handler.uploaderEvents;

import domainLogic.UploaderImpl;

import java.util.EventObject;

public class InsertUploaderEvent extends EventObject {
    private UploaderImpl uploader;

    public InsertUploaderEvent(Object source, UploaderImpl uploader) {
        super(source);
        this.uploader = uploader;
    }

    public UploaderImpl getUploader() {
        return uploader;
    }
}
