package io.github.archessmn.eng1.leaderboard;

import java.io.FileNotFoundException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * This class is used to store Achievements that were completed during the
 * game and can be stored into the leaderboard file. This file is only for logical
 * purposes.
 */
public class CompletedAchievement {
    // The file path to the icon used to represents the achievement in
    // the assets folder;
    private String iconPath;
    private String title;
    private String description;

    /**
     * iconPath is the Filehandle to for the icon used to represent the achievement
     * in the assets folder and descriptions is a short descriptions about how the
     * achievement was earned.
     * @param iconPath Filehandle for the icon. Cannot be null.
     * @param title Title for the achievement. Cannot be null or empty string.
     * @param description How the achievement is completed. Can be null. If null
     * then description is empty string.
     */
    public CompletedAchievement(String iconPath, String title, String description){
        if (iconPath == null) throw new IllegalArgumentException("iconPath cannot be null.");
        // TODO: THIS SHOULD BE CHANGED TO FILENOTFOUNDEXCEPTION.
        FileHandle path = Gdx.files.internal(iconPath);
        if (path.exists() == false) throw new IllegalArgumentException("iconPath for Achievement must exist.");
        if (title == null) throw new IllegalArgumentException("title cannot be null");
        if (title.equals("")) throw new IllegalArgumentException("title cannot be empty");
        this.iconPath = iconPath;
        this.title = title;
        if (description == null) description = "";
        this.description = description;
    }

    /**
     * Copy Constructor.
     * @param achievement
     */
    public CompletedAchievement(CompletedAchievement achievement) {
        if (achievement == null) throw new IllegalArgumentException("achievement cannot be null");
        this.iconPath = achievement.iconPath;
        this.title = achievement.title;
        this.description = achievement.description;
    }

    // ====SHOULD NOT BE USED, ONLY FOR JSON SERIALISATION====.
    public CompletedAchievement() {
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
     * Returns a filehandle to the path of the icon in the assets folder used
     * to represent the achievement.
     * @return Icon filehandle.
     */
    public FileHandle getIconFile() {
        FileHandle output = Gdx.files.internal(iconPath);
        return output;
    }

    /**
     * Returns the title of the achievement. Wont be null.
     * @return Achievement title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the description of the achievement. Wont be null.
     * @return Achievement description.
     */
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CompletedAchievement == false) return false;
        CompletedAchievement rhs = (CompletedAchievement)o;
        if (
            (this.iconPath.equals(rhs.iconPath)) &&
            (this.description.equals(rhs.description)) &&
            this.title.equals(rhs.title)
        ) return true;
        return false;
    }
}
