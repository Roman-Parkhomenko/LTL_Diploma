package info.Parkhomenko.personaldiary.data.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.lifecycle.ComputableLiveData;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.InvalidationTracker.Observer;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import info.Parkhomenko.personaldiary.data.model.Diary;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public final class DiaryDAO_Impl implements DiaryDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfDiary;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfDiary;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfDiary;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public DiaryDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDiary = new EntityInsertionAdapter<Diary>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `PersonalDiaryTB`(`id`,`title`,`description`,`date`,`timeOfDay`,`category`,`dificulty`,`userID`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Diary value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescription());
        }
        if (value.getDate() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDate());
        }
        if (value.getTimeOfDay() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getTimeOfDay());
        }
        if (value.getCategory() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getCategory());
        }
        stmt.bindLong(7, value.getDificulty());
        if (value.getUserID() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getUserID());
        }
      }
    };
    this.__deletionAdapterOfDiary = new EntityDeletionOrUpdateAdapter<Diary>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `PersonalDiaryTB` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Diary value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
      }
    };
    this.__updateAdapterOfDiary = new EntityDeletionOrUpdateAdapter<Diary>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `PersonalDiaryTB` SET `id` = ?,`title` = ?,`description` = ?,`date` = ?,`timeOfDay` = ?,`category` = ?,`dificulty` = ?,`userID` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Diary value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescription());
        }
        if (value.getDate() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDate());
        }
        if (value.getTimeOfDay() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getTimeOfDay());
        }
        if (value.getCategory() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getCategory());
        }
        stmt.bindLong(7, value.getDificulty());
        if (value.getUserID() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getUserID());
        }
        if (value.getId() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getId());
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "delete from PersonalDiaryTB";
        return _query;
      }
    };
  }

  @Override
  public long insert(Diary diary) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfDiary.insertAndReturnId(diary);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int delete(Diary diary) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfDiary.handle(diary);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int update(Diary diary) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfDiary.handle(diary);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteAll() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Diary>> selectAll() {
    final String _sql = "SELECT * FROM PersonalDiaryTB";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Diary>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<Diary> compute() {
        if (_observer == null) {
          _observer = new Observer("PersonalDiaryTB") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
          final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
          final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
          final int _cursorIndexOfTimeOfDay = _cursor.getColumnIndexOrThrow("timeOfDay");
          final int _cursorIndexOfCategory = _cursor.getColumnIndexOrThrow("category");
          final int _cursorIndexOfDificulty = _cursor.getColumnIndexOrThrow("dificulty");
          final int _cursorIndexOfUserID = _cursor.getColumnIndexOrThrow("userID");
          final List<Diary> _result = new ArrayList<Diary>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Diary _item;
            _item = new Diary();
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            _item.setTitle(_tmpTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            _item.setDescription(_tmpDescription);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            _item.setDate(_tmpDate);
            final String _tmpTimeOfDay;
            _tmpTimeOfDay = _cursor.getString(_cursorIndexOfTimeOfDay);
            _item.setTimeOfDay(_tmpTimeOfDay);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            _item.setCategory(_tmpCategory);
            final int _tmpDificulty;
            _tmpDificulty = _cursor.getInt(_cursorIndexOfDificulty);
            _item.setDificulty(_tmpDificulty);
            final String _tmpUserID;
            _tmpUserID = _cursor.getString(_cursorIndexOfUserID);
            _item.setUserID(_tmpUserID);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public LiveData<List<Diary>> selectByDate(String date) {
    final String _sql = "SELECT * FROM PersonalDiaryTB WHERE date LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, date);
    }
    return new ComputableLiveData<List<Diary>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<Diary> compute() {
        if (_observer == null) {
          _observer = new Observer("PersonalDiaryTB") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
          final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
          final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
          final int _cursorIndexOfTimeOfDay = _cursor.getColumnIndexOrThrow("timeOfDay");
          final int _cursorIndexOfCategory = _cursor.getColumnIndexOrThrow("category");
          final int _cursorIndexOfDificulty = _cursor.getColumnIndexOrThrow("dificulty");
          final int _cursorIndexOfUserID = _cursor.getColumnIndexOrThrow("userID");
          final List<Diary> _result = new ArrayList<Diary>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Diary _item;
            _item = new Diary();
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            _item.setTitle(_tmpTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            _item.setDescription(_tmpDescription);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            _item.setDate(_tmpDate);
            final String _tmpTimeOfDay;
            _tmpTimeOfDay = _cursor.getString(_cursorIndexOfTimeOfDay);
            _item.setTimeOfDay(_tmpTimeOfDay);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            _item.setCategory(_tmpCategory);
            final int _tmpDificulty;
            _tmpDificulty = _cursor.getInt(_cursorIndexOfDificulty);
            _item.setDificulty(_tmpDificulty);
            final String _tmpUserID;
            _tmpUserID = _cursor.getString(_cursorIndexOfUserID);
            _item.setUserID(_tmpUserID);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public LiveData<List<Diary>> selectByCategory(String category) {
    final String _sql = "SELECT * FROM PersonalDiaryTB WHERE category LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (category == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, category);
    }
    return new ComputableLiveData<List<Diary>>(__db.getQueryExecutor()) {
      private Observer _observer;

      @Override
      protected List<Diary> compute() {
        if (_observer == null) {
          _observer = new Observer("PersonalDiaryTB") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
          final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("description");
          final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
          final int _cursorIndexOfTimeOfDay = _cursor.getColumnIndexOrThrow("timeOfDay");
          final int _cursorIndexOfCategory = _cursor.getColumnIndexOrThrow("category");
          final int _cursorIndexOfDificulty = _cursor.getColumnIndexOrThrow("dificulty");
          final int _cursorIndexOfUserID = _cursor.getColumnIndexOrThrow("userID");
          final List<Diary> _result = new ArrayList<Diary>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Diary _item;
            _item = new Diary();
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            _item.setTitle(_tmpTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            _item.setDescription(_tmpDescription);
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            _item.setDate(_tmpDate);
            final String _tmpTimeOfDay;
            _tmpTimeOfDay = _cursor.getString(_cursorIndexOfTimeOfDay);
            _item.setTimeOfDay(_tmpTimeOfDay);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            _item.setCategory(_tmpCategory);
            final int _tmpDificulty;
            _tmpDificulty = _cursor.getInt(_cursorIndexOfDificulty);
            _item.setDificulty(_tmpDificulty);
            final String _tmpUserID;
            _tmpUserID = _cursor.getString(_cursorIndexOfUserID);
            _item.setUserID(_tmpUserID);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }
}
