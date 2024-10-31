package listener.MediaEventListener;

import contract.Tag;
import domainLogic.Admin;
import handler.MediaEvents.TagMediaEvent;
import listener.Listener;

import java.util.Set;

public class TagMediaEventListener implements Listener<TagMediaEvent> {
    private Admin admin;

    public TagMediaEventListener(Admin admin) {
        this.admin = admin;
    }

    @Override
    public void onEvent(TagMediaEvent event) {
        if ("i".equals(event.getMode())) {
            // Tags used by media
            Set<Tag> allTags = admin.getAllTags();
            System.out.println("All Tags used by media: " + allTags);

        } else if ("e".equals(event.getMode())) {
            // Unused tags
            Set<Tag> unusedTags = admin.getUnusedTags();
            System.out.println("Unused Tags: " + unusedTags);
        }
    }
}
