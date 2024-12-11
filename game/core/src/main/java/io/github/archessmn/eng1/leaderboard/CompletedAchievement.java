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
    public CompletedAchievement(FileHandle iconPath, String description){
        if (iconPath == null) throw new IllegalArgumentException("iconPath cannot be null.");
        // TODO: THIS SHOULD BE CHANGED TO FILENOTFOUNDEXCEPTION.
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

    /**
     * @return Returns the path to the icon and then the description separted
     * by a hashtag. If "icons/icon1" was the icon path and "example1" was the
     * description then this would return "icons/icon1#example1"
     */
    @Override
    public String toString() {
        return (iconPath.path() + "#" + description).strip();
    }

    public static CompletedAchievement fromString(String s) {
        String[] split = s.split("#");
        if (split.length > 2 || split.length == 0) {
            return null;
        }
        
        FileHandle fileHandle = Gdx.files.internal(split[0].strip());

        String description;
        if (split.length == 1) {
            description = "";
        } else {
            description = split[1].strip();  
        }
        CompletedAchievement output = new CompletedAchievement(fileHandle, description);
        return output;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CompletedAchievement == false) return false;
        CompletedAchievement rhs = (CompletedAchievement)o;
        if ((this.iconPath.equals(rhs.iconPath)) && (this.description.equals(rhs.description))) return true;
        return false;
    }
}
