package handler.MediaEvents;

import java.util.EventObject;

public class FilterMediaEvent extends EventObject {
    private String filterType;

    public FilterMediaEvent(Object source, String filterType) {
        super(source);
        this.filterType = filterType;
    }

    public String getFilterType() { return filterType; }

}
