package listener;

import java.util.EventObject;


public interface Listener<T extends EventObject> {

        void onEvent(T event);

}
