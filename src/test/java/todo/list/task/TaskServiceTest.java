package todo.list.task;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import todo.list.entity.Alarm;
import todo.list.entity.Category;
import todo.list.entity.Task;
import todo.list.entity.enums.TaskStatus;
import todo.list.task.file.IFileService;
import todo.list.task.task.TaskService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        when(fileUtilMock.loadTasks()).thenReturn(mockTasks);

        taskManager.loadDataFromFile();

        List<Task> loadedTasks = taskManager.listAllTasks();
        assertEquals(2, loadedTasks.size());
        assertEquals("Task1", loadedTasks.get(0).getName());
        assertEquals("Description1", loadedTasks.get(0).getDescription());
        assertEquals("Task2", loadedTasks.get(1).getName());
        assertEquals("Description2", loadedTasks.get(1).getDescription());
    }

    @Test
    public void testSaveDataToFile() throws IOException {
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

        taskManager.tasks = mockTasks;

        List<Task> tasksReturned = taskManager.listAllTasks();

        assertEquals(mockTasks, tasksReturned);
    }

    @Test
    public void testUpdateTask() throws IOException {
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1,
                new Category("Category1", "Category1 Description"), TaskStatus.TODO, Collections.singletonList(
                new Alarm(LocalDateTime.parse("2023-08-09T11:45"), "Alarme 1", 15)
        )));

        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2,
                new Category("Category2", "Category2 Description"), TaskStatus.DOING, Collections.singletonList(
                new Alarm(LocalDateTime.parse("2023-08-10T14:30"), "Alarme 2", 30)
        )));


        when(fileUtilMock.loadTasks()).thenReturn(mockTasks);
        taskManager.loadDataFromFile();

        Task taskToAdd = new Task("Task1", "Description3", LocalDateTime.parse("2023-08-10T15:00"), 2,
                new Category("Category3", "Category3 Description"), TaskStatus.DOING, Collections.singletonList(
                new Alarm(LocalDateTime.parse("2023-08-10T14:30"), "Alarme3", 30)
        ));

        taskManager.updateTask(taskToAdd);
        List<Task> updatedTasks = taskManager.listAllTasks();
        assertEquals(2, updatedTasks.size());

        Task updatedTask = updatedTasks.get(0);
        assertEquals("Task1", updatedTask.getName());
        assertEquals("Description3", updatedTask.getDescription());
        assertEquals(LocalDateTime.parse("2023-08-10T15:00"), updatedTask.getDeadline());
        assertEquals(2, updatedTask.getPriority());
        assertEquals("Category3", updatedTask.getCategory().getName());
        assertEquals(TaskStatus.DOING, updatedTask.getStatus());

        Task task2 = updatedTasks.get(1);
        assertEquals("Task2", task2.getName());
    }

    @Test
    public void testGetTasksByCategory() {
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1,
                new Category("Category1", "Category1 Description"), TaskStatus.TODO, Collections.singletonList(
                new Alarm(LocalDateTime.parse("2023-08-09T11:45"), "Alarme 1", 15)
        )));

        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2,
                new Category("Category2", "Category2 Description"), TaskStatus.DOING, Collections.singletonList(
                new Alarm(LocalDateTime.parse("2023-08-10T14:30"), "Alarme 2", 30)
        )));
        taskManager.tasks = mockTasks;
        List<Task> filteredTasks = taskManager.getTasksByCategory("Category1");

        assertEquals(1, filteredTasks.size());
        assertEquals("Task1", filteredTasks.get(0).getName());
    }

    @Test
    public void testGetTasksByPriority() {
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1,
                new Category("Category1", "Category1 Description"), TaskStatus.TODO, Collections.singletonList(
                new Alarm(LocalDateTime.parse("2023-08-09T11:45"), "Alarme 1", 15)
        )));

        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 1,
                new Category("Category2", "Category2 Description"), TaskStatus.DOING, Collections.singletonList(
                new Alarm(LocalDateTime.parse("2023-08-10T14:30"), "Alarme 2", 30)
        )));

        taskManager.tasks = mockTasks;
        List<Task> filteredTasks = taskManager.getTasksByPriority(1);

        assertEquals(2, filteredTasks.size());
        assertEquals("Task1", filteredTasks.get(0).getName());
        assertEquals("Task2", filteredTasks.get(1).getName());
    }

    @Test
    public void testGetTasksByStatus() {
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1,
                new Category("Category1", "Category1 Description"), TaskStatus.TODO, Collections.singletonList(
                new Alarm(LocalDateTime.parse("2023-08-09T11:45"), "Alarme 1", 15)
        )));

        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2,
                new Category("Category2", "Category2 Description"), TaskStatus.DOING, Collections.singletonList(
                new Alarm(LocalDateTime.parse("2023-08-10T14:30"), "Alarme 2", 30)
        )));

        taskManager.tasks = mockTasks;
        List<Task> filteredTasks = taskManager.getTasksByStatus(TaskStatus.TODO);

        assertEquals(1, filteredTasks.size());
        assertEquals("Task1", filteredTasks.get(0).getName());
    }

    @Test
    public void testCountCompletedTasks() {
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1,
                new Category("Category1", "Category1 Description"), TaskStatus.DONE, Collections.singletonList(
                new Alarm(LocalDateTime.parse("2023-08-09T11:45"), "Alarme 1", 15)
        )));

        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2,
                new Category("Category2", "Category2 Description"), TaskStatus.DONE, Collections.singletonList(
                new Alarm(LocalDateTime.parse("2023-08-10T14:30"), "Alarme 2", 30)
        )));
        taskManager.tasks = mockTasks;
        Integer completedTaskCount = taskManager.countCompletedTasks();

        assertEquals(2, completedTaskCount);
    }

    @Test
    public void testCountToDoTasks() {
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1,
                new Category("Category1", "Category1 Description"), TaskStatus.TODO, Collections.singletonList(
                new Alarm(LocalDateTime.parse("2023-08-09T11:45"), "Alarme 1", 15)
        )));

        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2,
                new Category("Category2", "Category2 Description"), TaskStatus.TODO, Collections.singletonList(
                new Alarm(LocalDateTime.parse("2023-08-10T14:30"), "Alarme 2", 30)
        )));

        taskManager.tasks = mockTasks;
        Integer toDoTaskCount = taskManager.countToDoTasks();

        assertEquals(2, toDoTaskCount);
    }

    @Test
    public void testCountDoingTasks() {
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1,
                new Category("Category1", "Category1 Description"), TaskStatus.DOING, Collections.singletonList(
                new Alarm(LocalDateTime.parse("2023-08-09T11:45"), "Alarme 1", 15)
        )));

        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2,
                new Category("Category2", "Category2 Description"), TaskStatus.DOING, Collections.singletonList(
                new Alarm(LocalDateTime.parse("2023-08-10T14:30"), "Alarme 2", 30)
        )));
        taskManager.tasks = mockTasks;
        Integer doingTaskCount = taskManager.countDoingTasks();

        assertEquals(2, doingTaskCount);
    }

    @Test
    public void testAddTaskWithPriorityRebalance() throws IOException {
        Task taskToAdd = new Task("NewTask", "NewDescription", LocalDateTime.parse("2023-08-11T14:00"), 3,
                new Category("Category3", "Category2 Description"), TaskStatus.DOING, Collections.singletonList(
                new Alarm(LocalDateTime.parse("2023-08-10T14:30"), "Alarme 2", 30)));

        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1,
                new Category("Category1", "Category1 Description"), TaskStatus.TODO, Collections.singletonList(
                new Alarm(LocalDateTime.parse("2023-08-09T11:45"), "Alarme 1", 15)
        )));

        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2,
                new Category("Category2", "Category2 Description"), TaskStatus.DOING, Collections.singletonList(
                new Alarm(LocalDateTime.parse("2023-08-10T14:30"), "Alarme 2", 30)
        )));
        when(fileUtilMock.loadTasks()).thenReturn(mockTasks);

        taskManager.loadDataFromFile();

        taskManager.addTaskWithPriorityRebalance(taskToAdd);

        verify(fileUtilMock, times(1)).saveTasks(anyList());

        List<Task> updatedTasks = taskManager.listAllTasks();
        assertEquals(3, updatedTasks.size());

        Task addedTask = updatedTasks.get(0);
        assertEquals("NewTask", addedTask.getName());
        assertEquals("NewDescription", addedTask.getDescription());
        assertEquals(LocalDateTime.parse("2023-08-11T14:00"), addedTask.getDeadline());
        assertEquals(3, addedTask.getPriority());
        assertEquals("Category3", addedTask.getCategory().getName());
        assertEquals(TaskStatus.DOING, addedTask.getStatus());

        Task highestPriorityTask = updatedTasks.get(0);
        assertEquals("NewTask", highestPriorityTask.getName());

        Task middlePriorityTask = updatedTasks.get(1);
        assertEquals("Task2", middlePriorityTask.getName());
    }

    @Test
    public void testDeleteTask() throws IOException {
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1,
                new Category("Category1", "Category1 Description"), TaskStatus.TODO, Collections.singletonList(
                new Alarm(LocalDateTime.parse("2023-08-09T11:45"), "Alarme 1", 15)
        )));

        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2,
                new Category("Category2", "Category2 Description"), TaskStatus.DOING, Collections.singletonList(
                new Alarm(LocalDateTime.parse("2023-08-10T14:30"), "Alarme 2", 30)
        )));

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
        assertEquals("Category2", remainingTask.getCategory().getName());
        assertEquals(TaskStatus.DOING, remainingTask.getStatus());
    }
}



