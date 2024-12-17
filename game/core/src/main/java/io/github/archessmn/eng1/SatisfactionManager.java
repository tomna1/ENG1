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
        Float previousSatisfaction = satisfaction;
        Float previousOptimumSatisfaction = optimumSatisfaction;
        Float previousPercentageSatisfaction = getPercentageSatisfaction();
        satisfaction = 0f;
        optimumSatisfaction = 0f;
        for(Building building : world.getBuildings()){
            SatisfactionContributor satisfactionContributor = building.getSatisfactionContributor();
            satisfactionContributor.updateSatisfactionContribution();

            satisfaction += satisfactionContributor.getSatisfactionContribution();
            optimumSatisfaction += satisfactionContributor.getOptimumSatisfactionContribution();
        }

        Float satisfactionDifference = satisfaction - previousSatisfaction;
        Float optimumSatisfactionDifference = optimumSatisfaction - previousOptimumSatisfaction;
        Float percentageDifference = getPercentageSatisfaction() - previousPercentageSatisfaction;

        System.out.println(String.format("Satisfaction: %-10.3f Optimum: %-10.3f Percentage: %.3f", satisfaction, optimumSatisfaction, getPercentageSatisfaction()));
        System.out.println(String.format("Average: %-10.5f Average Optimum: %.5f", getAvergageSatisfactionContribution(), getAverageOptimumContribution()));
        System.out.println(String.format("Satisfaction Dif: %-10.5f Optimum Dif: %-10.5f Percentage Dif: %f", satisfactionDifference, optimumSatisfactionDifference, percentageDifference));
        System.out.println("\n");
    }

    public Float getAvergageSatisfactionContribution(){
        return satisfaction / world.getBuildings().size;
    }

    public Float getAverageOptimumContribution(){
        return optimumSatisfaction / world.getBuildings().size;
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
