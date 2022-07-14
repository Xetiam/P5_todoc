package com.cleanup.todoc.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.security.Policy;
import java.util.ArrayList;
import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM taskdata")
    LiveData<List<TaskData>> getAll();

    @Query("SELECT * FROM taskdata WHERE taskId IN (:taskIds)")
    List<TaskData> loadAllByIds(int[] taskIds);

    @Query("SELECT * FROM taskdata WHERE creation_time_stamp IN (:creationTimeStamps)")
    List<TaskData> findByCreationDate(Long[] creationTimeStamps);

    @Query("SELECT * FROM taskdata WHERE project_id IN (:projectIds)")
    List<TaskData> findByProjectId(String[] projectIds);

    @Query("SELECT * FROM taskdata WHERE project_id IN (:taskNames)")
    List<TaskData> findByName(String[] taskNames);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(TaskData... tasks);

    @Delete
    void delete(TaskData task);
}
