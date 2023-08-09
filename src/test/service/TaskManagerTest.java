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
import static org.mockito.Mockito.*;

public class TaskManagerTest {

    @Mock
    private Task mockTask;
    private TaskManager taskManager;
    @Mock
    private FileUtil fileUtil;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        taskManager = new TaskManager(fileUtil);
    }

    @Test
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
    }

    @Test
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
    public void testUpdateTask() {
        // Prepare mock data
        List<Task> mockTasks = new ArrayList<>();
        Task taskToUpdate = new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1, "Category1", "Open");
        mockTasks.add(taskToUpdate);
        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2, "Category2", "In Progress"));

        // Set up the tasks within the TaskManager instance
        taskManager.tasks = mockTasks;

        // Call the method to be tested
        taskManager.updateTask(
                "Task1",
                "New Description",
                LocalDateTime.parse("2023-08-11T12:00"),
                3,
                "New Category",
                "Closed"
        );

        // Verify that the task properties were updated
        assertEquals("New Description", taskToUpdate.getDescription());
        assertEquals(LocalDateTime.parse("2023-08-11T12:00"), taskToUpdate.getDeadline());
        assertEquals(3, taskToUpdate.getPriority());
        assertEquals("New Category", taskToUpdate.getCategory());
        assertEquals("Closed", taskToUpdate.getStatus());

        // Verify that saveDataToFile() was called
        verify(fileUtil, times(1)).writeData(anyList());
    }

    @Test
    public void testGetTasksByCategory() {
        // Prepare mock data
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1, "Category1", "Open"));
        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2, "Category2", "In Progress"));
        mockTasks.add(new Task("Task3", "Description3", LocalDateTime.parse("2023-08-11T18:00"), 3, "Category1", "Closed"));

        // Set up the tasks within the TaskManager instance
        taskManager.tasks = mockTasks;

        // Call the method to be tested
        List<Task> filteredTasks = taskManager.getTasksByCategory("Category1");

        // Verify that the filteredTasks contains the tasks with the correct category
        assertEquals(2, filteredTasks.size());
        assertEquals("Task1", filteredTasks.get(0).getName());
        assertEquals("Task3", filteredTasks.get(1).getName());
    }

    @Test
    public void testGetTasksByPriority() {
        // Prepare mock data
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1, "Category1", "Open"));
        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2, "Category2", "In Progress"));
        mockTasks.add(new Task("Task3", "Description3", LocalDateTime.parse("2023-08-11T18:00"), 1, "Category1", "Closed"));

        // Set up the tasks within the TaskManager instance
        taskManager.tasks = mockTasks;

        // Call the method to be tested
        List<Task> filteredTasks = taskManager.getTasksByPriority(1);

        // Verify that the filteredTasks contains the tasks with the correct priority
        assertEquals(2, filteredTasks.size());
        assertEquals("Task1", filteredTasks.get(0).getName());
        assertEquals("Task3", filteredTasks.get(1).getName());
    }

    @Test
    public void testGetTasksByStatus() {
        // Prepare mock data
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1, "Category1", "Open"));
        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2, "Category2", "In Progress"));
        mockTasks.add(new Task("Task3", "Description3", LocalDateTime.parse("2023-08-11T18:00"), 1, "Category1", "Closed"));

        // Set up the tasks within the TaskManager instance
        taskManager.tasks = mockTasks;

        // Call the method to be tested
        List<Task> filteredTasks = taskManager.getTasksByStatus("Open");

        // Verify that the filteredTasks contains the tasks with the correct status
        assertEquals(1, filteredTasks.size());
        assertEquals("Task1", filteredTasks.get(0).getName());
    }

    @Test
    public void testCountCompletedTasks() {
        // Prepare mock data
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1, "Category1", "Open"));
        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2, "Category2", "Done"));
        mockTasks.add(new Task("Task3", "Description3", LocalDateTime.parse("2023-08-11T18:00"), 1, "Category1", "Done"));

        // Set up the tasks within the TaskManager instance
        taskManager.tasks = mockTasks;

        // Call the method to be tested
        int completedTaskCount = taskManager.countCompletedTasks();

        // Verify the count of completed tasks
        assertEquals(2, completedTaskCount);
    }

    @Test
    public void testCountToDoTasks() {
        // Prepare mock data
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1, "Category1", "Todo"));
        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2, "Category2", "Done"));
        mockTasks.add(new Task("Task3", "Description3", LocalDateTime.parse("2023-08-11T18:00"), 1, "Category1", "Todo"));

        // Set up the tasks within the TaskManager instance
        taskManager.tasks = mockTasks;

        // Call the method to be tested
        int toDoTaskCount = taskManager.countToDoTasks();

        // Verify the count of tasks to do
        assertEquals(2, toDoTaskCount);
    }

    @Test
    public void testCountDoingTasks() {
        // Prepare mock data
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1, "Category1", "Doing"));
        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2, "Category2", "Done"));
        mockTasks.add(new Task("Task3", "Description3", LocalDateTime.parse("2023-08-11T18:00"), 1, "Category1", "Doing"));

        // Set up the tasks within the TaskManager instance
        taskManager.tasks = mockTasks;

        // Call the method to be tested
        int doingTaskCount = taskManager.countDoingTasks();

        // Verify the count of tasks in progress
        assertEquals(2, doingTaskCount);
    }

    @Test
    public void testAddTaskWithPriorityRebalance() {
        // Prepare mock data
        Task newTask = new Task("New Task", "New Description", LocalDateTime.parse("2023-08-12T12:00"), 2, "Category1", "Open");

        // Call the method to be tested
        taskManager.addTaskWithPriorityRebalance(newTask, true, 15);

        // Verify that the task was added to the tasks list
        assertEquals(1, taskManager.tasks.size());
        assertEquals("New Task", taskManager.tasks.get(0).getName());

        // Verify that saveDataToFile() was called
        verify(fileUtil, times(1)).writeData(anyList());
    }

    @Test
    public void testGetTasksWithAlarms() {
        // Prepare mock data
        Task task1 = new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1, "Category1", "Open");
        task1.addAlarm(LocalDateTime.parse("2023-08-09T11:00"));

        Task task2 = new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2, "Category2", "In Progress");

        Task task3 = new Task("Task3", "Description3", LocalDateTime.parse("2023-08-11T18:00"), 3, "Category1", "Open");
        task3.addAlarm(LocalDateTime.parse("2023-08-11T17:00"));

        // Add tasks to the TaskManager instance
        taskManager.tasks.add(task1);
        taskManager.tasks.add(task2);
        taskManager.tasks.add(task3);

        // Call the method to be tested
        List<Task> tasksWithAlarms = taskManager.getTasksWithAlarms();

        // Verify the tasks with alarms
        assertEquals(2, tasksWithAlarms.size());
        assertTrue(tasksWithAlarms.contains(task1));
        assertTrue(tasksWithAlarms.contains(task3));
    }

    @Test
    public void testDeleteTask() {
        String taskName = "TaskToDelete";

        // Create a sample task and add it to the task list manually
        Task taskToDelete = new Task(taskName, "Description", LocalDateTime.now(), 1, "Category", "Todo");
        taskManager.tasks.add(taskToDelete);

        // Mocking readData to return an empty list
        when(fileUtil.readData()).thenReturn(new ArrayList<>());

        // Call the actual deleteTask method
        taskManager.deleteTask(taskName);

        // Verifying that the task was removed
        verify(fileUtil, times(1)).writeData(anyList());

        // Verify that the task was removed from the task list
        List<Task> tasks = taskManager.listAllTasks();
        assertTrue(tasks.isEmpty()); // Assuming you have a method to get all tasks in your TaskManager
    }
}




