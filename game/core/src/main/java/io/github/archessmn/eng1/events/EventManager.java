package io.github.archessmn.eng1.events;

import java.util.Random;
import com.badlogic.gdx.scenes.scene2d.Stage;

import io.github.archessmn.eng1.core.World;

/**
 * determines a random time for the event to start and checks each frame if
 * an event is active or should be active.
 * If the event is active run the start, main, or end of that event, determined
 * using eventPhase.
 */
public class EventManager {
    private final Random rand = new Random();
    private Event currentEvent;
    private int eventStartTime, nextEvent, eventPhase, minStartBound, maxStartBound;
    private boolean eventActive;

    /**
     * create an EventManager instance to manage when events are run.
     * 
     * @param minBound the minimum amount of time the manager should wait before
     *                 starting an event.
     * @param maxBound the maximum amount of time the manager should wait before
     *                 starting an event.
     */
    public EventManager(int minBound, int maxBound) {
        if (minBound < 0) {
            throw new IllegalArgumentException("minBound should be positive.");
        }
        if (maxBound <= minBound) {
            throw new IllegalArgumentException("maxBound should be greater than minBound.");
        }

        this.minStartBound = minBound;
        this.maxStartBound = maxBound;

        eventStartTime = rand.nextInt(minStartBound, maxStartBound);
        nextEvent = rand.nextInt(1, 4);
        eventActive = false;
        eventPhase = 0;
    }

    /**
     * Allows you to specify what the first event ran should be.
     * Mainly for testing.
     * 
     * @param firstEvent int 1, 2, or 3, to decide which event to run.
     */
    public EventManager(int minBound, int maxBound, int firstEvent) {
        this(minBound, maxBound);

        if (firstEvent < 1 || firstEvent > 3) {
            throw new IllegalArgumentException("Invalid firstEvent chosen.");
        }
        this.nextEvent = firstEvent;
    }

    /**
     * Run an event if it is active, or check if there is an event starting this
     * frame.
     * 
     * @param world       the World instance used for buildings.
     * @param stage       Stage instance used for placing imagebuttons.
     * @param elapsedTime The number of seconds the player has spent unpaused.
     * @return true if an event is running
     * @return false if no event is running
     */
    public boolean checkEvents(World world, Stage stage, int elapsedTime) {
        if (eventActive) {
            // run event
            switch (eventPhase) {
                case 0 -> {
                    eventPhase = currentEvent.eventStart(elapsedTime);
                    if (eventPhase == -1) {
                        eventActive = false;
                    }
                }
                case 1 -> eventPhase = currentEvent.eventMain(elapsedTime);
                case 2 -> {
                    eventPhase = currentEvent.eventEnd();
                    if (eventPhase == 0) {
                        eventActive = false;
                    }
                }
                default -> throw new AssertionError();
            }

            // after event finished, get new event and start time
            if (!eventActive) {
                eventStartTime = elapsedTime + rand.nextInt(minStartBound, maxStartBound);
                eventPhase = 0;
                nextEvent = rand.nextInt(1, 4);
            }

            return eventActive;
        } else {
            eventActive = (eventStartTime == elapsedTime);
            if (eventActive) {
                switch (nextEvent) {
                    case 1 -> currentEvent = new LectureHallMaintenance(world, stage);
                    case 2 -> currentEvent = new AlienInvasion(world, stage);
                    case 3 -> currentEvent = new GoodBadLecture(world, stage, rand.nextBoolean());
                    default -> throw new AssertionError();
                }

            }
            return eventActive;
        }
    }

    public int getMinStartBound() {
        return minStartBound;
    }

    public int getMaxStartBound() {
        return maxStartBound;
    }

    public int getEventStartTime() {
        return eventStartTime;
    }

    public int getNextEvent() {
        return nextEvent;
    }

    public int getEventPhase() {
        return eventPhase;
    }

    public boolean getEventActive() {
        return eventActive;
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

}
