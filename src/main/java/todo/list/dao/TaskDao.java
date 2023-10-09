package todo.list.dao;

import todo.list.entity.Task;
import todo.list.entity.enums.TaskStatus;
import todo.list.services.file.IFileService;
import todo.list.services.task.ITaskParseService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class TaskDao implements ITaskDao{
    private final IFileService fileService;

    public TaskDao(IFileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public List<Task> listAllTasks() {
        try {
            return fileService.loadTasks();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addTask(Task task) {
        try {
            List<Task> tasks = fileService.loadTasks();
            tasks.add(task);
            tasks.sort((t1, t2) -> Integer.compare(t2.getPriority(), t1.getPriority()));

            fileService.saveTasks(tasks);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateTask(Task updatedTask) {
        try {
            List<Task> tasks = fileService.loadTasks();
            for (Task task : tasks) {
                if (task.getName().equalsIgnoreCase(updatedTask.getName())) {
                    task.setDescription(updatedTask.getDescription());
                    task.setDeadline(updatedTask.getDeadline());
                    task.setPriority(updatedTask.getPriority());
                    task.setCategory(updatedTask.getCategory());
                    task.setStatus(updatedTask.getStatus());
                    task.setAlarms(updatedTask.getAlarms());

                    fileService.saveTasks(tasks);
                    return;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteTask(String name) {
        try {
            List<Task> tasks = fileService.loadTasks();
            tasks.removeIf(task -> task.getName().equalsIgnoreCase(name));
            fileService.saveTasks(tasks);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Task> getTasksByCategory(String categoryName) {
        try {
            List<Task> tasks = fileService.loadTasks();

            return tasks.stream()
                    .filter(task -> task.getCategory().getName().equalsIgnoreCase(categoryName))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Task> getTasksByPriority(Integer priority) {
        try {
            List<Task> tasks = fileService.loadTasks();

            return tasks.stream()
                    .filter(task -> task.getPriority().equals(priority))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Task> getTasksByStatus(TaskStatus status) {
        try {
            List<Task> tasks = fileService.loadTasks();

            return tasks.stream()
                    .filter(task -> task.getStatus().equals(status))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countCompletedTasks() {
        try {
            List<Task> tasks = fileService.loadTasks();

            long count = tasks.stream()
                    .filter(task -> task.getStatus() == TaskStatus.DONE)
                    .count();

            return (int) count;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countToDoTasks() {
        try {
            List<Task> tasks = fileService.loadTasks();

            long count = tasks.stream()
                    .filter(task -> task.getStatus() == TaskStatus.TODO)
                    .count();

            return (int) count;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countDoingTasks() {
        try {
            List<Task> tasks = fileService.loadTasks();

            long count = tasks.stream()
                    .filter(task -> task.getStatus() == TaskStatus.DOING)
                    .count();

            return (int) count;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Task> filterTasksByDate(LocalDate date) {
        try {
            List<Task> tasks = fileService.loadTasks();

            return tasks.stream()
                    .filter(task -> task.getDeadline().toLocalDate().isEqual(date))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Task> getTasksWithAlarms() {
        try {
            List<Task> tasks = fileService.loadTasks();

            return tasks.stream()
                    .filter(Task::hasAlarms)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
