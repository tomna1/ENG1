package io.github.archessmn.eng1;

import java.util.HashMap;
import java.util.Map;

public class SatisfactionStats { 
    private SatisfactionManager manager;
    private float maxSatisfaction = 0.0f;   // The max amount of satisfaction the player has earned
    private float minSatisfaction = 0.0f;   // The min amount of satisfaction the player has earned.
    private float currentSatisfaction = 0.0f;   // The current satisfaction.
    public Map<Float, Float> satisfactionHistory = new HashMap<>(); // Time as key and satisfaction as value.
    
    public SatisfactionStats(SatisfactionManager satisfactionManager) {
        if (satisfactionManager == null) throw new IllegalArgumentException("satisfaction manager cannot be null");
        this.manager = satisfactionManager;
    }

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

    public float getMaxSatisfaction() {
        return maxSatisfaction;
    }

    public float getMinSatisfaction() {
        return minSatisfaction;
    }

    public float getCurrentSatisfaction() {
        return currentSatisfaction;
    }

    public float getPercentageSatisfaction() {
        return manager.getPercentageSatisfaction();
    }

    public float getMaxPossibleSatisfaction() {
        return manager.getOptimumSatisfaction();
    }

    public float timeSinceGreaterThan(float satisfaction) {
        return 0.0f;
    } 
}
