package io.github.archessmn.eng1.leaderboard;

import java.io.FileNotFoundException;
import java.util.Arrays;

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
    private String title;
    private String description;

    /**
     * iconPath is the Filehandle to for the icon used to represent the achievement
     * in the assets folder and descriptions is a short descriptions about how the
     * achievement was earned.
     * @param iconPath Filehandle for the icon. Cannot be null.
     * @param description How the achievement is completed. Can be null.
     */
    public CompletedAchievement(FileHandle iconPath, String title, String description){
        if (iconPath == null) throw new IllegalArgumentException("iconPath cannot be null.");
        // TODO: THIS SHOULD BE CHANGED TO FILENOTFOUNDEXCEPTION.
        if (iconPath.exists() == false) throw new IllegalArgumentException("iconPath for Achievement must exist.");
        if (title == null) throw new IllegalArgumentException("title cannot be null");
        if (title.equals("")) throw new IllegalArgumentException("title cannot be empty");
        this.iconPath = iconPath;
        this.title = title;
        if (description == null) description = "";
        this.description = description;
    }

    public FileHandle getIconPath() {
        return iconPath;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    /**
     * @return Returns the path to the icon then the title then the description separted
     * by a hashtag. If "icons/icon1" was the icon path and "title1" was the
     * title and "description1" was the description then this would return 
     * "icons/icon1#title1#description1"
     */
    public String toLeaderboardString() {
        return (iconPath.path() + "#" + title + "#" + description).strip();
    }

    public static CompletedAchievement fromLeaderboardString(String s) {
        String[] split = s.split("#");
        if (split.length > 3 || split.length == 0) {
            return null;
        }
        
        FileHandle fileHandle = Gdx.files.internal(split[0].strip());

        String title = split[1].strip();

        String description;
        if (split.length == 2) {
            description = "";
        } else {
            description = split[2].strip();  
        }
        CompletedAchievement output = new CompletedAchievement(fileHandle, title, description);
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
