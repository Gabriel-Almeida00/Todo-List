package service;

import entity.Task;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TaskManager {
    private List<Task> tasks;
    private List<Task> tasksWithAlarms;
    private final String dataFilePath = "/home/gabriel/IdeaProjects/todo-list\n";

    public TaskManager() {
        tasks = new ArrayList<>();
        tasksWithAlarms = new ArrayList<>();
        File file = new File(dataFilePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        loadDataFromFile();
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
        return tasksWithAlarms; // Retorna a lista de tarefas com alarmes
    }

    // Carregar dados do arquivo
    private void loadDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
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
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    // Salvar dados no arquivo
    private void saveDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFilePath))) {
            for (Task task : tasks) {
                writer.write(task.getName() + "," + task.getDescription() + "," + task.getDeadline() + ","
                        + task.getPriority() + "," + task.getCategory() + "," + task.getStatus() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
