package io.github.archessmn.eng1;

import java.util.ArrayList;
import java.util.HashMap;

import io.github.archessmn.eng1.achievements.AbstractAchievement;
import io.github.archessmn.eng1.buildings.Building;

/**
 * Contains all stats that pertains to the world. Used by achievements to check
 * if they have been completed.
 */
public class WorldStats {
    public final static int maxPossibleBuildings = 10;
    
    private HashMap<Building.Use, Integer> buildingUseCounts = new HashMap<>();
    private HashMap<Building.Type, Integer> buildingTypeCounts = new HashMap<>();
    private int totalBuildingsPlaced = 0;

    private ArrayList<AbstractAchievement> onBuildingCountAchs = new ArrayList<>(); // Achievement that rely on building count.
    private ArrayList<AbstractAchievement> onBuildingTypeCountAchs = new ArrayList<>(); // Achievement that rely on building type.
    private ArrayList<AbstractAchievement> onBuildingUseCountAchs = new ArrayList<>(); // Achievement that rely on building use.

    /**
     * Creates new world stats. Initially the {@link #totalBuildingsPlaced} = 0
     * and the use and type counts of all buildings are 0.
     */
    public WorldStats() {
        for (Building.Use use : Building.Use.values()) {
            buildingUseCounts.put(use, 0);
        }

        for (Building.Type type : Building.Type.values()) {
            buildingTypeCounts.put(type, 0);
        }
    }

    /**
     * Updates the building use count, building type count and total building count
     * based on the building placed.
     * @param building The building placed.
     */
    public void onBuildingBuilt(Building building) {
        incrementBuildingTypeCount(building.getBuildingType());
        incrementBuildingUseCount(building.getBuildingUse());

        for (int i = 0; i < onBuildingCountAchs.size(); i++) {
            boolean isAchieved = onBuildingCountAchs.get(i).checkIfAchieved();
            if (isAchieved) {
                onBuildingCountAchs.remove(i);
                i--;
            }
        }

        totalBuildingsPlaced++;
    }

    private int incrementBuildingUseCount(Building.Use use) {
        int next = buildingUseCounts.get(use) + 1;
        buildingUseCounts.put(use, next);

        for (int i = 0; i < onBuildingUseCountAchs.size(); i++) {
            boolean isAchieved = onBuildingUseCountAchs.get(i).checkIfAchieved();
            if (isAchieved) {
                onBuildingUseCountAchs.remove(i);
                i--;
            }
        }

        return next;
    }

    private int incrementBuildingTypeCount(Building.Type type) {
        int next = buildingTypeCounts.get(type) + 1;
        buildingTypeCounts.put(type, next);

        for (int i = 0; i < onBuildingTypeCountAchs.size(); i++) {
            boolean isAchieved = onBuildingTypeCountAchs.get(i).checkIfAchieved();
            if (isAchieved) onBuildingTypeCountAchs.remove(i);
        }

        return next;
    }

    /**
     * Returns the total amount of buildings placed in the world.
     * @return Total buildings placed.
     */
    public int getTotalBuildingsPlaced() {
        return totalBuildingsPlaced;
    }

    /**
     * Returrns the amount of buildings placed with the {@link Building.Use}
     * of the building.
     * @param use The use of the buildings.
     * @return The amounts of buildings placed with that use.
     */
    public int getBuildingsPlaced(Building.Use use) {
        return buildingUseCounts.get(use);
    }

    /**
     * Returns the amount of buildings placed with the specified 
     * {@link Building.Type}.
     * @param type The type of the buildings.
     * @return The amount of buildings placed with that type.
     */
    public int getBuildingsPlaced(Building.Type type) {
        return buildingTypeCounts.get(type);
    }
}
