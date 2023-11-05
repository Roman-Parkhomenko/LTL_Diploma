package info.Parkhomenko.personaldiary.data.database;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import info.Parkhomenko.personaldiary.data.dao.DiaryDAO;
import info.Parkhomenko.personaldiary.data.dao.DiaryDAO_Impl;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public final class MyRoomDB_Impl extends MyRoomDB {
  private volatile DiaryDAO _diaryDAO;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(5) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `PersonalDiaryTB` (`id` TEXT NOT NULL, `title` TEXT, `description` TEXT, `date` TEXT, `timeOfDay` TEXT, `category` TEXT, `dificulty` INTEGER NOT NULL, `userID` TEXT, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"aa221181de02c0abef2ec2f206e76140\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `PersonalDiaryTB`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsPersonalDiaryTB = new HashMap<String, TableInfo.Column>(8);
        _columnsPersonalDiaryTB.put("id", new TableInfo.Column("id", "TEXT", true, 1));
        _columnsPersonalDiaryTB.put("title", new TableInfo.Column("title", "TEXT", false, 0));
        _columnsPersonalDiaryTB.put("description", new TableInfo.Column("description", "TEXT", false, 0));
        _columnsPersonalDiaryTB.put("date", new TableInfo.Column("date", "TEXT", false, 0));
        _columnsPersonalDiaryTB.put("timeOfDay", new TableInfo.Column("timeOfDay", "TEXT", false, 0));
        _columnsPersonalDiaryTB.put("category", new TableInfo.Column("category", "TEXT", false, 0));
        _columnsPersonalDiaryTB.put("dificulty", new TableInfo.Column("dificulty", "INTEGER", true, 0));
        _columnsPersonalDiaryTB.put("userID", new TableInfo.Column("userID", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPersonalDiaryTB = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPersonalDiaryTB = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPersonalDiaryTB = new TableInfo("PersonalDiaryTB", _columnsPersonalDiaryTB, _foreignKeysPersonalDiaryTB, _indicesPersonalDiaryTB);
        final TableInfo _existingPersonalDiaryTB = TableInfo.read(_db, "PersonalDiaryTB");
        if (! _infoPersonalDiaryTB.equals(_existingPersonalDiaryTB)) {
          throw new IllegalStateException("Migration didn't properly handle PersonalDiaryTB(info.Parkhomenko.personaldiary.data.model.Diary).\n"
                  + " Expected:\n" + _infoPersonalDiaryTB + "\n"
                  + " Found:\n" + _existingPersonalDiaryTB);
        }
      }
    }, "aa221181de02c0abef2ec2f206e76140", "20e93739bb51b4eb50e4073f447508eb");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "PersonalDiaryTB");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `PersonalDiaryTB`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public DiaryDAO diaryDAO() {
    if (_diaryDAO != null) {
      return _diaryDAO;
    } else {
      synchronized(this) {
        if(_diaryDAO == null) {
          _diaryDAO = new DiaryDAO_Impl(this);
        }
        return _diaryDAO;
      }
    }
  }
}
