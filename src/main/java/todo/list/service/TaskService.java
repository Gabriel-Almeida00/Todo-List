package todo.list.service;

import todo.list.entity.Task;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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

    public void loadDataFromFile()  {
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


    public List<Task> listAllTasks(){
        return tasks;
    }

    public void addTaskWithPriorityRebalance(Task task, boolean enableAlarm, int alarmPeriodMinutes) {
        tasks.add(task);

        Collections.sort(tasks, (t1, t2) -> Integer.compare(t2.getPriority(), t1.getPriority()));

        saveDataToFile();

        if (enableAlarm) {
            LocalDateTime alarmDateTime = task.getDeadline().minusMinutes(alarmPeriodMinutes);
            task.addAlarm(alarmDateTime);
        }
    }

    public void updateTask(Task updatedTask, boolean enableAlarm, int alarmPeriodMinutes) {
        for (Task task : tasks) {
            if (task.getName().equalsIgnoreCase(updatedTask.getName())) {
                task.setDescription(updatedTask.getDescription());
                task.setDeadline(updatedTask.getDeadline());
                task.setPriority(updatedTask.getPriority());
                task.setCategory(updatedTask.getCategory());
                task.setStatus(updatedTask.getStatus());
                saveDataToFile();

                if (enableAlarm) {
                    LocalDateTime alarmDateTime = updatedTask.getDeadline().minusMinutes(alarmPeriodMinutes);
                    task.getAlarms().clear();
                    task.addAlarm(alarmDateTime);
                } else {
                    task.getAlarms().clear();
                }

                return;
            }
        }
        System.out.println("Tarefa não encontrada.");
    }


    public List<Task> getTasksByCategory(String category) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getCategory().equalsIgnoreCase(category)) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    public List<Task> getTasksByPriority(int priority) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getPriority() == priority) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    public List<Task> getTasksByStatus(String status) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getStatus().equalsIgnoreCase(status)) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    public int countCompletedTasks() {
        int count = 0;
        for (Task task : tasks) {
            if (task.getStatus().equalsIgnoreCase("done")) {
                count++;
            }
        }
        return count;
    }

    public int countToDoTasks() {
        int count = 0;
        for (Task task : tasks) {
            if (task.getStatus().equalsIgnoreCase("todo")) {
                count++;
            }
        }
        return count;
    }

    public int countDoingTasks() {
        int count = 0;
        for (Task task : tasks) {
            if (task.getStatus().equalsIgnoreCase("doing")) {
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

    public List<Task> getTasksWithAlarms() {
        List<Task> tasksWithAlarms = new ArrayList<>();
        for (Task task : tasks) {
            if (task.hasAlarms()) {
                tasksWithAlarms.add(task);
            }
        }
        return tasksWithAlarms;
    }
}
