package com.example.gts.alaram.database;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import android.content.Context;
import androidx.annotation.NonNull;

import com.example.gts.alaram.database.dao.LocationDao;
import com.example.gts.alaram.database.dao.TaskDao;
import com.example.gts.alaram.models.LocationModel;
import com.example.gts.alaram.models.TaskModel;

/**
 * @author shilpi
 */

@Database(entities = {TaskModel.class, LocationModel.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    // Add Daos.
    public abstract TaskDao taskDao();

    public abstract LocationDao locationDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, DbConstants.APP_DATABASE_NAME)
                    .addMigrations(MIGRATION_2_3, MIGRATION_3_4)
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            DatabaseMigrator.migrateFromSqlToRoom(database);
        }

    };

    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            String query = "ALTER TABLE tasks ADD COLUMN repeat_code INTEGER NOT NULL DEFAULT 0 ;";
            database.execSQL(query);
        }
    };

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
