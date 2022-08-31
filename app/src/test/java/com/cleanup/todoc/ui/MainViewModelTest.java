package com.cleanup.todoc.ui;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.R;
import com.cleanup.todoc.ui.utils.TaskRepository;
import com.cleanup.todoc.utils.DataUtils;
import com.cleanup.todoc.utils.LiveDataTestUtils;
import com.cleanup.todoc.utils.TestExecutor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

public class MainViewModelTest {

    private final TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
    private final Executor ioExecutor = Mockito.spy(new TestExecutor());
    private final Long insertedDate = new Date().getTime();
    private final MutableLiveData<List<Task>> projectsMutableLiveData = new MutableLiveData<>();
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private MainViewModel viewModel;

    @Before
    public void setUp() {
        Mockito.doReturn(projectsMutableLiveData).when(taskRepository).getTasks();

        viewModel = new MainViewModel(taskRepository, ioExecutor);
    }

    @Test
    public void on_load_view_test() {
        // Given
        projectsMutableLiveData.setValue(DataUtils.getDefaultTask(insertedDate));

        // When
        viewModel.onLoadView();

        // Then
        MainStateWithTasks addTaskViewState = (MainStateWithTasks) LiveDataTestUtils.getValueForTesting(viewModel.state);
        assertArrayEquals(addTaskViewState.getTasks().toArray(), DataUtils.getDefaultTask(insertedDate).toArray());
    }

    @Test
    public void on_sorted_list_by_alphabetical() {
        //Given
        ArrayList<Task> witnessList = DataUtils.getTaskToSortByName(insertedDate);
        projectsMutableLiveData.setValue(witnessList);
        Task.TaskAZComparator comp = new Task.TaskAZComparator();
        Collections.sort(witnessList, comp);
        viewModel.setSortMethod(R.id.filter_alphabetical);

        //When
        viewModel.onLoadView();
        viewModel.sort();

        //Then
        MainStateWithTasks tasksViewState = (MainStateWithTasks) LiveDataTestUtils.getValueForTesting(viewModel.state);
        assertArrayEquals(witnessList.toArray(), tasksViewState.getTasks().toArray());
    }

    @Test
    public void on_sorted_list_by_alphabetical_inverted() {
        //Given
        ArrayList<Task> witnessList = DataUtils.getTaskToSortByName(insertedDate);
        projectsMutableLiveData.setValue(witnessList);
        Task.TaskZAComparator comp = new Task.TaskZAComparator();
        Collections.sort(witnessList, comp);
        viewModel.setSortMethod(R.id.filter_alphabetical_inverted);

        //When
        viewModel.onLoadView();
        viewModel.sort();

        //Then
        MainStateWithTasks tasksViewState = (MainStateWithTasks) LiveDataTestUtils.getValueForTesting(viewModel.state);
        assertArrayEquals(witnessList.toArray(), tasksViewState.getTasks().toArray());
    }

    @Test
    public void on_sorted_list_by_date_oldest() {
        //Given
        final Long insertedDate2 = new Date().getTime();
        final Long insertedDate3 = new Date().getTime();
        ArrayList<Task> witnessList = DataUtils.getTaskToSortByDate(insertedDate,insertedDate2,insertedDate3);
        projectsMutableLiveData.setValue(witnessList);
        Task.TaskOldComparator comp = new Task.TaskOldComparator();
        Collections.sort(witnessList, comp);
        viewModel.setSortMethod(R.id.filter_oldest_first);

        //When
        viewModel.onLoadView();
        viewModel.sort();

        //Then
        MainStateWithTasks tasksViewState = (MainStateWithTasks) LiveDataTestUtils.getValueForTesting(viewModel.state);
        assertArrayEquals(witnessList.toArray(), tasksViewState.getTasks().toArray());
    }

    @Test
    public void on_sorted_list_by_date_newest() throws InterruptedException {
        //Given
        final Long insertedDate2 = new Date().getTime();
        Thread.sleep(1000);
        final Long insertedDate3 = new Date().getTime();
        ArrayList<Task> witnessList = DataUtils.getTaskToSortByDate(insertedDate,insertedDate2,insertedDate3);
        ArrayList<Task> test = DataUtils.getTaskToSortByDate(insertedDate,insertedDate2,insertedDate3);
        projectsMutableLiveData.setValue(witnessList);
        Task.TaskRecentComparator comp = new Task.TaskRecentComparator();
        Collections.sort(test, comp);

        //When
        viewModel.onLoadView();
        LiveDataTestUtils.observeValueForTesting(viewModel.state);
        viewModel.setSortMethod(R.id.filter_recent_first);

        //Then
        MainStateWithTasks tasksViewState = (MainStateWithTasks) LiveDataTestUtils.getValueForTesting(viewModel.state);
        assertArrayEquals(test.toArray(), tasksViewState.getTasks().toArray());
    }

    @Test
    public void on_deleted_task_test(){
        //Given
        ArrayList<Task> witnessList = DataUtils.getDefaultTask(insertedDate);
        Task taskToBeDeleted = witnessList.get(0);
        projectsMutableLiveData.setValue(witnessList);

        //When
        viewModel.onLoadView();
        LiveDataTestUtils.getValueForTesting(viewModel.state);
        viewModel.removeTasks(taskToBeDeleted);

        //Then
        MainStateWithTasks tasksViewState = (MainStateWithTasks) LiveDataTestUtils.getValueForTesting(viewModel.state);
        assertTrue(!tasksViewState.getTasks().contains(taskToBeDeleted));
    }
}