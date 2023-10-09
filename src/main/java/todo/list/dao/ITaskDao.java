package todo.list.dao;

import todo.list.entity.Task;
import todo.list.entity.enums.TaskStatus;

import java.time.LocalDate;
import java.util.List;

public interface ITaskDao {
    List<Task> listAllTasks();
    void addTask(Task task);
    void updateTask(Task updatedTask);
    void deleteTask(String name);

    List<Task> getTasksByCategory(String categoryName);
    List<Task> getTasksByPriority(Integer priority);
    List<Task> getTasksByStatus(TaskStatus status);

    int countCompletedTasks();
    int countToDoTasks();
    int countDoingTasks();

    List<Task> filterTasksByDate(LocalDate date);
    List<Task> getTasksWithAlarms();
}
