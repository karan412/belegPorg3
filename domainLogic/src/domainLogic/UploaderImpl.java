package domainLogic;


import contract.Uploader;

import java.io.Serializable;

/**
 * UploaderImpl class
 */
public class UploaderImpl implements Uploader, Serializable {

    static final long serialVersionUID = 1L;
    private String uploaderName;

    /**
     * Konstruktor
     *
     * @param uploaderName Name des Uploaders
     */
    public UploaderImpl(String uploaderName) {
        this.uploaderName = uploaderName;
    }

    @Override
    public String getName() {
        return this.uploaderName;
    }

    @Override
    public String toString() {
        return "{" +
                "uploaderName='" + uploaderName + '\'' +
                '}';
    }
}
