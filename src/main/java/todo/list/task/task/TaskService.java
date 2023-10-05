package todo.list.task.task;

import todo.list.entity.Task;
import todo.list.entity.enums.TaskStatus;
import todo.list.task.file.IFileService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskService implements ITaskService {
    public List<Task> tasks;
    private IFileService fileUtil;

    public TaskService(IFileService fileUtil) {
        tasks = new ArrayList<>();
        this.fileUtil = fileUtil;
        loadDataFromFile();
    }

    public void loadDataFromFile() {
        try {
            tasks = fileUtil.loadTasks();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveDataToFile() {
        try {
            fileUtil.saveTasks(tasks);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Task> listAllTasks() {
        return tasks;
    }

    public void addTaskWithPriorityRebalance(Task task) {
        tasks.add(task);
        tasks.sort((t1, t2) -> Integer.compare(t2.getPriority(), t1.getPriority()));
        saveDataToFile();
    }

    public void updateTask(Task updatedTask) {
        for (Task task : tasks) {
            if (task.getName().equalsIgnoreCase(updatedTask.getName())) {
                task.setDescription(updatedTask.getDescription());
                task.setDeadline(updatedTask.getDeadline());
                task.setPriority(updatedTask.getPriority());
                task.setCategory(updatedTask.getCategory());
                task.setStatus(updatedTask.getStatus());
                task.setAlarms(updatedTask.getAlarms());
                saveDataToFile();
                return;
            }
        }
    }

    public List<Task> getTasksByCategory(String categoryName) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getCategory().getName().equalsIgnoreCase(categoryName)) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    public List<Task> getTasksByPriority(Integer priority) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getPriority().equals(priority)) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    public List<Task> getTasksByStatus(TaskStatus status) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getStatus() == status) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    public int countCompletedTasks() {
        int count = 0;
        for (Task task : tasks) {
            if (task.getStatus() == TaskStatus.DONE) {
                count++;
            }
        }
        return count;
    }

    public int countToDoTasks() {
        int count = 0;
        for (Task task : tasks) {
            if (task.getStatus() == TaskStatus.TODO) {
                count++;
            }
        }
        return count;
    }

    public int countDoingTasks() {
        int count = 0;
        for (Task task : tasks) {
            if (task.getStatus() == TaskStatus.DOING) {
                count++;
            }
        }
        return count;
    }

    public void deleteTask(String name) {
        tasks.removeIf(task -> task.getName().equalsIgnoreCase(name));
        saveDataToFile();
    }

    public List<Task> filterTasksByDate(LocalDate date) {
        return tasks.stream()
                .filter(task -> task.getDeadline().toLocalDate().isEqual(date))
                .collect(Collectors.toList());
    }
}
