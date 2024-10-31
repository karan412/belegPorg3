package handler.MediaEvents;

import listener.Listener;

import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;

public class EventHandler<T extends EventObject> {
    private final List<Listener<T>> listenerList = new LinkedList<>();

    public void add(Listener<T> listener) {
        listenerList.add(listener);
    }

    public void remove(Listener<T> listener) {
        listenerList.remove(listener);
    }

    public void handle(T event) {
        for (Listener<T> listener : listenerList) {
            listener.onEvent(event);
        }
    }
}
