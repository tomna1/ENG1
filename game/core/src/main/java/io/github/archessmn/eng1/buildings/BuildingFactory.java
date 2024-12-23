package io.github.archessmn.eng1.buildings;

import io.github.archessmn.eng1.World;

public class BuildingFactory {
    private World world;

    public BuildingFactory(World world) {
        if (world == null) throw new IllegalArgumentException("World cannot be null");
        this.world = world;
    }

    public Building getBuildingFromType(Building.Type type, int x , int y) {
        if (type == null) return null;
        Building building;
        switch(type) {
            case GYM:
                building = new GymBuilding(world);
                break;
            case HALLS:
                building = new HallsBuilding(world);
                break;
            case LECTURE_HALL:
                building = new LectureHallBuilding(world);
                break;
            case OFFICES:
                building = new OfficeBuilding(world);
                break;
            case PIAZZA:
                building = new PiazzaBuilding(world);
                break;
            default:
                building = new GymBuilding(world);
        }
        building.setX(x);
        building.setY(y);
        return building;
    }
}
