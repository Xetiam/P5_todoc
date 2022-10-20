package com.cleanup.todoc.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM taskdata")
    LiveData<List<TaskData>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TaskData task);

    @Delete
    void
    delete(TaskData task);
}
