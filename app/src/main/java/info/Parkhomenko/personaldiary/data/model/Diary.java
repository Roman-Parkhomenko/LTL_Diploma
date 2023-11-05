package info.Parkhomenko.personaldiary.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "PersonalDiaryTB")
public class Diary implements Serializable {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "timeOfDay")
    private String timeOfDay;
    @ColumnInfo(name = "category")
    private String category;
    @ColumnInfo(name = "dificulty")
    private int dificulty;
    @ColumnInfo(name = "userID")
    private String userID;


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public int getDificulty() {
        return dificulty;
    }
    public void setDificulty(int dificulty) {
        this.dificulty = dificulty;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return getTitle();
    }

    public String getUserID() { return userID; }

    public void setUserID(String userID) { this.userID = userID; }
}
//end

