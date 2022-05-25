package com.cleanup.todoc.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TaskData.class}, version = 1)
public abstract class TaskDataBase extends RoomDatabase {
        public abstract TaskDao taskDao();
}

