package Classes;

import java.util.*; //java.util.List

public class User extends Account {
    List <Event> eventsCreated;
    List<Event> eventsJoined;

    public void requestEvent (){
    }
    public boolean joinEvent(){
        return false;
    }
}
