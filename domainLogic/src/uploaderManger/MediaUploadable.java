package uploaderManger;

import contract.MediaContent;
import contract.Uploadable;

import java.io.Serializable;

public abstract class MediaUploadable implements MediaContent, Uploadable, Serializable {

    public abstract void setAccessCount(long l);

}
