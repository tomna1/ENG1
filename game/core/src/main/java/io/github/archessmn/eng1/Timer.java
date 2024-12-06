package io.github.archessmn.eng1;

public class Timer {
    float elapsedTime = 0.0f;
    int maxTime;
    int secondsPerYear;

    /**
     * Creates a new timer with the specified parameters. This timer will track the
     * amount of seconds passed and will also be able to get a count for the day and
     * year based on the {@link #secondsPerYear} passed into the constructor.
     * 
     * @param maxTime The maximum amount of time a timer can go for in seconds. Must be
     * greater than 0.
     * @param secondsPerYear The amount of seconds per year. Must be greater than 0.
     */
    public Timer(int maxTime, int secondsPerYear) {
        if (maxTime <= 0) throw new IllegalArgumentException("maxTime should be positive.");
        if (secondsPerYear <=  0) throw new IllegalArgumentException("secondsPeryear should be positive.");
        this.maxTime = maxTime;
        this.secondsPerYear = secondsPerYear;
    }

    /**
     * Returns the amount of time passed in seconds. This value will never be
     * bigger than {@link #maxTime}.
     * @return Seconds passed. e.g. 16.27 = 16.27 seconds.
     */
    public float getElapsedTime() {
        return elapsedTime;
    }

    /**
     * Returns the maximum time the timer can go up to defined in the constructor.
     * @return maxTime.
     */
    public int getMaxTime() {
        return maxTime;
    }

    /**
     * Returns the secondsPerYear as defined in the constructor.
     * @return secondsPerYear.
     */
    public int getSecondsPerYear() {
        return secondsPerYear;
    }

    /**
     * Returns what year it would be based on the elapsed time and the
     * {@link #secondsPerYear}.
     * @return Minimum 1.
     */
    public int getYearCount() {
        return (int)(elapsedTime / secondsPerYear) + 1;
    }

    /**
     * Returns what day of the year it would be based on the elapsed time and the
     * {@link #secondsPerYear}.
     * @return Day of year, min 1, max 365.
     */
    public int getDayCount() {
        return (int)((elapsedTime % secondsPerYear) * (365/secondsPerYear) + 1);
    }

    /**
     * Adds to the elapsed time based on the value passed in.
     * @param delta The amount of time to add to the elapsed time.
     */
    public void update(float delta) {
        if (this.hasEnded()) return;
        elapsedTime += delta;
        if (elapsedTime > maxTime) elapsedTime = maxTime;
    }

    /**
     * Checks if the timer has ended based on the elapsed time and the 
     * {@link #maxTime} value passed into the constructor.
     * @return true if timer has reached max time and false otherwise.
     */
    public boolean hasEnded() {
        if (elapsedTime >= (float)maxTime) {
            return true;
        }
        return false;
    }
}
