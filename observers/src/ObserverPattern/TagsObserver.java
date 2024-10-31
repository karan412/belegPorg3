package ObserverPattern;

import contract.Tag;
import domainLogic.Admin;
import interfaces.Observer;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * An Observer that observes the Admin for changes in the available tags.
 */
public class TagsObserver implements Serializable, Observer {

    public Set<Tag> availableTags = new HashSet<>();
    private Admin admin;

    public TagsObserver(Admin admin) {
        this.admin = admin;
    }


    @Override
    public void update() {
        // Get available tags
        Set<Tag> previousTags = new HashSet<>(availableTags);

        // Get current tags from the Admin (Observable)
        Set<Tag> currentTags = admin.getAllTags();

        // Update the availableTags with the new currentTags
        availableTags = new HashSet<>(currentTags);

        Set<Tag> addedTags = new HashSet<>(currentTags);
        addedTags.removeAll(previousTags);  // Tags that are in currentTags but not in previousTags

        Set<Tag> removedTags = new HashSet<>(previousTags);
        removedTags.removeAll(currentTags);  // Tags that are in previousTags but not in currentTags

        // Log the changes
        if (!addedTags.isEmpty()) {
            System.out.println("New Tags added: " + addedTags);
        }

        if (!removedTags.isEmpty()) {
            System.out.println("Tags removed: " + removedTags);
        }
    }
}

