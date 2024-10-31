package listener.MediaEventListener;

import exception.DomainLogicException;
import fileSystem.FileSystem;
import handler.MediaEvents.LoadEvent;
import listener.Listener;

public class LoadEventListener implements Listener<LoadEvent> {
    private FileSystem fileSys;

    public LoadEventListener(FileSystem file) {
        this.fileSys = file;
    }

    @Override
    public void onEvent(LoadEvent event) {
        this.fileSys.loadState(event.getFileType());
    }

}
