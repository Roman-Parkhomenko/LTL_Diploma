package info.Parkhomenko.personaldiary.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import info.Parkhomenko.personaldiary.data.dao.DiaryDAO;
import info.Parkhomenko.personaldiary.data.model.Diary;

@Database(entities = {Diary.class}, version = 4, exportSchema = false)
public abstract class MyRoomDB extends RoomDatabase {

    private static MyRoomDB myRoomDB;

    public abstract DiaryDAO diaryDAO();

    public static MyRoomDB getInstance(Context con) {
        if (myRoomDB == null) {
            myRoomDB = Room.databaseBuilder(con, MyRoomDB.class,
                    "MyRoomDB")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return myRoomDB;
    }
}
//end
