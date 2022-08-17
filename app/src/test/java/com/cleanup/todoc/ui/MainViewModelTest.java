package com.cleanup.todoc.ui;

import static org.junit.Assert.assertArrayEquals;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.ui.utils.TaskRepository;
import com.cleanup.todoc.utils.DataUtils;
import com.cleanup.todoc.utils.LiveDataTestUtils;
import com.cleanup.todoc.utils.TestExecutor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

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

}