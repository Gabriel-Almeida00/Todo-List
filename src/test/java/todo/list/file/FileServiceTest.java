package todo.list.file;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import todo.list.entity.Alarm;
import todo.list.entity.Category;
import todo.list.entity.Task;
import todo.list.entity.enums.TaskStatus;
import todo.list.task.file.IFileService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FileServiceTest {
    private IFileService fileService;

    @BeforeEach
    public void setUp() {
        fileService = mock(IFileService.class);
    }

    @Test
    public void testReadData() throws IOException {
        List<String> fakeData = new ArrayList<>();
        fakeData.add("Line 1");
        fakeData.add("Line 2");
        fakeData.add("Line 3");

        when(fileService.readData()).thenReturn(fakeData);
        List<String> readLines = fileService.readData();

        verify(fileService, times(1)).readData();
        assertEquals(fakeData, readLines);
    }

    @Test
    public void testWriteData() throws IOException {
        List<String> dataToWrite = new ArrayList<>();
        dataToWrite.add("Line 1");
        dataToWrite.add("Line 2");
        dataToWrite.add("Line 3");

        fileService.writeData(dataToWrite);
        verify(fileService, times(1)).writeData(dataToWrite);
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
