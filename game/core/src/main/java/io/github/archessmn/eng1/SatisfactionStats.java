package io.github.archessmn.eng1;

import java.util.HashMap;
import java.util.Map;

public class SatisfactionStats { 
    private float maxPossibleSatisfaction = 100.0f;
    private float maxSatisfaction = 0.0f;
    private float minSatisfaction = 0.0f;
    private float currentSatisfaction = 0.0f;
    public Map<Float, Float> satisfactionHistory = new HashMap<>(); // Time as key and satisfaction as value.
    
    public SatisfactionStats() {

    }

    public void addSatisfaction(float satisfaction, float currentTime) {
        if (satisfaction > maxSatisfaction) maxSatisfaction = satisfaction;
        else if (satisfaction < minSatisfaction) minSatisfaction = satisfaction;
        currentSatisfaction = satisfaction;
        if (satisfaction != currentSatisfaction)
        satisfactionHistory.put(currentTime, satisfaction);
        currentSatisfaction = satisfaction;
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

    public float timeSinceGreaterThan(float satisfaction) {
        return 0.0f;
    } 
}
