package todo.list.dao;

import todo.list.model.Task;
import todo.list.model.enums.TaskStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ITaskDao {
    List<Task> listAllTasks();
    void addTask(Task task);
    void updateTask(Task updatedTask);
    public void setTaskDone(UUID idTask);
    void deleteTask(UUID taskId);

    List<Task> getTasksByCategory(String categoryName);
    List<Task> getTasksByPriority(Integer priority);
    List<Task> getTasksByStatus(TaskStatus status);

    int countCompletedTasks();
    int countToDoTasks();
    int countDoingTasks();

    List<Task> filterTasksByDate(LocalDate date);
    List<Task> getTasksWithAlarms();
     void desativarAlarme(UUID id);
}
