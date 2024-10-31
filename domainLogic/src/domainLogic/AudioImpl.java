package domainLogic;

import contract.Audio;
import contract.Tag;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

/**
 * AudioImpl class
 */
public class AudioImpl extends MediaContentImpl implements Audio, Serializable {

    private final int samplingRate;

    public AudioImpl(String mediaContentType, String uploaderName, String address, Collection<Tag> tags, Long size, BigDecimal cost, Duration availabilty, int samplingRate) {
        super(mediaContentType, uploaderName, address, tags, size, cost, availabilty);
        this.samplingRate = samplingRate;
    }

    @Override
    public int getSamplingRate() {
        return samplingRate;
    }

    @Override
    public String toString() {
        return super.toString() + ", sampling rate= " + this.samplingRate;
    }

}
