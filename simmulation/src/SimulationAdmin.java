import contract.Tag;
import contract.Uploader;
import domainLogic.Admin;
import domainLogic.AudioImpl;
import domainLogic.MediaContentImpl;
import domainLogic.UploaderImpl;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

/**
 * SimulationAdmin class
 */
public class SimulationAdmin extends Thread {

    /**
     * Admin instance
     */
    private final Admin admin;

    /**
     * Constructor for SimulationAdmin
     * @param admin Admin instance
     */
    public SimulationAdmin(Admin admin) {
        this.admin = admin;
    }

    /**
     * InserterThread method, inserts randomly generated media in the admin
     */
    public Thread InserterThread() {
        return new Thread(() -> {
            Random random = new Random();
            UploaderImpl uploader = new UploaderImpl("Carmy");
            Collection<Tag> tags = Arrays.asList(Tag.Music, Tag.News, Tag.Review);
            admin.insertUploader(uploader);

            while (true) {
                synchronized (this) {
                        String address = admin.generateAddress();
                        long size = 1L + random.nextInt(10);
                        BigDecimal cost = BigDecimal.valueOf(random.nextDouble() * 100);
                        Duration availability = Duration.ofDays(10);
                        int samplingRate = 44100;

                        MediaContentImpl media = new AudioImpl("Audio", uploader.getName(), address, tags, size, cost, availability,samplingRate);
                        String result = admin.insert(media);
                        System.out.println("Insert result: " + result + " - " + media.getAddress());
                }
            }
        });
    }


    /**
     * DeleterThread method, deletes randomly generated media in the admin
     */
    public Thread DeleterThread() {
        return new Thread(() -> {
            Random random = new Random();
            while (true) {
                synchronized (this) {
                    if (!admin.list().isEmpty()) {
                        int index = random.nextInt(admin.list().size());
                        MediaContentImpl media = admin.list().get(index);
                        boolean result = admin.delete(media.getAddress());
                        System.out.println("Delete result: " + result + " - " + media.getAddress());
                    }
                }
            }
        });
    }
}

