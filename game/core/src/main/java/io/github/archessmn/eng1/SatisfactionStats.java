package io.github.archessmn.eng1;

import java.util.ArrayList;

/**
 * This class is used to track all of the stats that the achievements may need
 * for satisfaction.
 */
public class SatisfactionStats { 
    private class SatisfactionStat {
        public float time;
        public float satisfaction;
        public float satisfactionPercent;

        private SatisfactionStat(float time, float satisfaction, float satisfactionPercent) {
            this.satisfaction = satisfaction;
            this.satisfactionPercent = satisfactionPercent;
        }
    }

    private static final float HISTORY_TIME_LENGTH = 180.0f;
    
    private SatisfactionManager manager;
    private Timer timer;
    private float maxSatisfaction = 0.0f;   // The max amount of satisfaction the player has earned
    private float minSatisfaction = 0.0f;   // The min amount of satisfaction the player has earned.
    private float currentSatisfaction = 0.0f;   // The current satisfaction.
    private ArrayList<SatisfactionStat> satisfactionHistory = new ArrayList<>();
    
    public SatisfactionStats(SatisfactionManager satisfactionManager, Timer timer) {
        if (satisfactionManager == null) throw new IllegalArgumentException("satisfaction manager cannot be null");
        if (timer == null) throw new IllegalArgumentException("timer cannot be null");
        this.manager = satisfactionManager;
        this.timer = timer;
    }

    /**
     * Adds the associated satisfaction and satifaction percent to the satisfaction
     * history and changes the {@link #minSatisfaction} and {@link #maxSatisfaction}
     * values accordingly.
     * @param satisfaction The raw satisfaction value.
     * @param satisfactionPercentage The satisfaction percent, (0-100).
     */
    public void addSatisfaction(float satisfaction, float satisfactionPercentage) {
        if (satisfaction > maxSatisfaction) maxSatisfaction = satisfaction;
        else if (satisfaction < minSatisfaction) minSatisfaction = satisfaction;

        removeOldSatisfactionHistory();
        
        if (satisfaction != currentSatisfaction)
        satisfactionHistory.add(new SatisfactionStat(timer.getElapsedTime(), satisfaction, satisfactionPercentage));
        currentSatisfaction = satisfaction;
    }

    private void removeOldSatisfactionHistory() {
        float currentTime = timer.getElapsedTime();
        SatisfactionStat stat;
        while (satisfactionHistory.size() > 0) {
            stat = satisfactionHistory.get(0);
            if (currentTime - stat.time > HISTORY_TIME_LENGTH) {
                satisfactionHistory.remove(0);
            } else {
                return;
            }
        }
    }

    /**
     * Returns the highest satisfaction value that has been recorded.
     * @return Max satisfaction value.
     */
    public float getMaxSatisfaction() {
        return maxSatisfaction;
    }

    /**
     * Returns the lowest satisfaction value that has been recorded.
     * @return Min satisfaction value.
     */
    public float getMinSatisfaction() {
        return minSatisfaction;
    }

    /**
     * Returns the current satisfaction value. This is also the same as the last
     * satisfaction value that has been recorded.
     * @return Current satisfaction value.
     */
    public float getCurrentSatisfaction() {
        return currentSatisfaction;
    }

    /**
     * Returns the satisfaction value as a percentage of the maximum possible 
     * satisfaction value.
     * @return Percentage satisfaction. 0-100 inclusively.
     */
    public float getPercentageSatisfaction() {
        return manager.getPercentageSatisfaction();
    }

    /**
     * Returns the maximum possible satisfaction value.
     * @return Max possible satisfaction.
     */
    public float getMaxPossibleSatisfaction() {
        return manager.getOptimumSatisfaction();
    }

    /**
     * Returns the amount of time time that the satisfaction history percentage
     * has been higher than the input arg.
     * @param satisfactionPercent The satisfaction percent that the satisfaction
     * history should be higher than or equal to. Cannot be lower than 0 or
     * higher than 100.
     * @param maxTime The maximum time that the method should check that the 
     * satisfaction history for. Cannot be lower than 0 or higher than 180.
     * @return The amount of time satisfaction history percent has been higher
     * than satisfactionPercent
     */
    public float timeSinceGreaterThanPercent(float satisfactionPercent, int maxTime) {
        if (satisfactionPercent < 0.0f || satisfactionPercent > 100.0f) {
            throw new IllegalArgumentException("satisfactionPercent cant be lower than 0 or higher than 100.");
        }
        if (maxTime > HISTORY_TIME_LENGTH) {
            throw new IllegalArgumentException("maxTime cannot be greater than " + Float.toString(HISTORY_TIME_LENGTH));
        }
        if (maxTime < 0.0f) {
            throw new IllegalArgumentException("maxTime cannot be lower than 0.0f");
        }

        float time = 0.0f; // The output, how long satisfaction has been over percent.
        float lastTimeValue = timer.getElapsedTime(); // The time value of the previous satisfaction value,
        int index = satisfactionHistory.size() - 1;
        if (index == -1 | index == 0) {
            return 0.0f;
        }

        SatisfactionStat stat;
        while (index > 0 && time < maxTime) {
            stat = satisfactionHistory.get(index);
            if (stat.satisfactionPercent < satisfactionPercent) return time;
            time += lastTimeValue - stat.time;
            lastTimeValue = stat.time;
            index--;
        }
        // This is reached if no stat has a lower percent that the specified percent.
        if (time > maxTime) time = maxTime;
        return time;
    } 
}
