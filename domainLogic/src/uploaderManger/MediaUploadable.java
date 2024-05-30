package uploaderManger;

import contract.MediaContent;
import contract.Uploadable;

public abstract class MediaUploadable implements MediaContent, Uploadable {

    public abstract void setAccessCount(long l);

}
