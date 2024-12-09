package io.github.archessmn.eng1;

import java.util.HashMap;

import io.github.archessmn.eng1.buildings.Building;

public class SatisfactionManager {

    private World world;

    private Float optimumSatisfaction;
    private Float currentSatisfaction;
    public SatisfactionManager(World world){
        this.world = world;
    }

    public Float percentageSatisfaction(){
        return (float) (currentSatisfaction / optimumSatisfaction * 100);
    }

    public void updateCurrentSatisfaction(){

        HashMap<Building, Float> distanceFromOtherBuildings;

        for(Building building : world.getBuildings()){
            distanceFromOtherBuildings = building.getDistanceFromOtherBuildings();

            for(Building otherBuilding : distanceFromOtherBuildings.keySet()){
                Float distance = distanceFromOtherBuildings.get(otherBuilding);
                Float weight = building.getConnections().get(otherBuilding.getBuildingType());
                currentSatisfaction += distance * weight;
            }
        }
    }



    

    

}
