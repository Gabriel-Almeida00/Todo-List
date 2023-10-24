package todo.list.file;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import todo.list.model.Alarm;
import todo.list.model.Category;
import todo.list.model.Task;
import todo.list.model.enums.TaskStatus;
import todo.list.data.IJsonData;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FileServiceTest {
    private IJsonData fileService;

    @BeforeEach
    public void setUp() {
        fileService = mock(IJsonData.class);
    }


    @Test
    public void testLoadTasks() throws IOException {
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(
                new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1,
                        new Category("Category1", "Category1 Description"), TaskStatus.TODO, Collections.singletonList(
                        new Alarm(LocalDateTime.parse("2023-08-09T11:45"), "Alarme 1", 15)
                )));
        mockTasks.add(
                new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2,
                        new Category("Category2", "Category2 Description"), TaskStatus.DOING, Collections.singletonList(
                        new Alarm(LocalDateTime.parse("2023-08-10T14:30"), "Alarme 2", 30)
                )));

        when(fileService.loadTasks()).thenReturn(mockTasks);
        List<Task> loadedTasks = fileService.loadTasks();

        assertEquals(2, loadedTasks.size());
        Task task1 = loadedTasks.get(0);

        assertEquals("Task1", task1.getName());
        assertEquals("Description1", task1.getDescription());

        verify(fileService, times(1)).loadTasks();
    }

    @Test
    public void testSaveTasks() throws IOException {
        List<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(
                new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1,
                        new Category("Category1", "Category1 Description"), TaskStatus.TODO, Collections.singletonList(
                        new Alarm(LocalDateTime.parse("2023-08-09T11:45"), "Alarme 1", 15))));

        List<String> expectedLines = new ArrayList<>();
        expectedLines.add("Task1,Description1,2023-08-09T12:00,1,Category1,Category1 Description,TODO,2023-08-09T11:45,Alarme 1,15");

        fileService.saveTasks(tasksToSave);

        verify(fileService, times(1)).saveTasks(tasksToSave);
    }
}
