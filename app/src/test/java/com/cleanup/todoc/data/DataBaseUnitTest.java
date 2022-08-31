package com.cleanup.todoc.data;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.ui.utils.TaskRepository;
import com.cleanup.todoc.utils.DataUtils;
import com.cleanup.todoc.utils.LiveDataTestUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBaseUnitTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final TaskDao taskDataSource = Mockito.mock(TaskDao.class);

    private TaskRepository taskRepository;

    @Before
    public void setUp() {
        taskRepository = new TaskRepository(taskDataSource);
    }

    @Test
    public void verify_addTask() {
        // Given
        Long timestamp = new Date().getTime();
        Task taskModel = new Task(1, 1, "task 1", timestamp);

        // When
        taskRepository.addTaskToDataBase(taskModel);

        // Then
        ArgumentCaptor<TaskData> argument = ArgumentCaptor.forClass(TaskData.class);
        Mockito.verify(taskDataSource).insert(argument.capture());
        TaskData insertedValue = argument.getValue();
        assertEquals(timestamp, insertedValue.creationTimeStamp);
        assertEquals("task 1", insertedValue.taskName);
        assertEquals(new Long(1), argument.getValue().projectId);
        Mockito.verifyNoMoreInteractions(taskDataSource);
    }

    @Test
    public void verify_getAllProjectsWithTasks() {
        // Given
        Long insertedDate = new Date().getTime();

        MutableLiveData<List<TaskData>> projectsWithTasksLiveData = Mockito.spy(new MutableLiveData<>());
        projectsWithTasksLiveData.setValue(DataUtils.getDefaultTaskData(insertedDate));
        Mockito.doReturn(projectsWithTasksLiveData).when(taskDataSource).getAll();

        // When
        ArrayList<Task> resultFromDB = LiveDataTestUtils.getValueForTesting(taskRepository.getTasks());

        // Then
        ArrayList<Task> expectedResult = DataUtils.getDefaultTask(insertedDate);

        assertArrayEquals(expectedResult.toArray(), resultFromDB.toArray());
        Mockito.verify(taskDataSource).getAll();
        Mockito.verifyNoMoreInteractions(taskDataSource);
    }

    @Test
    public void verify_deleteTask(){
        // Given
        Long insertedDate = new Date().getTime();
        Task taskToDelete = DataUtils.getDefaultTask(insertedDate).get(0);
        TaskData taskDataDeleted = new TaskData(taskToDelete.getId(), taskToDelete.getName(), taskToDelete.getProjectId(), taskToDelete.getCreationTimestamp());
        MutableLiveData<List<TaskData>> projectsWithTasksLiveData = Mockito.spy(new MutableLiveData<>());
        projectsWithTasksLiveData.setValue(DataUtils.getDefaultTaskData(insertedDate));
        Mockito.doReturn(projectsWithTasksLiveData).when(taskDataSource).getAll();

        //When
        taskRepository.deleteTaskOnDataBase(taskToDelete);

        //Then
        ArgumentCaptor<TaskData> argument = ArgumentCaptor.forClass(TaskData.class);
        Mockito.verify(taskDataSource).delete(argument.capture());
        assertEquals(argument.getValue(), taskDataDeleted);
        Mockito.verify(taskDataSource).delete(taskDataDeleted);
        Mockito.verifyNoMoreInteractions(taskDataSource);
    }
}
