package domainLogic;

import contract.Audio;
import contract.Tag;
import contract.Uploader;
import uploaderManger.MediaUploadable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

public class AudioImpl extends MediaUploadable implements Audio, Serializable {

    static final long serialVersionUID = 1L;
    /**
     * Accesscount of the Audio
     */
    private long accessCount = 0;

    /**
     * Samplingrate of the Audio
     */
    private int samplingRate;

    /**
     * Address of the Audio
     */
    private String address;

    /**
     * Tags of the Audio
     */
    private Collection<Tag> tag;

    /**
     * Size of the Audio
     */
    private long size;

    /**
     * Uploader of the Audio
     */
    private Uploader uploader;

    /**
     * Availability of the Audio
     */
    private Duration availability;

    /**
     * Cost of the Audio
     */
    private BigDecimal cost;

    /**
     * Default Constructor
     */
    public AudioImpl() {
    }


    /**
     * Constructor for AudioImpl
     *
     * @param samplingRate
     * @param address
     * @param tag
     * @param size
     * @param availability
     * @param cost
     * @param uploader
     * @param accessCount
     */
    //[Media-Typ] [P-Name] [kommaseparierte Tags, einzelnes Komma für keine] [Größe] [Abrufkosten] [[Optionale Parameter]]
    public AudioImpl(Uploader uploader, Collection<Tag> tag, String address, long size, BigDecimal cost, Duration availability, int samplingRate, long accessCount) {
        this.uploader = uploader;
        this.tag = tag;
        this.address = address;
        this.samplingRate = samplingRate;
        this.size = size;
        this.availability = availability;
        this.cost = cost;
        this.accessCount = accessCount;
    }

    @Override
    public int getSamplingRate() {
        return this.samplingRate;
    }

    @Override
    public String getAddress() {
        return this.address;
    }

    @Override
    public Collection<Tag> getTags() {
        return this.tag;
    }

    @Override
    public long getAccessCount() {
        return this.accessCount;
    }

    @Override
    public void setAccessCount(long accessCount) {
        this.accessCount = accessCount;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public Uploader getUploader() {
        return this.uploader;
    }

    @Override
    public Duration getAvailability() {
        return this.availability;
    }

    @Override
    public BigDecimal getCost() {
        return this.cost;
    }
}
