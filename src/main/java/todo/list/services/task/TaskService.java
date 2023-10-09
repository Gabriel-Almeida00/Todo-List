package todo.list.services.task;

import todo.list.dao.ITaskDao;
import todo.list.entity.Task;
import todo.list.entity.enums.TaskStatus;
import todo.list.services.file.IFileService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskService implements ITaskService {
    private final ITaskDao taskDao;

    public TaskService(ITaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public List<Task> listAllTasks() {
        return taskDao.listAllTasks();
    }

    public void addTaskWithPriorityRebalance(Task task) {
        taskDao.addTask(task);
    }

    public void updateTask(Task updatedTask) {
        taskDao.updateTask(updatedTask);
    }

    public void deleteTask(Integer taskId) {
        taskDao.deleteTask(taskId);
    }

    public List<Task> getTasksByCategory(String categoryName) {
        return taskDao.getTasksByCategory(categoryName);
    }

    public List<Task> getTasksByPriority(Integer priority) {
        return taskDao.getTasksByPriority(priority);
    }

    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskDao.getTasksByStatus(status);
    }

    public int countCompletedTasks() {
        return taskDao.countCompletedTasks();
    }

    public int countToDoTasks() {
        return taskDao.countToDoTasks();
    }

    public int countDoingTasks() {
        return taskDao.countDoingTasks();
    }

    public List<Task> filterTasksByDate(LocalDate date) {
        return taskDao.filterTasksByDate(date);
    }

    public List<Task> getTasksWithAlarms() {
        return taskDao.getTasksWithAlarms();
    }
}
