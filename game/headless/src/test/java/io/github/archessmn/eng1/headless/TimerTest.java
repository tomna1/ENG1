package io.github.archessmn.eng1.headless;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.github.archessmn.eng1.Timer;

public class TimerTest {
    @Test
    public void timerInitTest() {
        Timer timer = new Timer(300, 60);
        assertEquals(0.0f, timer.getElapsedTime(),
            "The timer should have no elapsed time if it has not been updated.");
    }
}
