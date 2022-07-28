package Classes;

import java.util.List;

public class Venue {
    String venueID; //string/int according to our structure document
    String creatorID;
    String name;
    String location;
    List<String> sportsOffered;
    String openTime;

    List<Event> eventsList;
}
