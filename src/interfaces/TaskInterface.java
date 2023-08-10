package interfaces;

import entity.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TaskInterface {
    public List<Task> listAllTasks();
    public void addTaskWithPriorityRebalance(Task task, boolean enableAlarm, int alarmPeriodMinutes);
    public void updateTask(String name, String newDescription, LocalDateTime newDeadline, int newPriority, String newCategory, String newStatus, boolean enableAlarm, int alarmPeriodMinutes);
    public List<Task> getTasksByCategory(String category);
    public List<Task> getTasksByPriority(int priority);
    public List<Task> getTasksByStatus(String status);
    public int countCompletedTasks();
    public int countToDoTasks();
    public int countDoingTasks();
    public void deleteTask(String name);
    public List<Task> filterTasksByDate(LocalDate date);
    public List<Task> getTasksWithAlarms();
}
