package todo.list.interfaces;


import todo.list.entity.Task;

import java.time.LocalDate;
import java.util.List;

public interface TaskInterface {
     List<Task> listAllTasks();
     void addTaskWithPriorityRebalance(Task task, boolean enableAlarm, int alarmPeriodMinutes);
     void updateTask(Task task, boolean enableAlarm, int alarmPeriodMinutes);
     List<Task> getTasksByCategory(String category);
     List<Task> getTasksByPriority(int priority);
     List<Task> getTasksByStatus(String status);
     int countCompletedTasks();
     int countToDoTasks();
     int countDoingTasks();
     void deleteTask(String name);
     List<Task> filterTasksByDate(LocalDate date);
     List<Task> getTasksWithAlarms();
}
