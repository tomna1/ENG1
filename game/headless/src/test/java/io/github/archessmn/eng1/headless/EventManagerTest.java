package io.github.archessmn.eng1.headless;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.github.archessmn.eng1.World;
import io.github.archessmn.eng1.buildings.LectureHallBuilding;
import io.github.archessmn.eng1.events.AlienInvasion;
import io.github.archessmn.eng1.events.EventManager;
import io.github.archessmn.eng1.events.GoodBadLecture;
import io.github.archessmn.eng1.events.LectureHallMaintenance;

public class EventManagerTest extends AbstractHeadlessGdxTest {
        /**
         * Tests EventManager constructors and getters
         */
        @Test
        public void EventManagerInit() {
                int testMinBound = 5;
                int testMaxBound = 6;

                EventManager eventManager = new EventManager(testMinBound, testMaxBound);

                assertEquals(testMinBound, eventManager.getMinStartBound(),
                                "The actual minimum bound should be the same as testMinBound.");

                assertEquals(testMaxBound, eventManager.getMaxStartBound(),
                                "The actual maximum bound should be the same as testMaxBound.");

                assertEquals(0, eventManager.getEventPhase(),
                                "The actual event phase should be 0.");

                assertFalse(eventManager.getEventActive(),
                                "The event should be False.");

                assertTrue(testMinBound <= eventManager.getEventStartTime()
                                && testMaxBound > eventManager.getEventStartTime(),
                                "The eventStartTime should be between testMinBound and testMaxBound");

                assertTrue(1 <= eventManager.getNextEvent()
                                || 4 > eventManager.getEventStartTime(),
                                "The next event should be 1, 2, or 3");

                eventManager = new EventManager(testMinBound, testMaxBound, 3);

                assertEquals(eventManager.getNextEvent(), 3);

        }

        /**
         * Tests that the constructor throws IllegalArgumentExcepetion with illegal
         * min and max bounds, or with an invalid firstEvent.
         */
        @Test
        public void illegalParametersInit() {
                final int minBound = 5;
                final int maxBound = 6;

                // minBound < 0
                final int testMinBound = -1;
                assertThrows(IllegalArgumentException.class, () -> new EventManager(testMinBound, maxBound),
                                "Should throw IllegalArgumentException if minBound < 0.");

                // maxBound <= minBound
                final int testMaxBound = 5;
                assertThrows(IllegalArgumentException.class, () -> new EventManager(minBound, testMaxBound),
                                "Should throw IllegalArgumentException if maxBound <= minBound.");

                // firstEvent < 1
                final int testFirstEventLower = 0;
                assertThrows(IllegalArgumentException.class,
                                () -> new EventManager(minBound, testMaxBound, testFirstEventLower),
                                "Should throw IllegalArgumentException if firstEvent < 1");

                // firstEvent > 3
                final int testFirstEventHigher = 4;
                assertThrows(IllegalArgumentException.class,
                                () -> new EventManager(minBound, testMaxBound, testFirstEventHigher),
                                "Should throw IllegalArgumentException if firstEvent > 3");
        }

        /**
         * test that an event will start and set all values correctly, and also end
         * and reset those values if the event fails due to there being no buildings.
         */
        @Test
        public void eventsStartTest() {
                World world = new World(100, 100);
                FitViewport viewport = new FitViewport(1000, 1000);

                // not really sure about this :/
                Stage stage = new Stage(viewport, mock(SpriteBatch.class));

                EventManager eventManager = new EventManager(0, 1);

                int eventShouldStartAt = eventManager.getEventStartTime();
                int eventShouldBe = eventManager.getNextEvent();

                eventManager.checkEvents(world, stage, eventShouldStartAt);

                assertTrue(eventManager.getEventActive(),
                                "eventActive should be True.");

                assertEquals(eventManager.getEventPhase(), 0,
                                "eventPhase should be 0.");

                switch (eventShouldBe) {
                        case 1:
                                assertInstanceOf(LectureHallMaintenance.class, eventManager.getCurrentEvent(),
                                                "Current event should be of type LectureHallMaintenance");
                                break;
                        case 2:
                                assertInstanceOf(AlienInvasion.class, eventManager.getCurrentEvent(),
                                                "Current event should be of type AlienInvasion");
                                break;
                        case 3:
                                assertInstanceOf(GoodBadLecture.class, eventManager.getCurrentEvent(),
                                                "Current event should be of type GoodBadLecture");
                                break;

                        default:
                                break;
                }

                // THIS PART ONLY WORKS IF ALL EVENTS FAIL IF THERE ARE NO BUILDINGS
                // WHICH THEY CURRENTLY ALL DO.
                eventManager.checkEvents(world, stage, 1);

                assertEquals(eventManager.getEventStartTime(), 1,
                                "eventStartTime should be 1.");
                assertFalse(eventManager.getEventActive(),
                                "eventActive should be false");
        }

        /**
         * Tests that the event manager can run through an entire event, and set
         * the values for the eventPhase accordingly. Also that it resets
         * eventActive and changes eventStartTime after finishing.
         */
        @Test
        public void eventPhaseChangeTest() {
                World world = new World(100, 100);
                LectureHallBuilding lecturehall = new LectureHallBuilding(world);
                world.addBuilding(lecturehall);

                FitViewport viewport = new FitViewport(1000, 1000);

                // not really sure about this :/
                Stage stage = new Stage(viewport, mock(SpriteBatch.class));

                EventManager eventManager = new EventManager(0, 1, 3);

                eventManager.checkEvents(world, stage, 0);
                assertTrue(eventManager.getEventActive(),
                                "Event should be active.");
                assertEquals(0, eventManager.getEventPhase(),
                                "Event phase should be 0.");

                eventManager.checkEvents(world, stage, 0);

                assertEquals(1, eventManager.getEventPhase(),
                                "Event phase should be 1.");

                eventManager.checkEvents(world, stage, 6);
                assertEquals(2, eventManager.getEventPhase(),
                                "Event phase should be 2.");

                eventManager.checkEvents(world, stage, 6);
                assertEquals(0, eventManager.getEventPhase(),
                                "Event phase should be reset to 0.");
                assertEquals(6, eventManager.getEventStartTime(),
                                "Event start time should now be 7.");
                assertFalse(eventManager.getEventActive(),
                                "Event active should be false;");

        }
}
