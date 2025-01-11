package io.github.archessmn.eng1.achievements;

import io.github.archessmn.eng1.leaderboard.CompletedAchievement;

public abstract class AbstractAchievement {
    private int id;     // Used by the manager to add and remove it from arrays.
    private String title;
    private String description;
    private String iconPath;
    protected AchievementManager manager;
    private boolean onlyCheckAtEnd;
    
    public AbstractAchievement(AchievementManager manager, int id, String title, String description, String iconPath, boolean onlyCheckAtEnd) {
        if (manager == null) throw new IllegalArgumentException("Manager cannot be null");
        if (title == null) throw new IllegalArgumentException("achievement cannot be null");
        if (title.equals("")) throw new IllegalArgumentException("title cannot be empty");
        if (description == null) throw new IllegalArgumentException("decription cannot be null");
        if (description.equals("")) throw new IllegalArgumentException("descriptions cannot be empty");
        if (iconPath == null) throw new IllegalArgumentException("iconPath cannot be null.");
        if (iconPath.equals("")) throw new IllegalArgumentException("iconPath cannot be empty");
        
        this.manager = manager;
        this.id = id;
        this.title = title;
        this.description = description;
        this.iconPath = iconPath;
        this.onlyCheckAtEnd = onlyCheckAtEnd;
    }

    public int getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getIconPath() {
        return iconPath;
    }

    public boolean getOnlyCheckAtEnd() {
        return onlyCheckAtEnd;
    }
    
    public abstract boolean checkIfAchieved();

    public CompletedAchievement getCompletedAchievement() {
        return new CompletedAchievement(iconPath, title, description);
    }

    /**
     * This method should be called when the achievement is completed.
     */
    protected void onCompleted() {
        manager.onAchievementCompletion(this);
    }
}
