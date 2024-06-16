package domainLogic;


import contract.Uploader;

import java.io.Serializable;

public class UploaderImpl implements Uploader, Serializable {

    static final long serialVersionUID=1L;
    private String name;
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Konstruktor
     * @param name Name des Uploaders
     */
    public UploaderImpl(String name) {
        this.name = name;
    }
}
