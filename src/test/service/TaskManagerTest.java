package test.service;


import entity.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.FileUtil;
import service.TaskManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class TaskManagerTest {

    private TaskManager taskManager;
    @Mock
    private FileUtil fileUtil;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        taskManager = new TaskManager(fileUtil);
    }

   /* @Test
    public void testLoadDataFromFile() {
        List<String> mockLines = new ArrayList<>();
        mockLines.add("Task1,Description1,2023-08-09T12:00,1,Category1,Open");
        mockLines.add("Task2,Description2,2023-08-10T15:00,2,Category2,In Progress");

        when(fileUtil.readData()).thenReturn(mockLines);

        taskManager.loadDataFromFile();

        List<Task> loadedTasks = taskManager.listAllTasks();

        assertEquals(2, loadedTasks.size());

        Task task1 = loadedTasks.get(0);
        assertEquals("Task1", task1.getName());
        assertEquals("Description1", task1.getDescription());


        Task task2 = loadedTasks.get(1);
        assertEquals("Task2", task2.getName());
        assertEquals("Description2", task2.getDescription());
    }*/

    /*@Test
    public void testSaveDataToFile() {
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1, "Category1", "Open"));
        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2, "Category2", "In Progress"));

        taskManager = new TaskManager(fileUtil);
        taskManager.tasks = mockTasks;

        taskManager.saveDataToFile();

        List<String> expectedLines = new ArrayList<>();
        expectedLines.add("Task1,Description1,2023-08-09T12:00,1,Category1,Open");
        expectedLines.add("Task2,Description2,2023-08-10T15:00,2,Category2,In Progress");

        verify(fileUtil, times(1)).writeData(expectedLines);
    }*/

    @Test
    public void testListAllTasks() {
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1, "Category1", "Open"));
        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2, "Category2", "In Progress"));

        taskManager.tasks = mockTasks;

        List<Task> tasksReturned = taskManager.listAllTasks();

        assertEquals(mockTasks, tasksReturned);
    }

    /*@Test
    public void testUpdateTask() {
        List<Task> mockTasks = new ArrayList<>();
        Task taskToUpdate = new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1, "Category1", "Open");
        mockTasks.add(taskToUpdate);
        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2, "Category2", "In Progress"));

        taskManager.tasks = mockTasks;

        taskManager.updateTask(
                "Task1",
                "New Description",
                LocalDateTime.parse("2023-08-11T12:00"),
                3,
                "New Category",
                "Closed",true,5
        );

        assertEquals("New Description", taskToUpdate.getDescription());
        assertEquals(LocalDateTime.parse("2023-08-11T12:00"), taskToUpdate.getDeadline());
        assertEquals(3, taskToUpdate.getPriority());
        assertEquals("New Category", taskToUpdate.getCategory());
        assertEquals("Closed", taskToUpdate.getStatus());

        verify(fileUtil, times(1)).writeData(anyList());
    }*/

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
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1, "Category1", "Open"));
        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2, "Category2", "In Progress"));
        mockTasks.add(new Task("Task3", "Description3", LocalDateTime.parse("2023-08-11T18:00"), 1, "Category1", "Closed"));

        taskManager.tasks = mockTasks;

        List<Task> filteredTasks = taskManager.getTasksByStatus("Open");

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

   /* @Test
    public void testAddTaskWithPriorityRebalance() {
        Task newTask = new Task("New Task", "New Description", LocalDateTime.parse("2023-08-12T12:00"), 2, "Category1", "Open");

        taskManager.addTaskWithPriorityRebalance(newTask, true, 15);

        assertEquals(1, taskManager.tasks.size());
        assertEquals("New Task", taskManager.tasks.get(0).getName());

        verify(fileUtil, times(1)).writeData(anyList());
    }*/

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

    /*@Test
    public void testDeleteTask() {
        String taskName = "TaskToDelete";

        Task taskToDelete = new Task(taskName, "Description", LocalDateTime.now(), 1, "Category", "Todo");
        taskManager.tasks.add(taskToDelete);

        when(fileUtil.readData()).thenReturn(new ArrayList<>());

        taskManager.deleteTask(taskName);

        verify(fileUtil, times(1)).writeData(anyList());

        List<Task> tasks = taskManager.listAllTasks();
        assertTrue(tasks.isEmpty());
    }*/
}



