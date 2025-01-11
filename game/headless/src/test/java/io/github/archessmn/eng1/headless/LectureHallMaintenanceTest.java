package io.github.archessmn.eng1.headless;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.github.archessmn.eng1.buildings.GymBuilding;
import io.github.archessmn.eng1.buildings.LectureHallBuilding;
import io.github.archessmn.eng1.core.Timer;
import io.github.archessmn.eng1.core.World;
import io.github.archessmn.eng1.events.LectureHallMaintenance;

public class LectureHallMaintenanceTest extends AbstractHeadlessGdxTest {

        /* Tests LectureHallMaintenance constructor and getters */
        @Test
        public void eventInit() {
                Timer timer = mock(Timer.class);
                World world = new World(100, 100, timer);
                FitViewport viewport = new FitViewport(1000, 1000);
                Stage stage = new Stage(viewport, mock(SpriteBatch.class));

                LectureHallMaintenance testEvent = new LectureHallMaintenance(world, stage);

                // test getter
                assertEquals(0f, testEvent.getSatisfactionChange(),
                                "Satisfaction change should start at 0.");
                assertEquals(15, testEvent.getEventDuration(),
                                "Event duration should be 15 seconds.");
                assertFalse(testEvent.isChoiceMade(),
                                "Choice made should be false at initialisation.");

                // test setter
                testEvent.setSatisfactionChange(50);
                assertEquals(50, testEvent.getSatisfactionChange());

                stage.dispose();
                world.dispose();
        }

        /*
         * Tests eventStart will return -1 if there are no lecture halls or offices.
         * Also tests that variables are set correctly and the news page and
         * ImageButtons are added to the stage if a lecture hall is present, eventStart
         * then returning 1.
         */
        @Test
        public void testEventStart() {
                Timer timer = mock(Timer.class);
                World world = new World(100, 100, timer);
                LectureHallBuilding lectureHall = new LectureHallBuilding(world);
                GymBuilding gym = new GymBuilding(world);

                FitViewport viewport = new FitViewport(1000, 1000);
                Stage stage = new Stage(viewport, mock(SpriteBatch.class));

                LectureHallMaintenance testEvent = new LectureHallMaintenance(world, stage);
                int testPhase;

                // no buildings
                testPhase = testEvent.eventStart(0);
                assertEquals(-1, testPhase,
                                "Should have returned -1 with no buildings");

                // 1 building but no office or lecture hall
                world.addBuilding(gym);
                testPhase = testEvent.eventStart(0);
                assertEquals(-1, testPhase,
                                "Should have returned -1 with a building but no office or lecture hall.");

                // lecture hall present
                world.addBuilding(lectureHall);
                testPhase = testEvent.eventStart(0);
                assertEquals(1, testPhase,
                                "Should have returned 1 after adding lectureHall to world.");
                assertEquals(0, testEvent.getStartTime(),
                                "Start time should be 0.");
                assertEquals(3, stage.getActors().size,
                                "Should be 3 Actors in stage, which is the news page and both ImageButtons from the event.");

                stage.dispose();
                world.dispose();
        }

        /*
         * Tests eventMain will return 1 if the event has not reached its max duration,
         * and that it will return 2 and satisfactionChange will be -20 once it has.
         */
        @Test
        public void testEventMainTimeout() {
                Timer timer = mock(Timer.class);
                World world = new World(100, 100, timer);
                LectureHallBuilding lectureHall = new LectureHallBuilding(world);
                world.addBuilding(lectureHall);

                FitViewport viewport = new FitViewport(1000, 1000);
                Stage stage = new Stage(viewport, mock(SpriteBatch.class));

                LectureHallMaintenance testEvent = new LectureHallMaintenance(world, stage);
                int testPhase;

                testPhase = testEvent.eventStart(0);

                // event hasnt finished
                testPhase = testEvent.eventMain(testEvent.getEventDuration() - 1);
                assertEquals(1, testPhase,
                                "Test phase should still be 1 as event has not reached its duration.");

                // event main is finished
                testPhase = testEvent.eventMain(testEvent.getEventDuration());
                assertEquals(2, testPhase,
                                "Test phase should be 2 after elapsed time has reached event duration.");
                assertEquals(0f, testEvent.getSatisfactionChange(),
                                "Satisfaction change should still be 0 on event timeout.");

                stage.dispose();
                world.dispose();
        }

        /*
         * Tests that choiceMade and choseOnline are set correctly on clicking the
         * online button. Also tests that eventMain handles this choice correctly
         */
        @Test
        public void testOnlineLectureChoice() {
                Timer timer = mock(Timer.class);
                World world = new World(100, 100, timer);
                LectureHallBuilding lectureHall = new LectureHallBuilding(world);
                world.addBuilding(lectureHall);

                FitViewport viewport = new FitViewport(1000, 1000);
                Stage stage = new Stage(viewport, mock(SpriteBatch.class));
                ImageButton eventOnlineButton;
                InputEvent clickDown = new InputEvent();
                clickDown.setType(InputEvent.Type.touchDown);
                InputEvent clickUp = new InputEvent();
                clickUp.setType(InputEvent.Type.touchUp);

                LectureHallMaintenance testEvent = new LectureHallMaintenance(world, stage);
                int testPhase;

                testPhase = testEvent.eventStart(0);

                eventOnlineButton = (ImageButton) stage.getActors().get(1);
                eventOnlineButton.fire(clickDown);
                eventOnlineButton.fire(clickUp);

                // does button click work correctly
                assertTrue(testEvent.isChoiceMade(),
                                "Choice made should be true after a button is clicked.");
                assertTrue(testEvent.hasChoseOnline(),
                                "Chose online should be true after online button is clicked.");

                // does eventMain handle button click changes correctly
                testPhase = testEvent.eventMain(0);
                assertEquals(2, testPhase,
                                "Test phase should be 2 after online choice has been processed.");
                assertEquals(5f, testEvent.getSatisfactionChange(),
                                "Satisfaction change should be 5 after choosing online lectures.");

                stage.dispose();
                world.dispose();
        }

        /*
         * Tests that choiceMade and choseOnline are set correctly on clicking the halls
         * button. Also tests that eventMain handles this choice correctly
         */
        @Test
        public void testHallsChoice() {
                Timer timer = mock(Timer.class);
                World world = new World(100, 100, timer);
                LectureHallBuilding lectureHall = new LectureHallBuilding(world);
                world.addBuilding(lectureHall);

                FitViewport viewport = new FitViewport(1000, 1000);
                Stage stage = new Stage(viewport, mock(SpriteBatch.class));

                ImageButton eventHallsButton;
                InputEvent clickDown = new InputEvent();
                clickDown.setType(InputEvent.Type.touchDown);
                InputEvent clickUp = new InputEvent();
                clickUp.setType(InputEvent.Type.touchUp);

                LectureHallMaintenance testEvent = new LectureHallMaintenance(world, stage);
                int testPhase;

                testPhase = testEvent.eventStart(0);

                eventHallsButton = (ImageButton) stage.getActors().get(2);
                eventHallsButton.fire(clickDown);
                eventHallsButton.fire(clickUp);

                // does button click work correctly
                assertTrue(testEvent.isChoiceMade(),
                                "Choice made should be true after a button is clicked.");
                assertFalse(testEvent.hasChoseOnline(),
                                "Chose online should be false after halls button is clicked.");

                // does eventMain handle button click changes correctly
                // satisfaction change with 1 lecture hall (minimum)
                testPhase = testEvent.eventMain(0);
                assertEquals(2, testPhase,
                                "Test phase should be 2 after halls choice has been processed.");
                assertEquals(2.5f, testEvent.getSatisfactionChange(),
                                "Satisfaction change should be 2.5 after choosing hall, with 1 lecture hall present.");

                // satisfaction change with 3 lecture halls (middle)
                testEvent.setSatisfactionChange(0);
                world.addBuilding(lectureHall);
                world.addBuilding(lectureHall);
                testPhase = testEvent.eventMain(0);
                assertEquals(7.5f, testEvent.getSatisfactionChange(),
                                "Satisfaction change should be 7.5 with 3 lecture halls.");

                // satisfaction change with 5 lecture halls(satisfactionChange cap)
                testEvent.setSatisfactionChange(0);
                world.addBuilding(lectureHall);
                world.addBuilding(lectureHall);
                testPhase = testEvent.eventMain(0);
                assertEquals(10, testEvent.getSatisfactionChange(),
                                "Satisfaction change should be 10 with 5 lecture halls.");

                stage.dispose();
                world.dispose();
        }

        /*
         * Tests that eventEnd will return 0 and remove the news page and ImageButtons
         * from the stage.
         */
        @Test
        public void testEventEnd() {
                Timer timer = mock(Timer.class);
                World world = new World(100, 100, timer);
                LectureHallBuilding lectureHall = new LectureHallBuilding(world);
                world.addBuilding(lectureHall);

                FitViewport viewport = new FitViewport(1000, 1000);
                Stage stage = new Stage(viewport, mock(SpriteBatch.class));

                LectureHallMaintenance testEvent = new LectureHallMaintenance(world, stage);
                int testPhase;
                float initialSatisfaction = world.getSatisfaction();

                testPhase = testEvent.eventStart(0);
                testPhase = testEvent.eventEnd();
                assertEquals(0, testPhase,
                                "Test phase should be 0 after event has fully finished.");
                assertEquals(0, stage.getActors().size,
                                "Stage should have no actors if the newsPage and ImageButtons were successfully removed from event.");
                assertEquals(initialSatisfaction - 11f, world.getSatisfaction(),
                                "Satisfaction should have decreases by 11.");

                stage.dispose();
                world.dispose();
        }

}
