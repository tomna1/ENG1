package io.github.archessmn.eng1.achievements;

public class IHeartUniAchievement extends AbstractAchievement {
    public IHeartUniAchievement(AchievementManager manager, int id) {
        super(manager, id, 
        "I heart Uni",
        "Earned by maintaining student satisfaction for over 3 minutes.");
    }

    public boolean checkIfAchieved() {
        return true;
    }
}
