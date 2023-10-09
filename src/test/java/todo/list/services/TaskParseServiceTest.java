package todo.list.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import todo.list.entity.Alarm;
import todo.list.entity.Category;
import todo.list.entity.Task;
import todo.list.entity.enums.TaskStatus;
import todo.list.services.task.ITaskParseService;
import todo.list.services.task.TaskParseService;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaskParseServiceTest {
    private ITaskParseService taskParseService;

    @BeforeEach
    public void setUp() {
        taskParseService = mock(TaskParseService.class);
    }

    @Test
    public void testFromStringArray() {
        String[] parts = {"Task1", "Description1", "2023-08-09T12:00", "1",
                "Category1", "Category1 Description", "TODO", "2023-08-09T11:45,Alarme 1,15"};

        Task expectedTask = new Task(
                "Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1,
                new Category("Category1", "Category1 Description"), TaskStatus.TODO, Collections.singletonList(
                new Alarm(LocalDateTime.parse("2023-08-09T11:45"), "Alarme 1", 15)));

        when(taskParseService.fromStringArray(parts)).thenReturn(expectedTask);
        Task actualTask = taskParseService.fromStringArray(parts);

        assertEquals(expectedTask, actualTask);
    }

    @Test
    public void testToStringArray() {
        Task task = new Task("Task1", "Description1", LocalDateTime.parse("2023-08-09T12:00"), 1,
                new Category("Category1", "Category1 Description"), TaskStatus.TODO, Collections.singletonList(
                new Alarm(LocalDateTime.parse("2023-08-09T11:45"), "Alarme 1", 15)));

        String[] expectedParts = {"Task1", "Description1", "2023-08-09T12:00", "1",
                "Category1", "Category1 Description", "TODO", "2023-08-09T11:45,Alarme 1,15"};


        when(taskParseService.toStringArray(task)).thenReturn(expectedParts);
        String[] actualParts = taskParseService.toStringArray(task);

        assertArrayEquals(expectedParts, actualParts);
    }
}
