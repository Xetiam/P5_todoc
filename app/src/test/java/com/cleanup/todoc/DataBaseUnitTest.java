package com.cleanup.todoc;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.ui.MainActivity;
import com.cleanup.todoc.ui.MainState;
import com.cleanup.todoc.ui.utils.TaskRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Category(Robolectric.class)
@RunWith(RobolectricTestRunner.class)
public class DataBaseUnitTest {
    private TaskRepository mockRepository;
    private ArrayList<Task> dataBaseTasks;
    private MainActivity mockView = Mockito.spy(new MainActivity());

    @Before
    public void setUp() {

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1, 1, "task 1", new Date().getTime()));
        tasks.add(new Task(2, 2, "task 2", new Date().getTime()));
        tasks.add(new Task(3, 3, "task 3", new Date().getTime()));
        tasks.add(new Task(4, 4, "task 4", new Date().getTime()));
        mockRepository = new TaskRepository(ApplicationProvider.getApplicationContext());
        mockRepository.updateDataBase(tasks);
    }

    @Test
    public void shouldAddTaskInDataBase() throws InterruptedException {
        Task newTask = new Task(5, 2, "task 5", new Date().getTime());
        mockRepository.addTaskToDataBase(newTask);
        LiveData<ArrayList<Task>> tasks = mockRepository.getTasks();
        //tasks.observe(ApplicationProvider.getApplicationContext(), this::getLiveDataValue);
        assert Objects.requireNonNull(getOrAwaitValue(tasks)).contains(newTask);
    }

    private void getLiveDataValue(ArrayList<Task> tasks) {
        dataBaseTasks = tasks;
    }

    private static <T> T getOrAwaitValue(final LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(@Nullable T o) {
                data[0] = o;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        latch.await(2, TimeUnit.SECONDS);
        //noinspection unchecked
        return (T) data[0];
    }

}
