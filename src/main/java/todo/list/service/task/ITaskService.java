package todo.list.service.task;


import todo.list.entity.Task;

import java.time.LocalDate;
import java.util.List;

public interface ITaskService {
     List<Task> listAllTasks();
     void addTaskWithPriorityRebalance(Task task, boolean enableAlarm, int alarmPeriodMinutes);
     void updateTask(Task task, boolean enableAlarm, int alarmPeriodMinutes);
     void deleteTask(String name);

     List<Task> getTasksByCategory(String category);
     List<Task> getTasksByPriority(int priority);
     List<Task> getTasksByStatus(String status);

     int countCompletedTasks();
     int countToDoTasks();
     int countDoingTasks();


     List<Task> filterTasksByDate(LocalDate date);
     List<Task> getTasksWithAlarms();
}
