package info.Parkhomenko.personaldiary.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import info.Parkhomenko.personaldiary.data.model.Diary;
import info.Parkhomenko.personaldiary.data.repository.DiaryRepository;

public class DiaryViewModel extends AndroidViewModel {
    private final DiaryRepository diaryRepository;
    private final Context context;

    public DiaryViewModel(@NonNull Application application) {
        super(application);
        this.context = application;
        diaryRepository = new DiaryRepository(context);
    }
    public Long insert(Diary diary) {
        return diaryRepository.insertData(diary);
    }
    public LiveData<List<Diary>> getDiariesLiveData() {
        return diaryRepository.selectAllData();
    }

    public Integer delete(Diary diary) {
        return diaryRepository.delete(diary);
    }
    public Integer update(Diary diary) {
        return diaryRepository.update(diary);
    }
    public Integer deleteAll(){
        return diaryRepository.deleteAll();
   }

}
//end

