package io.github.archessmn.eng1.satisfaction;

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

        satisfactionContribution = calculateConnectionsContribution(false) + calculateOccupancyContribution(false);
        optimumSatisfactionContribution = calculateConnectionsContribution(true) + calculateOccupancyContribution(true);
    }

    private Float connectionContributionEquation(Float weight, Integer distance){
        return 1 / ((weight * distance) + 1);
    }

    private Float calculateConnectionContribution(Building.Type type, boolean isOptimum){

        Integer closestConnectionDistance = building.getClosestConnectedBuildingDistanceOfType(type);
        Float weight = building.getConnections().get(type);

        if(closestConnectionDistance != Integer.MAX_VALUE){
            closestConnectionDistance = isOptimum ? 1 : closestConnectionDistance;
            return connectionContributionEquation(weight, closestConnectionDistance);
        } else{
            return 0.0f;
        }
    }

    private Float calculateConnectionsContribution(boolean isOptimum){

        Float contribution = 0f;
        for(Building.Type type : building.getConnections().keySet()){
            contribution += calculateConnectionContribution(type, isOptimum);
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

        Float contribution = studentContribution + teacherContribution;

        if (isOptimum) {
            optimumOccupancyContribution = contribution;
            return optimumOccupancyContribution;
        } else {
            occupancyContribution = contribution;
            return occupancyContribution;
        }
    }

    private float occupancyEquation(float wight, float occupancy){
        double e = Math.E;
        return (float) Math.pow(e, -wight * Math.pow(occupancy - 1, 2));
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
            contribution = occupancyEquation(occupancyWeighting, occupancy);
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
