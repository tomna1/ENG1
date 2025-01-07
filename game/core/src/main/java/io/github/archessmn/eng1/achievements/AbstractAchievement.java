package io.github.archessmn.eng1.achievements;

public abstract class AbstractAchievement {
    private int id;     // Used by the manager to add and remove it from arrays.
    private String name;
    private String description;
    protected AchievementManager manager;
    
    public AbstractAchievement(AchievementManager manager, int id, String name, String description) {
        if (manager == null) throw new IllegalArgumentException("Manager cannot be null");
        if (name == null) throw new IllegalArgumentException("achievement cannot be null");
        if (description == null) throw new IllegalArgumentException("decription cannot be null");
        this.manager = manager;
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    
    public abstract boolean checkIfAchieved();

    // public abstract CompletedAchievement getCompletedAchievement();

    protected void onCompleted() {
        manager.onAchievementCompletion(this);
    }
}
