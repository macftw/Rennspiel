package events;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Creates EventTypes for RaceEvent
 */
public class RaceEvent extends Event {

    public static final EventType<RaceEvent> CRASH = new EventType(ANY, "CRASH");
    public static final EventType<RaceEvent> START = new EventType(ANY, "START");
    public static final EventType<RaceEvent> CHECKPOINT = new EventType(ANY, "CHECKPOINT");
    public static final EventType<RaceEvent> STARTINGLINE = new EventType(ANY, "STARTINGLINE");
    public static final EventType<RaceEvent> FINISH = new EventType(ANY, "FINISH");
    public static final EventType<RaceEvent> OBSTACLE = new EventType(ANY, "OBSTACLE");

    public String time;
    /**
     * Sets up a new RaceEvent that can occur during a RaceGame
     *
     * @param type specified EventType
     */
    public RaceEvent(EventType<RaceEvent> type) {
        super(type);
    }
    /**
     * Sets up a new RaceEvent that can occur during a RaceGame
     *
     * @param type specified EventType
     * @param time additional String Parameter that can be sent with the Event
     */
    public RaceEvent(EventType<RaceEvent> type, String time) {
        super(type);
        this.time = time;
    }

}
