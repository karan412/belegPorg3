import ObserverPattern.CapacityObserver;
import ObserverPattern.TagsObserver;
import domainLogic.Admin;
import handler.MediaEvents.*;
import handler.uploaderEvents.DeleteUploaderEvent;
import handler.uploaderEvents.InsertUploaderEvent;
import handler.uploaderEvents.ListUploaderMediaCountEvent;
import interfaces.Observer;
import listener.MediaEventListener.*;
import listener.uploaderListener.DeleteUploaderEventListener;
import listener.uploaderListener.InsertUploaderEventListener;
import listener.uploaderListener.ListUploaderMediaCountEventListener;
import util.Command;
import viewControl.Console;


/**
 * CLI Klasse für die Steuerung der Anwendung
 */

public class CLI {

    /**
     * Main Methode
     *
     * @param args String[]
     */
    public static void main(String[] args) {
        long capacity = Long.parseLong(args[0]);
        Admin ad = new Admin(capacity);
        Command cmd = new Command(ad, capacity);
        Console view = new Console(cmd);

        //Observers
        Observer capacityObserver = new CapacityObserver(ad);
        Observer tagsObserver = new TagsObserver(ad);
        ad.registerObserver(capacityObserver);
        ad.registerObserver(tagsObserver);


        // Event Handler
        EventHandler<InsertUploaderEvent> insertUploaderHandler = new EventHandler<>();
        EventHandler<DeleteUploaderEvent> deleteUploaderHandler = new EventHandler<>();
        EventHandler<ListUploaderMediaCountEvent> listUploaderMediaCountHandler = new EventHandler<>();
        EventHandler<InsertMediaEvent> insertMediaHandler = new EventHandler<>();
        EventHandler<DeleteMediaEvent> deleteMediaHandler = new EventHandler<>();
        EventHandler<UpdateMediaEvent> updateMediaHandler = new EventHandler<>();
        EventHandler<FilterMediaEvent> filterMediaHandler = new EventHandler<>();
        EventHandler<TagMediaEvent> tagMediaHandler = new EventHandler<>();

        // Event Listener
        InsertUploaderEventListener insertUploaderListener = new InsertUploaderEventListener(ad);
        DeleteUploaderEventListener deleteUploaderListener = new DeleteUploaderEventListener(ad);
        ListUploaderMediaCountEventListener listUploaderMediaCountListener = new ListUploaderMediaCountEventListener(ad);
        InsertMediaEventListener insertMediaListener = new InsertMediaEventListener(ad);
        DeleteMediaListener deleteMediaListener = new DeleteMediaListener(ad);
        UpdateMediaEventListener updateMediaListener = new UpdateMediaEventListener(ad);
        FilterMediaEventListener filterMediaListener = new FilterMediaEventListener(ad);
        TagMediaEventListener tagMediaListener = new TagMediaEventListener(ad);

        // Add Listener to Handler
        insertUploaderHandler.add(insertUploaderListener);
        deleteUploaderHandler.add(deleteUploaderListener);
        listUploaderMediaCountHandler.add(listUploaderMediaCountListener);
        insertMediaHandler.add(insertMediaListener);
        deleteMediaHandler.add(deleteMediaListener);
        updateMediaHandler.add(updateMediaListener);
        filterMediaHandler.add(filterMediaListener);
        tagMediaHandler.add(tagMediaListener);

        // Set Handler to Command
        cmd.setUploaderDeleteHandler(deleteUploaderHandler);
        cmd.setGetAllProducerMediaCountHandler(listUploaderMediaCountHandler);
        cmd.setMediaInsertHandler(insertMediaHandler);
        cmd.setMediaDeleteHandler(deleteMediaHandler);
        cmd.setMediaUpdateHandler(updateMediaHandler);
        cmd.setFilterMediaHandler(filterMediaHandler);
        cmd.setUploaderInsertHandler(insertUploaderHandler);
        cmd.setTagMediaHandler(tagMediaHandler);

        // Start View
        view.execute();
    }
}
