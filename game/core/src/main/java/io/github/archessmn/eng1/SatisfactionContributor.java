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

    private Float calculateConnectionContribution(boolean isOptimum) {

        Float contribution = 0f;
        for(Building connectedBuilding : building.getConnectedBuildings()){
            Float weight = building.getConnectionWeight(connectedBuilding);
            Integer distance = isOptimum ? 1 : building.getManhattenDistanceFrom(connectedBuilding);

            contribution += weight / distance;
        }

        if (isOptimum) {
            optimumConnectionsContribution += contribution;
            return optimumConnectionsContribution;
        } else {
            connectionsContribution += contribution;
            return connectionsContribution;
        }

    }

    private Float calculateOccupancyContribution(boolean isOptimum) {

        Float studentOccupancy = calculateOccupancy(studentCapacity, isOptimum);
        Float teacherOccupancy = calculateOccupancy(teacherCapacity, isOptimum);

        Float studentContribution = studentOccupancy * studentOccupancyWeighting;
        Float teacherContribution = teacherOccupancy * teacherOccupancyWeighting;

        Float contribution = studentContribution + teacherContribution;

        if (isOptimum) {
            optimumOccupancyContribution = contribution;
            return optimumOccupancyContribution;
        } else {
            occupancyContribution = contribution;
            return occupancyContribution;
        }
    }

    private Float calculateOccupancy(Float capacity, boolean isOptimum) {
        if (capacity == 0) {
            return 0f;
        }
        return isOptimum ? 1.0f : (float) building.getWorld().getStudentCount() / capacity;
    }

    public Float getSatisfactionContribution() {
        return satisfactionContribution;
    }

    public Float getOptimumSatisfactionContribution() {
        return optimumSatisfactionContribution;
    }
}
