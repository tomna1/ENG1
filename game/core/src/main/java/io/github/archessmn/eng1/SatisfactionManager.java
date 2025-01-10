package io.github.archessmn.eng1;

import io.github.archessmn.eng1.buildings.Building;

public class SatisfactionManager {

    private World world;
    private Float satisfaction = 0f;
    private Float optimumSatisfaction = 0f;
    private Float totalEventSatisfaction = 0f, optimumTotalEventSatisfaction = 0f;
    private SatisfactionStats satisfactionStats;

    public SatisfactionManager(World world) {
        this.world = world;
        satisfaction = 0f;
        satisfactionStats = new SatisfactionStats(this);
    }

    public void updateSatisfaction() {

        satisfaction = 0f;
        optimumSatisfaction = 0f;
        for (Building building : world.getBuildings()) {

            SatisfactionContributor satisfactionContributor = building.getSatisfactionContributor();
            satisfactionContributor.updateSatisfactionContribution();

            satisfaction += satisfactionContributor.getSatisfactionContribution();
            optimumSatisfaction += satisfactionContributor.getOptimumSatisfactionContribution();
        }
        satisfaction += totalEventSatisfaction;
        optimumSatisfaction += optimumTotalEventSatisfaction;
        System.out.println("Satisfaction: " + satisfaction + " Optimum Satisfaction: " + optimumSatisfaction);

        satisfactionStats.addSatisfaction(satisfaction, 0.0f);
    }

    public void updateEventSatisfaction(float eventSatisfaction, float optimumEventSatisfaction) {
        totalEventSatisfaction += eventSatisfaction;
        optimumTotalEventSatisfaction += optimumEventSatisfaction;
    }

    public Float getAvergageSatisfactionContribution() {
        return satisfaction / world.getBuildings().size;
    }

    public Float getAverageOptimumContribution() {
        return optimumSatisfaction / world.getBuildings().size;
    }

    public Float getSatisfaction() {
        return satisfaction;
    }

    public Float getOptimumSatisfaction() {
        return optimumSatisfaction;
    }

    public Float getTotalEventSatisfaction() {
        return totalEventSatisfaction;
    }

    public Float getOptimumTotalEventSatisfaction() {
        return optimumTotalEventSatisfaction;
    }

    public Float getPercentageSatisfaction() {
        return satisfaction / optimumSatisfaction * 100;
    }

    public SatisfactionStats getSatisfactionStats() {
        return satisfactionStats;
    }
}
