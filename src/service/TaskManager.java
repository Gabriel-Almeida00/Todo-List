package service;

import entity.Task;
import interfaces.FileUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class TaskManager {
    public List<Task> tasks;
    private List<Task> tasksWithAlarms;

    private FileUtil fileUtil;

    public TaskManager(FileUtil fileUtil) {
        tasks = new ArrayList<>();
        tasksWithAlarms = new ArrayList<>();
        this.fileUtil = fileUtil;
        loadDataFromFile();
    }

    public void loadDataFromFile() {
        tasks = fileUtil.loadTasks();
    }

    public void saveDataToFile() {
        fileUtil.saveTasks(tasks);
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
            tasksWithAlarms.add(task);
        }
    }

    public void updateTask(String name, String newDescription, LocalDateTime newDeadline, int newPriority, String newCategory, String newStatus, boolean enableAlarm, int alarmPeriodMinutes) {
        for (Task task : tasks) {
            if (task.getName().equalsIgnoreCase(name)) {
                task.setDescription(newDescription);
                task.setDeadline(newDeadline);
                task.setPriority(newPriority);
                task.setCategory(newCategory);
                task.setStatus(newStatus);
                saveDataToFile();

                if (enableAlarm) {
                    LocalDateTime alarmDateTime = newDeadline.minusMinutes(alarmPeriodMinutes);
                    task.getAlarms().clear();
                    task.addAlarm(alarmDateTime);
                    tasksWithAlarms.add(task);
                } else {
                    task.getAlarms().clear();
                    tasksWithAlarms.remove(task);
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
