import ObserverPattern.CapacityObserver;
import domainLogic.Admin;
import handler.MediaEvents.*;
import handler.uploaderEvents.InsertUploaderEvent;
import handler.uploaderEvents.ListUploaderMediaCountEvent;
import interfaces.Observer;
import listener.MediaEventListener.DeleteMediaListener;
import listener.MediaEventListener.FilterMediaEventListener;
import listener.MediaEventListener.InsertMediaEventListener;
import listener.MediaEventListener.UpdateMediaEventListener;
import listener.uploaderListener.InsertUploaderEventListener;
import listener.uploaderListener.ListUploaderMediaCountEventListener;
import util.Command;
import viewControl.Console;

/**
 * AlternativeCLI Class for managing Media
 *
 * Disabled features:
 * 1. Deleting uploaders (producers)
 * 2. Listing tags
 */
public class AlternativeCLI {

    public static void main(String[] args) {
        long capacity = Long.parseLong(args[0]);
        Admin ad = new Admin(capacity);
        Command cmd = new Command(ad, capacity);
        Console view = new Console(cmd);

        //Observer for capacity
        Observer capacityObserver = new CapacityObserver(ad);
        ad.registerObserver(capacityObserver);


        // Event Handler without deleteUploaderHandler and tagMediaHandler
        EventHandler<InsertUploaderEvent> insertUploaderHandler = new EventHandler<>();
        EventHandler<ListUploaderMediaCountEvent> listUploaderMediaCountHandler = new EventHandler<>();
        EventHandler<InsertMediaEvent> insertMediaHandler = new EventHandler<>();
        EventHandler<DeleteMediaEvent> deleteMediaHandler = new EventHandler<>();
        EventHandler<UpdateMediaEvent> updateMediaHandler = new EventHandler<>();
        EventHandler<FilterMediaEvent> filterMediaHandler = new EventHandler<>();

        // Event Listener without deleteUploaderListener and tagMediaListener
        InsertUploaderEventListener insertUploaderListener = new InsertUploaderEventListener(ad);
        ListUploaderMediaCountEventListener listUploaderMediaCountListener = new ListUploaderMediaCountEventListener(ad);
        InsertMediaEventListener insertMediaListener = new InsertMediaEventListener(ad);
        DeleteMediaListener deleteMediaListener = new DeleteMediaListener(ad);
        UpdateMediaEventListener updateMediaListener = new UpdateMediaEventListener(ad);
        FilterMediaEventListener filterMediaListener = new FilterMediaEventListener(ad);


        // Add Listener to Handler
        insertUploaderHandler.add(insertUploaderListener);
        listUploaderMediaCountHandler.add(listUploaderMediaCountListener);
        insertMediaHandler.add(insertMediaListener);
        deleteMediaHandler.add(deleteMediaListener);
        updateMediaHandler.add(updateMediaListener);
        filterMediaHandler.add(filterMediaListener);

        // Set Handler to Command
        cmd.setGetAllProducerMediaCountHandler(listUploaderMediaCountHandler);
        cmd.setMediaInsertHandler(insertMediaHandler);
        cmd.setMediaDeleteHandler(deleteMediaHandler);
        cmd.setMediaUpdateHandler(updateMediaHandler);
        cmd.setFilterMediaHandler(filterMediaHandler);
        cmd.setUploaderInsertHandler(insertUploaderHandler);

        // Start View
        view.execute();
    }


}
