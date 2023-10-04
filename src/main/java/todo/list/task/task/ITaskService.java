package todo.list.task.task;


import todo.list.entity.Task;
import todo.list.entity.enums.TaskStatus;

import java.time.LocalDate;
import java.util.List;

public interface ITaskService {
      void setTasks(List<Task> tasks);
     List<Task> listAllTasks();
     void addTaskWithPriorityRebalance(Task task);
     void updateTask(Task task);
     void deleteTask(String name);

     List<Task> getTasksByCategory(String categoryName);
     List<Task> getTasksByPriority(Integer priority);
     List<Task> getTasksByStatus(TaskStatus status);

     int countCompletedTasks();
     int countToDoTasks();
     int countDoingTasks();


     List<Task> filterTasksByDate(LocalDate date);
}
