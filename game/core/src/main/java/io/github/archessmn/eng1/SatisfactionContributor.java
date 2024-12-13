package io.github.archessmn.eng1;

import io.github.archessmn.eng1.buildings.Building;

public class SatisfactionContributor {
    
    private Building building;
    private Float studentCapacity, teacherCapacity;
    private Float studentOccupancyWeighting, teacherOccupancyWeighting;

    private Float connectionsContribution = 0f, optimumConnectionsContribution = 0f;
    private Float occupancyContribution = 0f, optimumOccupancyContribution = 0f;
    private Float satisfactionContribution = 0f, optimumSatisfactionContribution = 0f;

    public SatisfactionContributor(
        Building building, 
        Float studentCapacity, 
        Float teacherCapacity,
        Float studentOccupancyWeighting, 
        Float teacherOccupancyWeighting
    ) {
        this.building = building;
        this.studentCapacity = studentCapacity;
        this.teacherCapacity = teacherCapacity;
        this.studentOccupancyWeighting = studentOccupancyWeighting;
        this.teacherOccupancyWeighting = teacherOccupancyWeighting;
    }

    public void updateSatisfactionContribution() {
        satisfactionContribution = calculateConnectionContribution(false) + calculateOccupancyContribution(false);
        optimumSatisfactionContribution = calculateConnectionContribution(true) + calculateOccupancyContribution(true);
    }

    private Float connectionContributionEquation(Float weight, Integer distance){
        Float distanceFactor = 1.5f;
        if(weight == 0){
            return 0f;
        }
        return weight / (distance * distanceFactor);
    }

    private Float calculateConnectionContribution(boolean isOptimum) {

        Float contribution = 0f;
        for(Building connectedBuilding : building.getConnectedBuildings()){
            Float weight = building.getConnectionWeight(connectedBuilding);
            Integer distance = isOptimum ? 1 : building.getManhattenDistanceFrom(connectedBuilding);
            contribution += connectionContributionEquation(weight, distance);
           // System.out.println(String.format("Building: %-10s Connected: %-10s Distance: %-10d Weight: %-10f Contribution: %f", building.getBuildingType().name(), connectedBuilding.getBuildingType().name(), distance, weight, contribution));
        }


        if (isOptimum) {
            optimumConnectionsContribution = contribution;
            return optimumConnectionsContribution;
        } else {
            
            connectionsContribution = contribution;
            return connectionsContribution;
        }

    }


    private Float calculateOccupancyContribution(boolean isOptimum) {

        Float studentContribution = getOccupancyContribution(Building.Type.HALLS, isOptimum);
        Float teacherContribution = getOccupancyContribution(Building.Type.OFFICES, isOptimum);
        //System.out.println("type: " + building.getBuildingType().name() + " studentOccupacy: " + studentOccupacy + " teacherOccupancy: " + teacherOccupancy + " optimum: " + isOptimum);
        //System.out.println("studentContribution: " + studentContribution + " teacherContribution: " + teacherContribution + "\n");

        Float contribution = studentContribution + teacherContribution;

        if (isOptimum) {
            optimumOccupancyContribution = contribution;
            return optimumOccupancyContribution;
        } else {
            occupancyContribution = contribution;
            return occupancyContribution;
        }
    }

    private Float getOccupancyContribution(Building.Type type, boolean isOptimum) {

        Float contribution = 0f;
        Float occupancy = 0f;
        Float capacity = getCapacityFromType(type);
        Float ratio = building.getWorld().ratioToType(building.getBuildingType(), type);
        Float occupancyWeighting = getOccupancyWeightFromType(type);

        if(capacity == 0 || ratio == null){
            return 0.0f;
        }
        else{
            occupancy = isOptimum ? 1.0f : getOccupancy(capacity, ratio);
            contribution = 1 - occupancyWeighting * Math.abs(occupancy - 1);
            return contribution;

        }
    }

    private Float getOccupancy(Float capcity, Float ratio){
        return capcity / ratio;
    }

    private Float getCapacityFromType(Building.Type type){

        return switch(type){
            case HALLS -> studentCapacity;
            case OFFICES -> teacherCapacity;
            default -> null;
        };
    }

    private Float getOccupancyWeightFromType(Building.Type type){
        return switch(type){
            case HALLS -> studentOccupancyWeighting;
            case OFFICES -> teacherOccupancyWeighting;
            default -> null;
        };
    }

    public Float getSatisfactionContribution() {
        return satisfactionContribution;
    }

    public Float getOptimumSatisfactionContribution() {
        return optimumSatisfactionContribution;
    }
}
