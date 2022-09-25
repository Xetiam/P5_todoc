package com.cleanup.todoc.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {TaskData.class, ProjectData.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "task_database";

    private static AppDatabase sInstance;

    public static AppDatabase getInstance(
            @NonNull Context application
    ) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = create(application);
                }
            }
        }
        return sInstance;
    }

    private static AppDatabase create(
            @NonNull Context application
    ) {
        return Room.databaseBuilder(
                application,
                AppDatabase.class,
                DATABASE_NAME
        ).build();
    }

    public abstract TaskDao getTaskDao();

    public abstract ProjectDao getProjectDao();
}

