package io.github.archessmn.eng1;

/**
 * This class is used to manage all the time statistics of the game for 
 * achievements.
 */
public class TimeStats {
    private float totalGameTime = 0.0f;

    public TimeStats() {

    }

    public void update(float deltaTime) {
        totalGameTime += deltaTime;
    }

    public float getTotalGameTime() {
        return totalGameTime;
    }
}
