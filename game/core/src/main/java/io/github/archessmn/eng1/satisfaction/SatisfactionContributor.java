package io.github.archessmn.eng1.satisfaction;

import io.github.archessmn.eng1.buildings.Building;

/**
 * This class calculates the satisfaction contribution of a building using its connections and occupancy.
 * It also calculates the optimum satisfaction contribution of a building.
    */
public class SatisfactionContributor {
    
    // The building that the satisfaction contributor is calculating the satisfaction contribution for.
    private Building building;

    // The capacity of students and teachers for the building.
    private Float studentCapacity, teacherCapacity;

    // These define how important it is for the building to be occupied by students and teachers.
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

    /**
     * Updates the satisfaction contribution and optimum contribution of the building.
     */
    public void updateSatisfactionContribution() {

        satisfactionContribution = calculateConnectionsContribution(false) + calculateOccupancyContribution(false);
        optimumSatisfactionContribution = calculateConnectionsContribution(true) + calculateOccupancyContribution(true);
    }

    /**
     * Calculates the contribution of a connection to the satisfaction of the building.
     * @param weight The weight of the connection.
     * @param distance The distance of the closest connected building.
     * @return The contribution of the connection to the satisfaction of the building.
     */
    private Float connectionContributionEquation(Float weight, Integer distance){
        // The equation defines a gradual decrease in contribution as the distance increases.
        return 1 / ((weight * distance) + 1);
    }

    /**
     * Calculates the contribution of the closest connection of the building.
     * @param type The type of the building of the connection.
     * @param isOptimum Whether to calculate the optimum contribution.
     * @return The contribution of the connection to the satisfaction of the building.
     */
    private Float calculateConnectionContribution(Building.Type type, boolean isOptimum){

        // The distance of the closest connected building of the type.
        Integer closestConnectionDistance = building.getClosestConnectedBuildingDistanceOfType(type);
        Float weight = building.getConnections().get(type);

        // If there is a connection of the type.
        if(closestConnectionDistance != Integer.MAX_VALUE){
            // If the contribution is optimum, the distance is 1.
            closestConnectionDistance = isOptimum ? 1 : closestConnectionDistance;
            return connectionContributionEquation(weight, closestConnectionDistance);
        } else{
            return 0.0f;
        }
    }


    /**
     * Calculates the contribution of all the connections of the building to the satisfaction of the building.
     * @param isOptimum Whether to calculate the optimum contribution.
     * @return The contribution of all the connections to the satisfaction of the building.
     */
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

    /**
     * Calculates the contribution of the occupancy of the building to the satisfaction of the building.
     * @param isOptimum Whether to calculate the optimum contribution.
     * @return The contribution of the occupancy to the satisfaction of the building.
     */
    private Float calculateOccupancyContribution(boolean isOptimum) {

        // The occupancy contribution of the students.
        Float studentContribution = getOccupancyContribution(Building.Type.HALLS, isOptimum);
        // The occupancy contribution of the teachers.
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

    /**
     * Calculates the contribution of the occupancy of the building to the satisfaction of the building.
     * @param weight The weight of the occupancy.
     * @param occupancy The occupancy of the building.
     * @return The contribution of the occupancy to the satisfaction of the building.
     */
    private float occupancyEquation(float weight, float occupancy){
        // The equation describes a bump that peaks at 1 and decreases either side of 1.
        double e = Math.E;
        return (float) Math.pow(e, -weight * Math.pow(occupancy - 1, 2));
    }

    /**
     * Calculates the contribution of the occupancy of a certain type of building to the satisfaction of the building.
     * @param type The type of the building.
     * @param isOptimum Whether to calculate the optimum contribution.
     * @return The contribution of the occupancy to the satisfaction of the building.
     */
    private Float getOccupancyContribution(Building.Type type, boolean isOptimum) {

        Float contribution = 0f;
        Float occupancy = 0f;
        Float capacity = getCapacityFromType(type);
        Float ratio = building.getWorld().ratioToType(building.getBuildingType(), type);
        Float occupancyWeighting = getOccupancyWeightFromType(type);

        // If the capacity is 0 or the ratio is null, the contribution is 0.
        if(capacity == 0 || ratio == null){
            return 0.0f;
        }
        else{
            occupancy = isOptimum ? 1.0f : getOccupancy(capacity, ratio);
            contribution = occupancyEquation(occupancyWeighting, occupancy);
            return contribution;

        }
    }

    /**
     * Gets the occupancy of a building of a certain type.
     * @param type The type of the building.
     * @param isOptimum Whether to calculate the optimum contribution.
     * @return The occupancy of the building.
     */
    private Float getOccupancy(Float capcity, Float ratio){
        return capcity / ratio;
    }

    /**
     * Gets the capacity of a building of a certain type.
     * @param type The type of the building.
     * @return The capacity of the building.
     */
    private Float getCapacityFromType(Building.Type type){

        return switch(type){
            case HALLS -> studentCapacity;
            case OFFICES -> teacherCapacity;
            default -> null;
        };
    }

    /**
     * Gets the occupancy weight of a building of a certain type.
     * @param type The type of the building.
     * @return The occupancy weight of the building.
     */
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
