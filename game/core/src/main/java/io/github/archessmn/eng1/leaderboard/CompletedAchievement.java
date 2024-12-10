package io.github.archessmn.eng1.leaderboard;

import com.badlogic.gdx.files.FileHandle;

/**
 * This class is used to store Achievements that were completed during the
 * game and can be stored into the leaderboard file. This file is only for logica
 * purposes. The UI of this class is {@link CompletedAchievementIcon}.
 */
public class CompletedAchievement {
    // The file path to the icon used to represents the achievement in
    // the assets folder;
    private FileHandle iconPath;
    // A description of the requirements needed to earn this achievement.
    private String description;

    /**
     * iconPath is the Filehandle to for the icon used to represent the achievement
     * in the assets folder and descriptions is a short descriptions about how the
     * achievement was earned.
     * @param iconPath Filehandle for the icon. Cannot be null.
     * @param description How the achievement is completed. Can be null.
     */
    public CompletedAchievement(FileHandle iconPath, String description) {
        if (iconPath == null) throw new IllegalArgumentException("iconPath cannot be null.");
        if (iconPath.exists() == false) throw new IllegalArgumentException("iconPath for Achievement must exist.");
        this.iconPath = iconPath;
        if (description == null) description = "";
        this.description = description;
    }

    public FileHandle getIconPath() {
        return iconPath;
    }

    public String getDescription() {
        return description;
    }
}
