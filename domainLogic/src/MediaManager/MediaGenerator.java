package MediaManager;

import domainLogic.AudioImpl;
import domainLogic.AudioVideoImpl;
import domainLogic.MediaContentImpl;
import domainLogic.VideoImpl;

public class MediaGenerator{

    private String type;

    private MediaContentImpl mediaContent;

    public MediaGenerator(String type){
        this.type = type;
    }

    public void generateMedia(String inp){
        switch(inp){
            case "Audio":
             //  AudioImpl audio = new AudioImpl(sampleRating, location);
                break;
            case "Video":
              //  VideoImpl video = new VideoImpl();
                break;
            case "Audiovideo":
                //AudioVideoImpl audioVideo = new AudioVideoImpl();
                break;
            default:
                System.out.println("Invalid type");
        }
    }
}
