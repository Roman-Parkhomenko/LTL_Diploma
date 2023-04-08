package info.Parkhomenko.personaldiary.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import info.Parkhomenko.personaldiary.data.model.Diary;

@Dao
public interface DiaryDAO {

    //NB= Methods annotated with @Insert can return either void, long, Long, long[], Long[]
    //or List<Long>.
    @Insert
    long  insert(Diary diary);

    //Update methods must either return void or return int (the number of updated rows).
    @Update
    int update(Diary diary);

    //Deletion methods must either return void or return int (the number of deleted rows).
    @Delete
    int  delete(Diary diary);

    @Query("SELECT * FROM PersonalDiaryTB")
    LiveData<List<Diary>> selectAll();

    @Query("SELECT * FROM PersonalDiaryTB WHERE date LIKE :date")
    LiveData<List<Diary>> selectByDate(String date);

    @Query("SELECT * FROM PersonalDiaryTB WHERE category LIKE :category")
    LiveData<List<Diary>> selectByCategory(String category);

    //NB= Deletion methods must either return void or return int (the number of deleted rows).
    @Query("delete from PersonalDiaryTB")
    int deleteAll();
}
//end