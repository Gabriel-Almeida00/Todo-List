package service;

import entity.Task;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TaskManager {
    public List<Task> tasks;
    private List<Task> tasksWithAlarms;

    private FileUtil dataReader;

    public TaskManager( FileUtil fileService) {
        tasks = new ArrayList<>();
        tasksWithAlarms = new ArrayList<>();
        this.dataReader = fileService;
        loadDataFromFile();
    }

    public void loadDataFromFile() {
        List<String> lines = dataReader.readData();

        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 6) {
                String name = parts[0].trim();
                String description = parts[1].trim();
                LocalDateTime deadline = LocalDateTime.parse(parts[2].trim());
                int priority = Integer.parseInt(parts[3].trim());
                String category = parts[4].trim();
                String status = parts[5].trim();
                Task task = new Task(name, description, deadline, priority, category, status);
                tasks.add(task);
            }
        }
    }

    public void saveDataToFile() {
        List<String> lines = new ArrayList<>();
        for (Task task : tasks) {
            lines.add(task.getName() + "," + task.getDescription() + "," + task.getDeadline() + ","
                    + task.getPriority() + "," + task.getCategory() + "," + task.getStatus());
        }
        dataReader.writeData(lines);
    }

    public List<Task> listAllTasks(){
        return tasks;
    }


    // Adicionar uma nova tarefa
    public void addTask(Task task) {
        tasks.add(task);
        saveDataToFile();
    }

    // Atualizar tarefa por nome
    public void updateTask(String name, String newDescription, LocalDateTime newDeadline, int newPriority, String newCategory, String newStatus) {
        for (Task task : tasks) {
            if (task.getName().equalsIgnoreCase(name)) {
                task.setDescription(newDescription);
                task.setDeadline(newDeadline);
                task.setPriority(newPriority);
                task.setCategory(newCategory);
                task.setStatus(newStatus);
                saveDataToFile();
                return;
            }
        }
        System.out.println("Tarefa não encontrada.");
    }

    // Listar tarefas por categoria
    public List<Task> getTasksByCategory(String category) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getCategory().equalsIgnoreCase(category)) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    // Listar tarefas por prioridade
    public List<Task> getTasksByPriority(int priority) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getPriority() == priority) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    // Listar tarefas por status
    public List<Task> getTasksByStatus(String status) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getStatus().equalsIgnoreCase(status)) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    // Contar tarefas concluídas
    public int countCompletedTasks() {
        int count = 0;
        for (Task task : tasks) {
            if (task.getStatus().equalsIgnoreCase("done")) {
                count++;
            }
        }
        return count;
    }

    // Contar tarefas a fazer
    public int countToDoTasks() {
        int count = 0;
        for (Task task : tasks) {
            if (task.getStatus().equalsIgnoreCase("todo")) {
                count++;
            }
        }
        return count;
    }

    // Contar tarefas em andamento
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
