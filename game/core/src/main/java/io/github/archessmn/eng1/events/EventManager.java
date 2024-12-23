package io.github.archessmn.eng1.events;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import io.github.archessmn.eng1.World;

/**
 * determines a random time for the event to start and checks each frame if
 * an event is active or should be active.
 */
public class EventManager {
    private final Random rand = new Random();
    private Event currentEvent;
    private int eventStartTime, nextEvent, eventPhase;
    private boolean eventActive;

    /**
     * create an EventManager instance to manage when events are run.
     */
    public EventManager() {
        // private static int eventStartTime = randInt.nextInt(1,85);
        this.eventStartTime = 2;// for testing

        this.nextEvent = 2;
        this.eventActive = false;

        this.eventPhase = 0;
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
            switch (eventPhase) {
                case 0 -> eventPhase = currentEvent.eventStart(elapsedTime);
                case 1 -> eventPhase = currentEvent.eventMain(elapsedTime);
                case 2 -> {
                    eventPhase = currentEvent.eventEnd();
                    if (eventPhase == 0) {
                        eventActive = false;
                    }
                }
                default -> throw new AssertionError();
            }
            if (eventPhase == -1) {
                eventActive = false;
            }
            if (!eventActive) {
                eventStartTime = elapsedTime + rand.nextInt(5, 6);// for testing
                //nextEvent = rand.nextInt(1, 4);
                nextEvent = 2;
                eventPhase = 0;
            }
            return true;
        } else {
            eventActive = (eventStartTime == elapsedTime);
            if (eventActive) {
                switch (nextEvent) {
                    case 1 -> currentEvent = new LectureHallMaintenance(world, stage);
                    case 2 -> currentEvent = new AlienInvasion(world, stage);
                    case 3 -> currentEvent = new GoodBadLecture(world, rand.nextBoolean());
                    default -> throw new AssertionError();
                }
            }
            return eventActive;
        }
    }

    /**
     * runs the events draw method if it is currently running.
     * 
     * @param batch gameScreen's batch used to draw sprites.
     */
    public void drawEvent(SpriteBatch batch) {
        if (eventActive) {
            currentEvent.draw(batch);
        }
    }

}
