package io.github.archessmn.eng1.headless;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.github.archessmn.eng1.Timer;
import io.github.archessmn.eng1.World;
import io.github.archessmn.eng1.buildings.GymBuilding;
import io.github.archessmn.eng1.buildings.LectureHallBuilding;
import io.github.archessmn.eng1.events.AlienInvasion;
import io.github.archessmn.eng1.events.GoodBadLecture;
import io.github.archessmn.eng1.events.LectureHallMaintenance;

public class AlienInvasionTest extends AbstractHeadlessGdxTest {

        /*
         * test AlienInvasion constructor, getters and setters
         */
        @Test
        public void eventInit() {
                Timer timer = mock(Timer.class);
                World world = new World(100, 100, timer);
                FitViewport viewport = new FitViewport(1000, 1000);
                Stage stage = new Stage(viewport, mock(SpriteBatch.class));

                float[] testArray = { -45f, -45f };

                AlienInvasion testEvent = new AlienInvasion(world, stage);

                // test getter
                assertEquals(18, testEvent.getEventDuration(),
                                "Event duration should be 20 seconds.");
                assertFalse(testEvent.isTractorActive(),
                                "Tractor active should be false at initialisation");
                assertFalse(testEvent.hasStarted(),
                                "Starting should be false at initialisation.");
                assertFalse(testEvent.getAbduction(),
                                "Should be false at initialisation.");
                assertArrayEquals(testArray, testEvent.getUFOcoords());

                // test setters
                testEvent.setClickCount(50);
                testEvent.setTractorActive(true, 0);
                float[] testCoords = { 150f, 150f };
                testEvent.setUFOcoords(150, 150);

                assertEquals(50, testEvent.getClickCount(),
                                "Click count should be 50 after setting.");
                assertTrue(testEvent.isTractorActive(),
                                "Should be true after setter.");
                assertArrayEquals(testCoords, testEvent.getUFOcoords(),
                                "Should be 150, 150 after setter.");

                stage.dispose();
                world.dispose();
        }

        /*
         * Tests eventStart will return -1 if there is less than 2 buildings.
         * Also tests that variables are set correctly and the news page is added to
         * the stage then removed and the UFO added, eventStart then returning 1.
         */
        @Test
        public void testEventStart() {
                Timer timer = mock(Timer.class);
                World world = new World(100, 100, timer);
                GymBuilding gym = new GymBuilding(world);

                FitViewport viewport = new FitViewport(1000, 1000);
                Stage stage = new Stage(viewport, mock(SpriteBatch.class));

                AlienInvasion testEvent = new AlienInvasion(world, stage);
                int testPhase;

                // no buildings
                testPhase = testEvent.eventStart(0);
                assertEquals(-1, testPhase,
                                "Should have returned -1 with no buildings");

                // 2 buildings, starts eventStart
                world.addBuilding(gym);
                world.addBuilding(gym);
                testPhase = testEvent.eventStart(0);
                assertEquals(0, testPhase,
                                "Should have returned 0 on first run of eventStart, with at least 2 buildings.");

                assertTrue(testEvent.hasStarted(),
                                "Should be true after first run of eventStart.");
                assertEquals(0, testEvent.getStartTime(),
                                "Start time should be 0.");
                assertEquals(1, stage.getActors().size,
                                "Should have 1 Actor in stage if newsPage added correctly.");

                // end of eventStart
                testPhase = testEvent.eventStart(6);
                assertEquals(1, testPhase,
                                "Should return 1 at end of eventStart.");
                assertEquals(1, stage.getActors().size,
                                "Should be 1 after removing newsPage and adding UFO.");

                stage.dispose();
                world.dispose();

        }

        /*
         * Tests eventMain will return 1 and move the UFO in the correct speed and
         * direction if the event has not reached its max duration,
         * and that once it has, it will set the correct speed and coordinates for
         * the UFO's final destination off the screen and return 2.
         */
        @Test
        public void testEventMainMovementAndEndOfMain() {
                Timer timer = mock(Timer.class);
                World world = new World(100, 100, timer);
                GymBuilding gym = new GymBuilding(world);
                gym.setX(65);
                gym.setY(50);

                FitViewport viewport = new FitViewport(1000, 1000);
                Stage stage = new Stage(viewport, mock(SpriteBatch.class));

                AlienInvasion testEvent = new AlienInvasion(world, stage);
                int testPhase;

                world.addBuilding(gym);
                world.addBuilding(gym);
                testPhase = testEvent.eventStart(0);
                testPhase = testEvent.eventStart(6);

                // test moving towards building
                float[] testNewUFOcoords = { -44f, -44f };
                testPhase = testEvent.eventMain(7);
                assertEquals(1, testPhase,
                                "Test phase should be 1 as main has not finished.");
                assertArrayEquals(testNewUFOcoords, testEvent.getUFOcoords(),
                                "UFO Coords should be incremented by 1.");

                // test ending main and moving towards final destination
                float[][] testNextBuildingPos = { { 500, 545 }, { 4.525f, 4.9f } };
                testPhase = testEvent.eventMain(6 + testEvent.getEventDuration());
                assertEquals(2, testPhase,
                                "Should return 2 as event has reached its max duration.");
                assertArrayEquals(testNextBuildingPos, testEvent.getNextBuildingPos());

                stage.dispose();
                world.dispose();

        }

        /**
         * Tests that it will record an abduction after the tractor has been active
         * for 5 seconds, and that it will set tractorActive false and reset clickCount
         * to 0 after 5 clicks.
         * Tests that eventMain returns 1 when not finished and tractor is active.
         */
        @Test
        public void testEventMainWhenTractorActive() {
                Timer timer = mock(Timer.class);
                World world = new World(100, 100, timer);
                LectureHallBuilding lecturehall = new LectureHallBuilding(world);
                GymBuilding gym = new GymBuilding(world);
                gym.setX(65);
                gym.setY(50);
                lecturehall.setX(60);
                lecturehall.setY(45);

                FitViewport viewport = new FitViewport(1000, 1000);
                Stage stage = new Stage(viewport, mock(SpriteBatch.class));

                AlienInvasion testEvent = new AlienInvasion(world, stage);
                int testPhase;

                world.addBuilding(gym);
                world.addBuilding(lecturehall);
                testPhase = testEvent.eventStart(0);
                testPhase = testEvent.eventStart(6);

                // test when tractor has been active for 3 seconds and UFO clicked 3 times.
                testEvent.setTractorActive(true, 7);
                testEvent.setClickCount(3);
                testEvent.eventMain(10);
                assertEquals(1, testPhase,
                                "Event should return 1 as main is not finished.");
                assertEquals(3, testEvent.getClickCount(),
                                "Should be 3, resets when clickCount reaches 5");
                assertTrue(testEvent.isTractorActive(),
                                "Should be true as UFO has not been clicked 5 times.");
                assertFalse(testEvent.getAbduction(),
                                "Should be false as tractor has not been active for 5 seconds.");

                // test when tractor has been active for 4 seconds and UFO clicked 5 times.
                testEvent.setClickCount(5);
                testEvent.eventMain(11);
                assertEquals(0, testEvent.getClickCount(),
                                "Should be 0 as it resets after 5 clicks.");
                assertFalse(testEvent.isTractorActive(),
                                "Should be false as the UFO was clicked 5 times.");
                assertTrue(testEvent.getAbduction(),
                                "Should be true as tractor has been true for 4 seconds.");

                stage.dispose();
                world.dispose();
        }

        /*
         * Test that the UFO correctly moves towards the finals positions and
         * ends the event after reaching it.
         */
        @Test
        public void testEventEnd() {
                Timer timer = mock(Timer.class);
                World world = new World(100, 100, timer);
                GymBuilding gym = new GymBuilding(world);
                gym.setX(65);
                gym.setY(50);

                FitViewport viewport = new FitViewport(1000, 1000);
                Stage stage = new Stage(viewport, mock(SpriteBatch.class));

                AlienInvasion testEvent = new AlienInvasion(world, stage);
                int testPhase;

                world.addBuilding(gym);
                world.addBuilding(gym);
                testPhase = testEvent.eventStart(0);
                testPhase = testEvent.eventStart(6);
                testPhase = testEvent.eventMain(6 + testEvent.getEventDuration());

                // test moving towards final position
                float[] testNewUFOcoords = { -39.4666666667f, -39.0916666667f };
                testPhase = testEvent.eventEnd();
                assertEquals(2, testPhase,
                                "Should return 2 as UFO not in final position yet.");
                assertArrayEquals(testNewUFOcoords, testEvent.getUFOcoords(),
                                "UFO Coords should match test coords.");

                // test event finishes correctly when UFO reaches final destination
                testEvent.setUFOcoords(495.466666667f, 540.091666667f);
                testPhase = testEvent.eventEnd();
                assertEquals(0, testPhase,
                                "Should return 0 now that event is fully finished.");
                assertEquals(0, stage.getActors().size,
                                "Stage should have 0 actors after UFO is removed.");

                stage.dispose();
                world.dispose();
        }

        /*
         * Tests that hit is set to true when the UFO is clicked and clickCounter is
         * incremented by 1. Also tests that hit is reset to false when eventMain is
         * run.
         */
        @Test
        public void testUFOclick() {
                Timer timer = mock(Timer.class);
                World world = new World(100, 100, timer);
                GymBuilding gym = new GymBuilding(world);
                gym.setX(65);
                gym.setY(50);

                FitViewport viewport = new FitViewport(1000, 1000);
                Stage stage = new Stage(viewport, mock(SpriteBatch.class));

                AlienInvasion testEvent = new AlienInvasion(world, stage);
                testEvent.setTractorActive(true, 7);
                int testPhase;

                world.addBuilding(gym);
                world.addBuilding(gym);
                testPhase = testEvent.eventStart(0);
                testPhase = testEvent.eventStart(6);

                Image eventUFO;
                InputEvent clickDown = new InputEvent();
                clickDown.setType(InputEvent.Type.touchDown);
                InputEvent clickUp = new InputEvent();
                clickUp.setType(InputEvent.Type.touchUp);

                assertFalse(testEvent.isHit(),
                                "Should be false until clicked.");
                assertEquals(0, testEvent.getClickCount(),
                                "Should only increment on click.");

                eventUFO = (Image) stage.getActors().get(0);
                eventUFO.fire(clickDown);
                eventUFO.fire(clickUp);

                assertTrue(testEvent.isHit(),
                                "Should be true after click.");
                assertEquals(1, testEvent.getClickCount(),
                                "Should increment by 1 on click.");

                testPhase = testEvent.eventMain(11);

                assertFalse(testEvent.isHit(),
                                "Should be reset to false when eventMain is run.");

                stage.dispose();
                world.dispose();
        }
}
