package io.github.archessmn.eng1;

import io.github.archessmn.eng1.buildings.Building;

public class SatisfactionManager {
    
    private World world;
    private Float satisfaction = 0f;
    private Float optimumSatisfaction = 0f;
    public SatisfactionManager(World world){
        this.world = world;
        satisfaction = 0f;
    }

    public void updateSatisfaction(){
        satisfaction = 0f;
        optimumSatisfaction = 0f;
        for(Building building : world.getBuildings()){
            SatisfactionContributor satisfactionContributor = building.getSatisfactionContributor();
            satisfactionContributor.updateSatisfactionContribution();

            satisfaction += satisfactionContributor.getSatisfactionContribution();
            optimumSatisfaction += satisfactionContributor.getOptimumSatisfactionContribution();
        }
        System.out.println(String.format("Satisfaction: %-10.3f Optimum: %-10.3f Percentage: %.3f\n\n", satisfaction, optimumSatisfaction, getPercentageSatisfaction()));
    }

    public Float getSatisfaction(){
        return satisfaction;
    }

    public Float getOptimumSatisfaction(){
        return optimumSatisfaction;
    }

    public Float getPercentageSatisfaction(){
        return satisfaction / optimumSatisfaction * 100;
    }


}
