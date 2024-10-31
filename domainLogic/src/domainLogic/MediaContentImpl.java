package domainLogic;

import contract.MediaContent;
import contract.Tag;
import contract.Uploadable;
import contract.Uploader;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

/**
 * MediaContentImpl class implements MediaContent and Uploadable, represents a MediaContent object
 */
public class MediaContentImpl implements MediaContent, Uploadable {

    private String address;
    private Collection<Tag> tags;
    private long size;
    private Uploader uploader;
    private BigDecimal cost;
    private Duration availability;
    private String mediaContentType;
    private long accessCount;
    private Date dateInserted;

    public MediaContentImpl(String mediaContentType, String uploaderName, String address, Collection<Tag> tags, Long sizeMediaContent, BigDecimal cost, Duration availability) {
        this.mediaContentType = mediaContentType;
        this.address = address;
        this.tags = tags;
        this.size = sizeMediaContent;
        this.uploader = new UploaderImpl(uploaderName);
        this.cost = cost;
        this.availability = availability;
        this.accessCount = 0;
        this.dateInserted = new Date();
    }


    @Override
    public String toString() {
        return "\n" +
                "mediaContentType=" + mediaContentType +
                ", address=" + address + ", tags=" + tags +
                ", sizeMediaContent=" + size +
                ", uploader=" + uploader.getName() +
                ", cost=" + cost +
                ", accessCount=" + accessCount +
                ", insertionDate=" + dateInserted +
                ", availability=" + getAvailability();
    }

    @Override
    public String getAddress() {
        return this.address;
    }

    @Override
    public Collection<Tag> getTags() {
        return this.tags;
    }

    @Override
    public long getAccessCount() {
        return this.accessCount;
    }

    void setAccessCount(long l) {
        this.accessCount = l;
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
        long durationInSeconds = (new Date().getTime() - dateInserted.getTime()) / 1000;
        return Duration.ofSeconds(durationInSeconds);
    }

    @Override
    public BigDecimal getCost() {
        return this.cost;
    }

    public String getMediaContentType() {
        return mediaContentType;
    }

}
