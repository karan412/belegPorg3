package domainLogic;

import contract.Audio;
import contract.Tag;
import contract.Uploader;
import uploaderManger.MediaUploadable;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

public class AudioImpl extends MediaUploadable implements Audio {

    /**
     * Anzahl der Zugriffe auf das Audio
     */
    private long accessCount = 0;

    /**
     * Samplingrate des Audios
     */
    private int samplingRate;

    /**
     * Adresse des Audios
     */
    private String address;

    /**
     * Tags des Audios
     */
    private Collection<Tag> tag;

    /**
     * Groesse des Audios
     */
    private long size;

    /**
     * Uploader des Audios
     */
    private Uploader uploader;

    /**
     * Verfuegbarkeit des Audios
     */
    private Duration availability;

    /**
     * Kosten des Audios
     */
    private BigDecimal cost;

    /**
     * Default Konstruktor fuer AudioImpl
     */
    public AudioImpl() {
    }

    /**
     * Konstruktor fuer AudioImpl nur mit 2 parameters
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

    /**
     * Setter fuer das accesCount des Audios
     *
     * @param accessCount
     */
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
