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
    final static int maxBuildingsPlaced = 5;
    
    private HashMap<Building.Use, Integer> buildingUseCounts = new HashMap<>();
    private HashMap<Building.Type, Integer> buildingTypeCounts = new HashMap<>();
    private int totalBuildingsPlaced = 0;

    private ArrayList<AbstractAchievement> onBuildingTypeCountAchs = new ArrayList<>();

    public WorldStats() {
        for (Building.Use use : Building.Use.values()) {
            buildingUseCounts.put(use, 0);
        }

        for (Building.Type type : Building.Type.values()) {
            buildingTypeCounts.put(type, 0);
        }
    }

    public int incrementBuildingUseCount(Building.Use use) {
        int next = buildingUseCounts.get(use) + 1;
        buildingUseCounts.put(use, next);
        return next;
    }

    public int incrementBuildingTypeCount(Building.Type type) {
        int next = buildingTypeCounts.get(type) + 1;
        buildingTypeCounts.put(type, next);
        for (int i = 0; i < onBuildingTypeCountAchs.size(); i++) {
            boolean isAchieved = onBuildingTypeCountAchs.get(i).checkIfAchieved();
            
        }
        return next;
    }
}
