package domainLogic;


import contract.Uploader;

public class UploaderImpl implements Uploader {

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
