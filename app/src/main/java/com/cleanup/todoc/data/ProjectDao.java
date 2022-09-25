package com.cleanup.todoc.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {
    @Query("SELECT * FROM projectdata")
    LiveData<List<ProjectData>> getAll();

    @Query("SELECT * FROM projectdata WHERE projectId IN (:projectIds)")
    List<ProjectData> loadAllByIds(int[] projectIds);

    @Query("SELECT * FROM projectdata WHERE project_name IN(:projectNames)")
    List<ProjectData> loadAllByNames(String[] projectNames);

    @Query("SELECT * FROM projectdata WHERE project_color IN(:projectColors)")
    List<ProjectData> loadAllByColors(String[] projectColors);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ProjectData... projects);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProjectData project);

    @Delete
    void
    delete(ProjectData project);
}

