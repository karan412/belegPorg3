package handler.MediaEvents;

import java.util.EventObject;

public class UpdateMediaEvent extends EventObject {
    private String address;

    public UpdateMediaEvent(Object source, String address){
        super(source);
        this.address = address;
    }

    public String getAddress(){
        return address;
    }
}
