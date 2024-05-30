package domainLogic;

import contract.Tag;
import contract.Uploader;
import contract.Video;
import uploaderManger.MediaUploadable;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

public class VideoImpl extends MediaUploadable implements Video {
    private String address;
    private Collection<Tag> tag;
    private long size;
    private Uploader uploader;
    private Duration availability;
    private BigDecimal cost;
    private int resolution;
    private long accessCount;
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
     * Konstruktor fuer VideoImpl
     * @param address Adresse des Videos
     * @param tag Tags des Videos
     * @param size Groesse des Videos
     * @param uploader Uploader des Videos
     * @param availability Verfuegbarkeit des Videos
     * @param cost Kosten des Videos
     * @param resolution Aufloesung des Videos
     * @param accessCount Zugriffszahl des Videos
     */
    public VideoImpl(Uploader uploader, Collection<Tag> tag, String address, long size, BigDecimal cost, Duration availability, int resolution, long accessCount) {
        this.address = address;
        this.tag = tag;
        this.size = size;
        this.uploader = uploader;
        this.availability = availability;
        this.cost = cost;
        this.resolution = resolution;
        this.accessCount = accessCount;
    }

    @Override
    public void setAccessCount(long accessCount) {
        this.accessCount = accessCount;
    }
}
