package io.github.archessmn.eng1;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to track all of the stats that the achievements may need
 * for satisfaction.
 */
public class SatisfactionStats { 
    private SatisfactionManager manager;
    private float maxSatisfaction = 0.0f;   // The max amount of satisfaction the player has earned
    private float minSatisfaction = 0.0f;   // The min amount of satisfaction the player has earned.
    private float currentSatisfaction = 0.0f;   // The current satisfaction.
    public Map<Float, Float> satisfactionHistory = new HashMap<>(); // Time as key and satisfaction as value.
    
    /**
     * Creates a new satisfaction manager with default stats.
     * @param satisfactionManager
     */
    public SatisfactionStats(SatisfactionManager satisfactionManager) {
        if (satisfactionManager == null) throw new IllegalArgumentException("satisfaction manager cannot be null");
        this.manager = satisfactionManager;
    }

    /**
     * Will add the satisfaction value to the satisfaction history and check if
     * it the the min/max value seen so far. Will also update {@link #currentSatisfaction}
     * to this value.
     * @param satisfaction The current satisfaction value.
     * @param currentTime Time since game started.
     */
    public void addSatisfaction(float satisfaction, float currentTime) {
        if (satisfaction > maxSatisfaction) maxSatisfaction = satisfaction;
        else if (satisfaction < minSatisfaction) minSatisfaction = satisfaction;
        
        if (satisfaction != currentSatisfaction)
        // satisfactionHistory.put(currentTime, satisfaction);
        currentSatisfaction = satisfaction;
    }

    private void removeOldSatisfactionHistory(float newTime) {
        for (float f : satisfactionHistory.keySet()) {
            if (newTime - f > 30) {
                satisfactionHistory.remove(f);
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
     * Returns the amount of time that the satisfaction has been greater than
     * the supplied value.
     * @param satisfaction The value that the satisfaction must be higher than
     * or equal to.
     * @return Time, 1.0 = 1 second.
     */
    public float timeSinceGreaterThan(float satisfaction) {
        return 0.0f;
    } 
}
