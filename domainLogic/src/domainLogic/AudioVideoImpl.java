package domainLogic;

import contract.AudioVideo;
import contract.Tag;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

/**
 * AudioVideoImpl class
 */
public class AudioVideoImpl extends MediaContentImpl implements AudioVideo, Serializable {

    private final int samplingRate;
    private final int resolution;

    public AudioVideoImpl(String mediaContentType, String uploaderName, String address, Collection<Tag> tags, Long sizeMediaContent, BigDecimal cost, Duration availability, int samplingRate, int resolution) {
        super(mediaContentType, uploaderName, address, tags, sizeMediaContent, cost, availability);
        this.samplingRate = samplingRate;
        this.resolution = resolution;
    }

    @Override
    public int getSamplingRate() {
        return samplingRate;
    }

    @Override
    public int getResolution() {
        return resolution;
    }

    @Override
    public String toString() {
        return super.toString() + ", sampling rate= " + this.samplingRate + ", resolution= " + this.resolution;
    }
}
