package io.github.archessmn.eng1;

import java.util.ArrayList;

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
