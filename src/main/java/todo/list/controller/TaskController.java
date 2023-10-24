package todo.list.controller;

import todo.list.model.Task;
import todo.list.model.enums.TaskStatus;
import todo.list.services.task.ITaskService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class TaskController {
    ITaskService taskService;

    public TaskController(ITaskService taskService) {
        this.taskService = taskService;
    }

    public List<Task> listAllTasks() {
        return taskService.listAllTasks();
    }

    public void addTaskWithPriorityRebalance(Task task) {
        taskService.addTaskWithPriorityRebalance(task);
    }
    public void updateTask(Task updatedTask) {
        taskService.updateTask(updatedTask);
    }

    public void deleteTask(UUID taskId) {
        taskService.deleteTask(taskId);
    }

    public List<Task> getTasksByCategory(String categoryName) {
        return taskService.getTasksByCategory(categoryName);
    }

    public List<Task> getTasksByPriority(Integer priority) {
        return taskService.getTasksByPriority(priority);
    }

    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskService.getTasksByStatus(status);
    }

    public int countCompletedTasks() {
        return taskService.countCompletedTasks();
    }

    public int countToDoTasks() {
        return taskService.countToDoTasks();
    }

    public int countDoingTasks() {
        return taskService.countDoingTasks();
    }

    public List<Task> filterTasksByDate(LocalDate date) {
        return taskService.filterTasksByDate(date);
    }

    public List<Task> getTasksWithAlarms() {
        return taskService.getTasksWithAlarms();
    }
}
