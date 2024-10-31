package domainLogic;

import contract.Tag;
import contract.Video;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

/**
 * VideoImpl class
 */
public class VideoImpl extends MediaContentImpl implements Video, Serializable {


    private final int resolution;

    public VideoImpl(String mediaContentType, String address, String uploaderName, Collection<Tag> tags, Long size, BigDecimal cost, Duration availability, int resolution) {
        super(mediaContentType, uploaderName, address, tags, size, cost, availability);
        this.resolution = resolution;
    }

    @Override
    public int getResolution() {
        return resolution;
    }

    @Override
    public String toString() {
        return super.toString() + ", resolution= " + this.resolution;
    }
}
