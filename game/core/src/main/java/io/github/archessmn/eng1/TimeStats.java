package io.github.archessmn.eng1;

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
