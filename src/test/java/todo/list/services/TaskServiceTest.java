package todo.list.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import todo.list.model.Alarm;
import todo.list.model.Category;
import todo.list.model.Task;
import todo.list.model.enums.TaskStatus;
import todo.list.services.task.ITaskService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class TaskServiceTest {

    private ITaskService taskService;

    @BeforeEach
    public void setUp() {
        taskService = mock(ITaskService.class);

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
                        new Alarm(LocalDateTime.parse("2023-08-10T14:30"), "Alarme 2", 30))));

        when(taskService.listAllTasks()).thenReturn(mockTasks);
        List<Task> tasksReturned = taskService.listAllTasks();

        assertEquals(mockTasks, tasksReturned);
    }

    @Test
    public void testAddTaskWithPriorityRebalance() {
        Task taskToAdd = new Task("NewTask", "NewDescription", LocalDateTime.parse("2023-08-11T14:00"), 3,
                new Category("Category3", "Category2 Description"), TaskStatus.DOING, Collections.singletonList(
                new Alarm(LocalDateTime.parse("2023-08-10T14:30"), "Alarme 2", 30)));

        doNothing().when(taskService).addTaskWithPriorityRebalance(taskToAdd);
        taskService.addTaskWithPriorityRebalance(taskToAdd);

        verify(taskService, times(1)).addTaskWithPriorityRebalance(taskToAdd);
    }


    @Test
    public void testUpdateTask() {
        Task updatedTask = new Task("UpdatedTask", "UpdatedDescription", LocalDateTime.now(), 2,
                new Category("UpdatedCategory", "UpdatedCategory Description"), TaskStatus.DOING,
                Collections.singletonList(new Alarm(LocalDateTime.now(), "UpdatedAlarm", 30)));

        taskService.updateTask(updatedTask);
        verify(taskService).updateTask(updatedTask);
    }

    @Test
    public void testDeleteTask() {
        Integer taskIdToDelete = 1;

        taskService.deleteTask(taskIdToDelete);
        verify(taskService).deleteTask(taskIdToDelete);
    }

    @Test
    public void testGetTasksByCategory() {
        String categoryNameToFilter = "Category1";
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1,
                new Category("Category1", "Category1 Description"), TaskStatus.TODO, Collections.singletonList(
                new Alarm(LocalDateTime.parse("2023-08-09T11:45"), "Alarme 1", 15)
        )));

        when(taskService.getTasksByCategory(categoryNameToFilter)).thenReturn(mockTasks);
        List<Task> filteredTasks = taskService.getTasksByCategory(categoryNameToFilter);

        assertEquals(1, filteredTasks.size());
        assertEquals("Task1", filteredTasks.get(0).getName());
    }

    @Test
    public void testGetTasksByPriority() {
        Integer priorityToFilter = 1;
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1,
                new Category("Category1", "Category1 Description"), TaskStatus.TODO, Collections.singletonList(
                new Alarm(LocalDateTime.parse("2023-08-09T11:45"), "Alarme 1", 15)
        )));
        mockTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2,
                new Category("Category2", "Category2 Description"), TaskStatus.DOING, Collections.singletonList(
                new Alarm(LocalDateTime.parse("2023-08-10T14:30"), "Alarme 2", 30)
        )));

        when(taskService.getTasksByPriority(priorityToFilter)).thenReturn(mockTasks);
        List<Task> filteredTasks = taskService.getTasksByPriority(priorityToFilter);

        assertEquals(2, filteredTasks.size());
        assertEquals("Task1", filteredTasks.get(0).getName());
        assertEquals("Task2", filteredTasks.get(1).getName());
    }

    @Test
    public void testGetTasksByStatus() {
        TaskStatus statusToFilter = TaskStatus.TODO;
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1,
                new Category("Category1", "Category1 Description"), TaskStatus.TODO, Collections.singletonList(
                new Alarm(LocalDateTime.parse("2023-08-09T11:45"), "Alarme 1", 15)
        )));


        when(taskService.getTasksByStatus(statusToFilter)).thenReturn(mockTasks);
        List<Task> filteredTasks = taskService.getTasksByStatus(statusToFilter);

        assertEquals(1, filteredTasks.size());
        assertEquals("Task1", filteredTasks.get(0).getName());
    }

    @Test
    public void testCountCompletedTasks() {
        List<Task> mockCompletedTasks = new ArrayList<>();
        mockCompletedTasks.add(new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1,
                new Category("Category1", "Category1 Description"), TaskStatus.DONE, Collections.singletonList(
                new Alarm(LocalDateTime.parse("2023-08-09T11:45"), "Alarme 1", 15)
        )));
        mockCompletedTasks.add(new Task("Task2", "Description2", LocalDateTime.parse("2023-08-10T15:00"), 2,
                new Category("Category2", "Category2 Description"), TaskStatus.DONE, Collections.singletonList(
                new Alarm(LocalDateTime.parse("2023-08-10T14:30"), "Alarme 2", 30)
        )));

        when(taskService.countCompletedTasks()).thenReturn(mockCompletedTasks.size());

        int completedTaskCount = taskService.countCompletedTasks();
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

        when(taskService.countToDoTasks()).thenReturn(mockTasks.size());

        Integer toDoTaskCount = taskService.countToDoTasks();
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

        when(taskService.countDoingTasks()).thenReturn(mockTasks.size());
        Integer doingTaskCount = taskService.countDoingTasks();

        assertEquals(2, doingTaskCount);
    }
}



