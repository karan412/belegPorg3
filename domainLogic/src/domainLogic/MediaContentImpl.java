package domainLogic;

import contract.MediaContent;
import contract.Tag;

import java.util.Collection;

public class MediaContentImpl implements MediaContent{

    static final long serialVersionUID = 1L;
    private String address;
    private Collection<Tag> tag;
    private long accessCount;
    private long size;
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

    /**
     * Konstruktor
     * @param address Adresse des MediaContents
     * @param tag Tags des MediaContents
     * @param accessCount Zugriffszahl des MediaContents
     * @param size Groesse des MediaContents
     */
    public MediaContentImpl(String address, Collection<Tag> tag, long accessCount, long size) {
        this.address = address;
        this.tag = tag;
        this.accessCount = accessCount;
        this.size = size;
    }
}
