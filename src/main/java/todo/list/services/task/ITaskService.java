package todo.list.services.task;


import todo.list.model.Task;
import todo.list.model.enums.TaskStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ITaskService {
     List<Task> listAllTasks();
     void addTaskWithPriorityRebalance(Task task);
     void updateTask(Task task);
     void deleteTask(UUID taskId);

     List<Task> getTasksByCategory(String categoryName);
     List<Task> getTasksByPriority(Integer priority);
     List<Task> getTasksByStatus(TaskStatus status);

     int countCompletedTasks();
     int countToDoTasks();
     int countDoingTasks();


     List<Task> filterTasksByDate(LocalDate date);
     List<Task> getTasksWithAlarms();
}
