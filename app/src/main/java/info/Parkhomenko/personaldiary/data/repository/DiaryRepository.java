package info.Parkhomenko.personaldiary.data.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import info.Parkhomenko.personaldiary.data.model.Diary;
import info.Parkhomenko.personaldiary.data.database.MyRoomDB;
import info.Parkhomenko.personaldiary.data.dao.DiaryDAO;

public class DiaryRepository {

    private static DiaryDAO diaryDAO;

    public DiaryRepository(Context context) {
        MyRoomDB database = MyRoomDB.getInstance(context);
        diaryDAO = database.diaryDAO();
    }

    static class SelectAllDataTask extends AsyncTask<Void, Void, LiveData<List<Diary>>> {
        @Override
        protected LiveData<List<Diary>> doInBackground(Void... voids) {
            return diaryDAO.selectAll();
        }
    }
    public LiveData<List<Diary>> selectAllData() {
        try {
            return new SelectAllDataTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    static class InsertTask extends AsyncTask<Diary, Void, Long> {
        @Override
        protected Long doInBackground(Diary... diaries) {
            return diaryDAO.insert(diaries[0]);
        }
    }
    public Long insertData(Diary diary) {
        try {
            return new InsertTask().execute(diary).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    static class UpdateTask extends AsyncTask<Diary, Void, Integer> {
        @Override
        protected Integer doInBackground(Diary... diaries) {
            return diaryDAO.update(diaries[0]);
        }
    }
    public Integer update(Diary diary) {
        try {
            return new UpdateTask().execute(diary).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    static class DeleteTask extends AsyncTask<Diary, Void, Integer> {
        @Override
        protected Integer doInBackground(Diary... diaries) {
            return diaryDAO.delete(diaries[0]);
        }
    }
    public Integer delete(Diary diary) {
        try {
            return new DeleteTask().execute(diary).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    static class DeleteAllTask extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... voids) {
            return diaryDAO.deleteAll();
        }
    }
    public Integer deleteAll() {
        try {
            return new DeleteAllTask().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
//end