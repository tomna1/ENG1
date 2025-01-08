package io.github.archessmn.eng1.achievements;

import io.github.archessmn.eng1.leaderboard.CompletedAchievement;

/**
 * All achievements should inherit from this class.
 */
public abstract class AbstractAchievement {
    private int id;     // Used by the manager to add and remove it from arrays.
    private String title;
    private String description;
    private String iconPath;
    protected AchievementManager manager;
    
    /**
     * Creates a new achievement with the all the specified attributes.
     * @param manager Reference to the manager that manages this achievement. 
     * Cannot be null
     * @param id The unique id of an achievement. Has to be unique in the 
     * {@link AchievementManager}.
     * @param title The title of the achievement. Cannot be null or empty
     * @param description The description of the achievement (how it can be earned).
     * Cannot be null or empty
     * @param iconPath The path to the icon in the assets folder used to represent
     * the achievement. Cannot be null.
     */
    public AbstractAchievement(AchievementManager manager, int id, String title, String description, String iconPath) {
        if (manager == null) throw new IllegalArgumentException("Manager cannot be null");
        if (title == null) throw new IllegalArgumentException("title cannot be null");
        if (title.equals("")) throw new IllegalArgumentException("title cannot be empty");
        if (description == null) throw new IllegalArgumentException("decription cannot be null");
        if (description.equals("")) throw new IllegalArgumentException("description cannot be empty");
        if (iconPath == null) throw new IllegalArgumentException("iconPath cannot be null.");
        this.manager = manager;
        this.id = id;
        this.title = title;
        this.description = description;
        this.iconPath = iconPath;
    }

    /**
     * Returns the unique id of the achievement.
     * @return Id of achievement.
     */
    public int getID() {
        return id;
    }

    /**
     * Returns the title associated with the achievement that was defined in the
     * constructor. Wont be null or empty string.
     * @return Title of achievement.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the description of the achievement that was defined in the constructor.
     * Wont be null or empty string.
     * @return Description of achievement.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the path to the icon in the assets folder used to represent the
     * achievement. Wont be null or empty string.
     * @return Icon path.
     */
    public String getIconPath() {
        return iconPath;
    }

    /**
     * Checks if the achievement has been achieved or not.
     * @return true if has been achieved and false if not.
     */
    public abstract boolean checkIfAchieved();

    /**
     * Returns the {@link CompletedAchievement} form of this achievement used
     * by the leaderboard based on the title, description and icon path defined
     * in the constructor.
     * @return Completed achievement.
     */
    public CompletedAchievement getCompletedAchievement() {
        return new CompletedAchievement(iconPath, title, description);
    }

    /**
     * This method should be called when the achievement is completed. It will
     * call the {@link AchievementManager#onAchievementCompletion(AbstractAchievement)}
     * method with itself as the argument.
     */
    protected void onCompleted() {
        manager.onAchievementCompletion(this);
    }
}
