package interfaces;

public interface Observable {

    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void observerNotify();


}
