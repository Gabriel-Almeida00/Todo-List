package todo.list.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import todo.list.entity.Task;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
public class TaskServiceTest {

    private TaskService taskManager;
    private IFileService fileUtilMock;

    @BeforeEach
    public void setUp() {
        fileUtilMock = mock(IFileService.class);
        taskManager = new TaskService(fileUtilMock);
    }

    @Test
    public void testLoadDataFromFile() throws IOException {
        List<Task> testData = Arrays.asList(
                new Task("Task 1",
                        "Description 1",
                        LocalDateTime.parse("2023-08-10T12:00"),
                        1,
                        "Category 1",
                        "Incomplete"),
                new Task("Task 2",
                        "Description 2",
                        LocalDateTime.parse("2023-08-11T14:00"),
                        2,
                        "Category 2",
                        "Complete")
        );
        when(fileUtilMock.loadTasks()).thenReturn(testData);

        taskManager.loadDataFromFile();

        List<Task> loadedTasks = taskManager.listAllTasks();
        assertEquals(2, loadedTasks.size());
        assertEquals("Task 1", loadedTasks.get(0).getName());
        assertEquals("Description 1", loadedTasks.get(0).getDescription());
        assertEquals("Task 2", loadedTasks.get(1).getName());
        assertEquals("Description 2", loadedTasks.get(1).getDescription());
    }
    @Test
    public void testSaveDataToFile() throws IOException {
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1",
                "Description1",
                LocalDateTime.parse("2023-08-09T12:00"),
                1,
                "Category1",
                "Open"));
        mockTasks.add(new Task("Task2",
                "Description2", LocalDateTime.parse("2023-08-10T15:00"),
                2,
                "Category2",
                "In Progress"));

        taskManager.tasks = mockTasks;

        List<String> expectedLines = new ArrayList<>();
        expectedLines.add("Task1,Description1,2023-08-09T12:00,1,Category1,Open");
        expectedLines.add("Task2,Description2,2023-08-10T15:00,2,Category2,In Progress");

        doNothing().when(fileUtilMock).saveTasks(mockTasks);

        taskManager.saveDataToFile();

        verify(fileUtilMock, times(1)).saveTasks(mockTasks);
    }

    @Test
    public void testListAllTasks() {
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1, "Category1", "Open"));
        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2, "Category2", "In Progress"));

        taskManager.tasks = mockTasks;

        List<Task> tasksReturned = taskManager.listAllTasks();

        assertEquals(mockTasks, tasksReturned);
    }

    @Test
    public void testUpdateTask() throws IOException {
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1, "Category1", "Open"));
        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2, "Category2", "In Progress"));
        when(fileUtilMock.loadTasks()).thenReturn(mockTasks);

        taskManager.loadDataFromFile();

        Task taskToAdd = new Task("Task1", "NewDescription", LocalDateTime.parse("2023-08-11T14:00"), 3, "Category3", "Open");

        taskManager.updateTask(taskToAdd, true, 30);

        verify(fileUtilMock, times(1)).saveTasks(anyList());

        List<Task> updatedTasks = taskManager.listAllTasks();
        assertEquals(2, updatedTasks.size());

        Task updatedTask = updatedTasks.get(0);
        assertEquals("Task1", updatedTask.getName());
        assertEquals("NewDescription", updatedTask.getDescription());
        assertEquals(LocalDateTime.parse("2023-08-11T14:00"), updatedTask.getDeadline());
        assertEquals(3, updatedTask.getPriority());
        assertEquals("Category3", updatedTask.getCategory());
        assertEquals("Open", updatedTask.getStatus());

        Task task2 = updatedTasks.get(1);
        assertEquals("Task2", task2.getName());
    }

    @Test
    public void testGetTasksByCategory() {
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1, "Category1", "Open"));
        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2, "Category2", "In Progress"));
        mockTasks.add(new Task("Task3", "Description3", LocalDateTime.parse("2023-08-11T18:00"), 3, "Category1", "Closed"));

        taskManager.tasks = mockTasks;

        List<Task> filteredTasks = taskManager.getTasksByCategory("Category1");

        assertEquals(2, filteredTasks.size());
        assertEquals("Task1", filteredTasks.get(0).getName());
        assertEquals("Task3", filteredTasks.get(1).getName());
    }

    @Test
    public void testGetTasksByPriority() {
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1, "Category1", "Open"));
        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2, "Category2", "In Progress"));
        mockTasks.add(new Task("Task3", "Description3", LocalDateTime.parse("2023-08-11T18:00"), 1, "Category1", "Closed"));

        taskManager.tasks = mockTasks;

        List<Task> filteredTasks = taskManager.getTasksByPriority(1);

        assertEquals(2, filteredTasks.size());
        assertEquals("Task1", filteredTasks.get(0).getName());
        assertEquals("Task3", filteredTasks.get(1).getName());
    }

    @Test
    public void testGetTasksByStatus() {
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1, "Category1", "todo"));
        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2, "Category2", "doing"));
        mockTasks.add(new Task("Task3", "Description3", LocalDateTime.parse("2023-08-11T18:00"), 1, "Category1", "done"));

        taskManager.tasks = mockTasks;

        List<Task> filteredTasks = taskManager.getTasksByStatus("todo");

        assertEquals(1, filteredTasks.size());
        assertEquals("Task1", filteredTasks.get(0).getName());
    }

    @Test
    public void testCountCompletedTasks() {
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1, "Category1", "Open"));
        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2, "Category2", "Done"));
        mockTasks.add(new Task("Task3", "Description3", LocalDateTime.parse("2023-08-11T18:00"), 1, "Category1", "Done"));

        taskManager.tasks = mockTasks;

        int completedTaskCount = taskManager.countCompletedTasks();

        assertEquals(2, completedTaskCount);
    }

    @Test
    public void testCountToDoTasks() {
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1, "Category1", "Todo"));
        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2, "Category2", "Done"));
        mockTasks.add(new Task("Task3", "Description3", LocalDateTime.parse("2023-08-11T18:00"), 1, "Category1", "Todo"));

        taskManager.tasks = mockTasks;

        int toDoTaskCount = taskManager.countToDoTasks();

        assertEquals(2, toDoTaskCount);
    }

    @Test
    public void testCountDoingTasks() {
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1, "Category1", "Doing"));
        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2, "Category2", "Done"));
        mockTasks.add(new Task("Task3", "Description3", LocalDateTime.parse("2023-08-11T18:00"), 1, "Category1", "Doing"));

        taskManager.tasks = mockTasks;

        int doingTaskCount = taskManager.countDoingTasks();

        assertEquals(2, doingTaskCount);
    }

    @Test
    public void testAddTaskWithPriorityRebalance() throws IOException {
        Task taskToAdd = new Task("NewTask", "NewDescription", LocalDateTime.parse("2023-08-11T14:00"), 3, "Category3", "Open");

        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1, "Category1", "Open"));
        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2, "Category2", "In Progress"));
        when(fileUtilMock.loadTasks()).thenReturn(mockTasks);

        taskManager.loadDataFromFile();

        taskManager.addTaskWithPriorityRebalance(taskToAdd, true, 30);

        verify(fileUtilMock, times(1)).saveTasks(anyList());

        List<Task> updatedTasks = taskManager.listAllTasks();
        assertEquals(3, updatedTasks.size());

        Task addedTask = updatedTasks.get(0);
        assertEquals("NewTask", addedTask.getName());
        assertEquals("NewDescription", addedTask.getDescription());
        assertEquals(LocalDateTime.parse("2023-08-11T14:00"), addedTask.getDeadline());
        assertEquals(3, addedTask.getPriority());
        assertEquals("Category3", addedTask.getCategory());
        assertEquals("Open", addedTask.getStatus());

        Task highestPriorityTask = updatedTasks.get(0);
        assertEquals("NewTask", highestPriorityTask.getName());

        Task middlePriorityTask = updatedTasks.get(1);
        assertEquals("Task2", middlePriorityTask.getName());
    }

    @Test
    public void testGetTasksWithAlarms() {
        Task task1 = new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1, "Category1", "Open");
        task1.addAlarm(LocalDateTime.parse("2023-08-09T11:00"));

        Task task2 = new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2, "Category2", "In Progress");

        Task task3 = new Task("Task3", "Description3", LocalDateTime.parse("2023-08-11T18:00"), 3, "Category1", "Open");
        task3.addAlarm(LocalDateTime.parse("2023-08-11T17:00"));

        taskManager.tasks.add(task1);
        taskManager.tasks.add(task2);
        taskManager.tasks.add(task3);

        List<Task> tasksWithAlarms = taskManager.getTasksWithAlarms();

        assertEquals(2, tasksWithAlarms.size());
        assertTrue(tasksWithAlarms.contains(task1));
        assertTrue(tasksWithAlarms.contains(task3));
    }

    @Test
    public void testDeleteTask() throws IOException {
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1, "Category1", "Open"));
        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2, "Category2", "In Progress"));

        when(fileUtilMock.loadTasks()).thenReturn(mockTasks);

        taskManager.loadDataFromFile();

        taskManager.deleteTask("Task1");

        verify(fileUtilMock, times(1)).saveTasks(anyList());

        List<Task> remainingTasks = taskManager.listAllTasks();
        assertEquals(1, remainingTasks.size());

        Task remainingTask = remainingTasks.get(0);
        assertEquals("Task2", remainingTask.getName());
        assertEquals("Description2", remainingTask.getDescription());
        assertEquals(LocalDateTime.parse("2023-08-10T15:00"), remainingTask.getDeadline());
        assertEquals(2, remainingTask.getPriority());
        assertEquals("Category2", remainingTask.getCategory());
        assertEquals("In Progress", remainingTask.getStatus());
    }
}



