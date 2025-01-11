package io.github.archessmn.eng1.headless;

import org.junit.jupiter.api.Test;

import io.github.archessmn.eng1.core.Timer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TimerTest {
    /**
     * Test the timers constructor. And the basic getters.
     */
    @Test
    public void defaultInit() {
        int maxTime = 35;
        int secondsPerYear = 7;

        Timer timer = new Timer(maxTime, secondsPerYear);
        assertEquals(maxTime, timer.getMaxTime(),
                "The maxTime should be 35 as defined in constructor.");
        assertEquals(secondsPerYear, timer.getSecondsPerYear(),
                "The secondsPerYear should be 7 as defined in constructor.");
        assertEquals(0.0f, timer.getElapsedTime(),
                "The timer should have no elapsed time if it has not been updated.");
        assertEquals(false, timer.hasEnded(),
                "Should be false as timer has not ended.");
    }

    /**
     * Tests that the constructors throw IllegalArguementException when the
     * maxTime <= 0.
     */
    @Test
    public void illegalMaxTimeInit() {
        final int maxTime = 0;
        final int secondsPerYear = 5;
        assertThrows(IllegalArgumentException.class, () -> new Timer(maxTime, secondsPerYear),
                "Should throw IllegalArgumentException if maxTime <= 0.");

        final int maxTime2 = -5;
        assertThrows(IllegalArgumentException.class, () -> new Timer(maxTime2, secondsPerYear),
                "Should throw IllegalArgumentException if maxTime <= 0.");
    }

    /**
     * Tests that the constructors throw IllegalArguementException when the
     * secondsPerYear <= 0.
     */
    @Test
    public void illegalSecondsPerYearInit() {
        final int maxTime = 7;
        final int secondsPerYear = 0;
        assertThrows(IllegalArgumentException.class, () -> new Timer(maxTime, secondsPerYear),
                "Should throw IllegalArgumentException if secondsPerYear <= 0.");

        final int secondsPerYear2 = -8;
        assertThrows(IllegalArgumentException.class, () -> new Timer(maxTime, secondsPerYear2),
                "Should throw IllegalArgumentException if secondsPerYear <= 0.");
    }

    /**
     * Test the timers update(float) method. Assumes the getElapsedTime() method
     * works.
     */
    @Test
    public void timerUpdate() {
        Timer timer = new Timer(10, 1);
        assertEquals(0.0f, timer.getElapsedTime(),
                "The timer should have no elapsed time if it has not been updated.");

        float delta = 2.0f;
        timer.update(delta);
        assertEquals(delta, timer.getElapsedTime(),
                "The timer elapsed time should have updated to 2.0f.");

        float delta2 = 2.5f;
        timer.update(delta2);
        assertEquals(delta + delta2, timer.getElapsedTime(),
                "The timer elapsed time should have updated to 4.5f.");
    }

    /**
     * Test the timers hasEnded() method and the elapsed time after the timer
     * reaches its max time. Assumes constructor and update(float) methods
     * work.
     */
    @Test
    public void onTimerEnding() {
        int maxTime = 10;
        int secondsPerYear = 1;
        float beforeMaxTime = 5.0f;
        float onMaxTime = 10.0f;
        float afterMaxTime = 20.0f;

        Timer timer = new Timer(maxTime, secondsPerYear);
        assertEquals(false, timer.hasEnded(),
                "Timer should not have ended as it has not reached maxTime.");

        timer.update(beforeMaxTime);
        assertEquals(false, timer.hasEnded(),
                "Timer should not have ended as it has not reached maxTime.");

        timer = new Timer(maxTime, secondsPerYear);
        timer.update(onMaxTime);
        assertEquals(true, timer.hasEnded(),
                "Timer should have ended after it reaches maxTime");

        timer = new Timer(maxTime, secondsPerYear);
        timer.update(afterMaxTime);
        assertEquals(true, timer.hasEnded(),
                "Timer should have ended after it goes over maxTime.");
        assertEquals(maxTime, timer.getElapsedTime(),
                "The elapsed time of the timer should have of max value of maxTime.");
    }

    @Test
    public void timerDoesntUpdateAfterEnding() {
        int maxTime = 10;
        int secondsPerYear = 1;
        int biggerThanMaxTime = 11;

        Timer timer = new Timer(maxTime, secondsPerYear);
        timer.update(maxTime);
        assertEquals(true, timer.hasEnded(),
                "Timer should have ended after it reaches its maxTime.");

        timer.update(biggerThanMaxTime);
        assertEquals(maxTime, timer.getElapsedTime(),
                "Timer should not have an elapsed time greater than maxTime.");
    }

    /**
     * Tests the getDayCount() method when not going past a year.
     */
    @Test
    public void dayCount() {
        int maxTime = 10_000;
        int secondsPerYear = 365;
        float day = 1.0f;
        float week = 7.0f;

        Timer timer = new Timer(maxTime, secondsPerYear);
        assertEquals(1, timer.getDayCount(),
                "Day count should be 1 as timer has not updated.");

        timer.update(day);
        assertEquals((int) day + 1.0f, timer.getDayCount(),
                "Day count should be 2 as a day has passed.");

        timer = new Timer(maxTime, secondsPerYear);
        timer.update(week);
        assertEquals(week + 1.0f, timer.getDayCount(),
                "Day count should be 8 as 1 week has passed.");
    }

    /**
     * Tests the getDayCount() method when going past a year.
     */
    @Test
    public void dayCountAfterYear() {
        int maxTime = 10_000;
        int secondsPerYear = 365;
        float year = 365.0f;
        float decade = 3650.0f;

        Timer timer = new Timer(maxTime, secondsPerYear);
        timer.update(year);
        assertEquals(1.0f, timer.getDayCount(),
                "Day count should be 1 after 1 year has passed as it resets.");

        timer.update(decade);
        assertEquals(1.0f, timer.getDayCount(),
                "Day count should be 1 after 1 decade has passed as it resets.");
    }

    /**
     * Tests the getYearCount() method. Assumes that update(float) and
     * getYearCount() already work.
     */
    @Test
    public void yearCount() {
        int maxTime = 10_000;
        int secondsPerYear = 10;
        float year = 10.0f;
        float decade = 100.0f;

        Timer timer = new Timer(maxTime, secondsPerYear);
        assertEquals(1, timer.getYearCount(),
                "Year count should start as 1.");

        timer.update(year);
        assertEquals(2, timer.getYearCount(),
                "Year count should be 2 after a year has passed.");

        timer = new Timer(maxTime, secondsPerYear);
        timer.update(decade);
        assertEquals(11, timer.getYearCount(),
                "Year count should be 11 after a decade has passed.");
    }
}
