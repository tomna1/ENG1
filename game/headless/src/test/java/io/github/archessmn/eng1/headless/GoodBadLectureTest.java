package io.github.archessmn.eng1.headless;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.utils.viewport.FitViewport;

import io.github.archessmn.eng1.World;
import io.github.archessmn.eng1.buildings.GymBuilding;
import io.github.archessmn.eng1.buildings.LectureHallBuilding;
import io.github.archessmn.eng1.events.GoodBadLecture;

public class GoodBadLectureTest extends AbstractHeadlessGdxTest {
        /* Tests GoodBadLecture constructor and getters */
        @Test
        public void GoodBadLectureInit() {
                World world = new World(100, 100);
                FitViewport viewport = new FitViewport(1000, 1000);
                Stage stage = new Stage(viewport, mock(SpriteBatch.class));

                GoodBadLecture testEvent = new GoodBadLecture(world, stage, true);
                assertTrue(testEvent.getPositive(),
                                "Should be positive event.");

                assertEquals(4, testEvent.getSatisfactionChange(),
                                "Satisfaction change should be +5.");

                assertEquals(6, testEvent.getEventDuration(),
                                "Event duration should be 6 seconds.");

                testEvent = new GoodBadLecture(world, stage, false);
                assertFalse(testEvent.getPositive(),
                                "Should be a negative event.");

                assertEquals(-3f, testEvent.getSatisfactionChange(),
                                "Satisfaction change should -5.");

                stage.dispose();
                world.dispose();
        }

        /*
         * Tests eventStart will return -1 if there are no lecture halls or offices.
         * Also tests that variables are set correctly and the news page is added to
         * the stage if a lecture hall is present, eventStart then returning 1.
         */
        @Test
        public void testEventStart() {
                World world = new World(100, 100);
                LectureHallBuilding lectureHall = new LectureHallBuilding(world);
                GymBuilding gym = new GymBuilding(world);

                FitViewport viewport = new FitViewport(1000, 1000);
                Stage stage = new Stage(viewport, mock(SpriteBatch.class));

                GoodBadLecture testEvent = new GoodBadLecture(world, stage, true);
                int testPhase;

                testPhase = testEvent.eventStart(0);
                assertEquals(-1, testPhase,
                                "Should have returned -1 with no buildings");

                world.addBuilding(gym);
                testPhase = testEvent.eventStart(0);
                assertEquals(-1, testPhase,
                                "Should have returned -1 with a building but no office or lecture hall.");

                world.addBuilding(lectureHall);
                testPhase = testEvent.eventStart(0);
                assertEquals(1, testPhase,
                                "Should have returned 1 after adding lectureHall to world.");
                assertEquals(0, testEvent.getStartTime(),
                                "Start time should be 0.");
                assertEquals(1, stage.getActors().size,
                                "Should be 1 Actor in stage, which is the news page from the event.");

                stage.dispose();
                world.dispose();
        }

        /*
         * Tests eventMain will return 1 if the event has not reached its max duration,
         * and that it will return 2 once it has.
         */
        @Test
        public void testEventMain() {
                World world = new World(100, 100);
                LectureHallBuilding lectureHall = new LectureHallBuilding(world);
                world.addBuilding(lectureHall);

                FitViewport viewport = new FitViewport(1000, 1000);
                Stage stage = new Stage(viewport, mock(SpriteBatch.class));

                GoodBadLecture testEvent = new GoodBadLecture(world, stage, true);
                int testPhase;

                testPhase = testEvent.eventStart(0);
                testPhase = testEvent.eventMain(testEvent.getEventDuration() - 1);
                assertEquals(1, testPhase,
                                "Test phase should still be 1 as event has not reached its duration.");

                testPhase = testEvent.eventMain(testEvent.getEventDuration());
                assertEquals(2, testPhase,
                                "Test phase should be 2 after elapsed time has reached event duration.");

                stage.dispose();
                world.dispose();
        }

        /*
         * Tests that eventEnd will return 0 and remove the news page from the stage.
         */
        @Test
        public void testEventEnd() {
                World world = new World(100, 100);
                LectureHallBuilding lectureHall = new LectureHallBuilding(world);
                world.addBuilding(lectureHall);

                FitViewport viewport = new FitViewport(1000, 1000);
                Stage stage = new Stage(viewport, mock(SpriteBatch.class));

                GoodBadLecture testEvent = new GoodBadLecture(world, stage, true);
                int testPhase;

                testPhase = testEvent.eventStart(0);
                testPhase = testEvent.eventEnd();
                assertEquals(0, testPhase,
                                "Test phase should be 0 after event has fully finished.");
                assertEquals(0, stage.getActors().size,
                                "Stage should have no actors if the newsPage was successfully removed from event.");

                stage.dispose();
                world.dispose();
        }
}
