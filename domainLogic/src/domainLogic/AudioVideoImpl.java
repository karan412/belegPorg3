package domainLogic;

import contract.AudioVideo;
import contract.Tag;
import contract.Uploader;
import uploaderManger.MediaUploadable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

public class AudioVideoImpl extends MediaUploadable implements AudioVideo, Serializable {

    static final long serialVersionUID=1L;
    private String address;
    private Collection<Tag> tag;
    private long accessCount;
    private long size;
    private Uploader uploader;
    private Duration availability;
    private BigDecimal cost;
    private int resolution;
    private int samplingRate;

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

    @Override
    public int getResolution() {
        return this.resolution;
    }

    /**
     * Konstruktor
     * @param address Adresse
     * @param tag Tags
     * @param accessCount Zugriffszahl
     * @param size Groesse
     * @param uploader Uploader
     * @param availability Verfuegbarkeit
     * @param cost Kosten
     * @param resolution Aufloesung
     * @param samplingRate Abtastrate
     */
    public AudioVideoImpl(Uploader uploader, Collection<Tag> tag, String address, long size, BigDecimal cost, Duration availability, int resolution, int samplingRate, long accessCount) {
        this.address = address;
        this.tag = tag;
        this.accessCount = accessCount;
        this.size = size;
        this.uploader = uploader;
        this.availability = availability;
        this.cost = cost;
        this.resolution = resolution;
        this.samplingRate = samplingRate;
    }

    @Override
    public void setAccessCount(long accessCount) {
        this.accessCount = accessCount;
    }
}
