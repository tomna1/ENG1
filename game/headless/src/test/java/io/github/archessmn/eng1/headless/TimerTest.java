package io.github.archessmn.eng1.headless;

import org.junit.jupiter.api.Test;
import io.github.archessmn.eng1.Timer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimerTest {
    /**
     * Test the timers constructor. And the basic getters.
     */
    @Test
    public void initTest() {
        Timer timer = new Timer(35, 7);
        assertEquals(35, timer.getMaxTime(),
            "The maxTime should be 35 as defined in constructor.");
        assertEquals(7.0f, timer.getSecondsPerYear(),
            "The secondsPerYear should be 7 as defined in constructor.");
        assertEquals(0.0f, timer.getElapsedTime(),
            "The timer should have no elapsed time if it has not been updated.");
        assertEquals(false, timer.hasEnded(),
            "Should be false as timer has not ended.");
    }

    /**
     * Test the timers update(float) method. Assumes the getElapsedTime() method
     * works.
     */
    @Test
    public void timerTimeTest() {
        Timer timer = new Timer(10, 1);
        assertEquals(0.0f, timer.getElapsedTime(),
            "The timer should have no elapsed time if it has not been updated.");
        
        timer.update(2.0f);
        assertEquals(2.0f, timer.getElapsedTime(),
            "The timer elapsed time should have updated to 2.0f.");
        
        timer.update(2.5f);
        assertEquals(4.5f, timer.getElapsedTime(),
            "The timer elapsed time should have updated to 4.5f.");
    }

    /**
     * Test the timers hasEnded() method. Assumes constructor and update(float)
     * methods work.
     */
    @Test
    public void hasEndedTest() {
        Timer timer = new Timer(10, 1);
        assertEquals(false, timer.hasEnded(),
            "Should be false as timer has not ended.");
        
        timer.update(5.0f);
        assertEquals(false, timer.hasEnded(),
            "Should be false as timer has not ended.");
        
        timer = new Timer(10, 1);
        timer.update(10.0f);
        assertEquals(true, timer.hasEnded(),
            "Timer should have ended as it reached the maxTime.");
        
        timer = new Timer(10, 1);
        timer.update(20.0f);
        assertEquals(true, timer.hasEnded(),
            "Timer should have ended as it went over maxTime.");
    }

    /**
     * Tests the getDayCount() method. Assumes that update(float) and
     * getDayCount() already work.
     */
    @Test
    public void dayCountTest() {
        Timer timer = new Timer(10000, 365);
        assertEquals(0, timer.getDayCount(),
            "Day count should be 0 as timer has not updated.");
        
        timer.update(1.0f);
        assertEquals(1, timer.getDayCount(),
            "Day count should be 1 as per update() and secondsPerYear.");
        
        timer.update(364.0f);
            assertEquals(365.0f, timer.getDayCount(),
            "Day count should be 1 as 1 year has passed so it resets.");
    }

    /**
     * Tests the getYearCount() method. Assumes that update(float) and
     * getYearCount() already work.
     */
    @Test
    public void yearCountTest() {
        Timer timer = new Timer(10000, 10);
        assertEquals(1, timer.getYearCount(),
            "Year count should start as 1.");

        timer.update(10.0f);
        assertEquals(2, timer.getYearCount(),
            "Year count should be 2 as per update and secondsPerYear");
    }
}
