package UI;

import entity.Task;
import service.TaskManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private TaskManager taskManager;
    private Scanner scanner;

    public Menu(TaskManager taskManager) {
        this.taskManager = taskManager;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean exit = false;

        while (!exit) {
            System.out.println("=== Task Manager Menu ===");
            System.out.println("1. List all tasks");
            System.out.println("2. Add a task");
            System.out.println("3. Update a task");
            System.out.println("4. Delete a task");
            System.out.println("5. Filter tasks by date");
            System.out.println("6. Filter tasks by Categoria");
            System.out.println("7. Filter tasks by Prioridade");
            System.out.println("8. Filter tasks by Status");
            System.out.println("9. Contar Tarefas Concluídas");
            System.out.println("10. Contar Tarefas para Fazer");
            System.out.println("11. Contar Tarefas em Andamento");
            System.out.println("12. Exit");

            int choice = readIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    listAllTasks();
                    break;
                case 2:
                    addTask();
                    break;
                case 3:
                    updateTask();
                    break;
                case 4:
                    deleteTask();
                    break;
                case 5:
                    filterTasksByDate();
                    break;
                case 6:
                    getTasksByCategory();
                    break;
                case 7:
                    getTasksByPriority();
                    break;
                case 8:
                    getTasksByStatus();
                    break;
                case 9:
                    countCompletedTasks();
                    break;
                case 10:
                    countToDoTasks();
                    break;
                case 11:
                    countDoingTasks();
                    break;
                case 12:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private void listAllTasks() {
        System.out.println();
        List<Task> tasks = taskManager.listAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            System.out.println("=== All Tasks ===");
            for (Task task : tasks) {
                System.out.println();
                System.out.println(task);
                System.out.println();
            }
        }
    }

    private void addTask() {
        System.out.println();
        System.out.println("=== Add Task ===");
        checkAlarms(taskManager);
        System.out.print("Nome: ");
        String name = scanner.nextLine();
        System.out.print("Descrição: ");
        String description = scanner.nextLine();
        System.out.print("Data de término (formato: dd/MM/yyyy): ");
        String deadlineDateInput = scanner.nextLine();
        System.out.print("Hora de término (formato: HH:mm): ");
        String deadlineTimeInput = scanner.nextLine();

        LocalDateTime deadline = LocalDateTime.parse(deadlineDateInput + " " + deadlineTimeInput,
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        System.out.print("Nível de prioridade (1 a 5): ");
        int priority = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Categoria: ");
        String category = scanner.nextLine();
        System.out.print("Status (todo, doing, done): ");
        String status = scanner.nextLine();

        Task newTask = new Task(name, description, deadline, priority, category, status);

        System.out.print("Deseja habilitar o alarme? (S/N): ");
        String enableAlarmChoice = scanner.nextLine();
        boolean enableAlarm = enableAlarmChoice.equalsIgnoreCase("S");

        if (enableAlarm) {
            System.out.print("Digite o período de antecedência do alarme (em minutos): ");
            int alarmPeriodMinutes = scanner.nextInt();
            scanner.nextLine();

            taskManager.addTaskWithPriorityRebalance(newTask, enableAlarm, alarmPeriodMinutes);
        } else {
            taskManager.addTaskWithPriorityRebalance(newTask, false, 0);
        }
    }

    public void updateTask() {
        System.out.println("=== Update Task with Alarm ===");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o nome da tarefa que deseja atualizar: ");
        String name = scanner.nextLine();

        System.out.print("Nova descrição: ");
        String newDescription = scanner.nextLine();

        System.out.print("Data de término (formato: dd/MM/yyyy): ");
        String deadlineDateInput = scanner.nextLine();
        System.out.print("Hora de término (formato: HH:mm): ");
        String deadlineTimeInput = scanner.nextLine();

        LocalDateTime deadline = LocalDateTime.parse(deadlineDateInput + " " + deadlineTimeInput,
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        System.out.print("Nova prioridade: ");
        int newPriority = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Nova categoria: ");
        String newCategory = scanner.nextLine();

        System.out.print("Novo status: ");
        String newStatus = scanner.nextLine();

        System.out.print("Deseja habilitar o alarme? (S/N): ");
        String enableAlarmChoice = scanner.nextLine();
        boolean enableAlarm = enableAlarmChoice.equalsIgnoreCase("S");

        int alarmPeriodMinutes = 0;
        if (enableAlarm) {
            System.out.print("Período do alarme em minutos: ");
            alarmPeriodMinutes = scanner.nextInt();
        }

        taskManager.updateTask(name, newDescription, deadline, newPriority, newCategory, newStatus, enableAlarm, alarmPeriodMinutes);
        System.out.println("Tarefa atualizada com sucesso!");
    }

    private void deleteTask() {
        System.out.println();
        System.out.println("=== Delete Task ===");
        System.out.println("Nome da Task: ");
        String taskName = scanner.nextLine();

        List<Task> tasks = taskManager.listAllTasks();
        boolean taskFound = false;

        for (Task task : tasks) {
            if (task.getName().equalsIgnoreCase(taskName)) {
                taskManager.deleteTask(taskName);
                System.out.println("Task '" + taskName + "' has been deleted.");
                taskFound = true;
                break;
            }
        }

        if (!taskFound) {
            System.out.println("Task '" + taskName + "' not found.");
        }
    }

    private void filterTasksByDate() {
        System.out.println();
        System.out.println("=== Filter Tasks by Date ===");
        System.out.print("Data do Filtro (formato: dd/MM/yyyy): ");
        String dateString = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        List<Task> filteredTasks = taskManager.filterTasksByDate(date);

        if (filteredTasks.isEmpty()) {
            System.out.println("No tasks found for the specified date.");
        } else {
            System.out.println("=== Tasks on " + dateString + " ===");
            for (Task task : filteredTasks) {
                System.out.println(task);
            }
        }
    }

    private int readIntInput(String message) {
        System.out.println();
        System.out.print(message);
        int input = -1;
        while (input == -1) {
            try {
                input = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                System.out.print(message);
            }
        }
        return input;
    }

    private void getTasksByCategory() {
        System.out.println();
        System.out.println("=== Get Tasks by Category ===");
        System.out.print("Enter category: ");
        String category = scanner.nextLine();

        List<Task> filteredTasks = taskManager.getTasksByCategory(category);

        if (filteredTasks.isEmpty()) {
            System.out.println("No tasks found in category '" + category + "'.");
        } else {
            System.out.println("Tasks in category '" + category + "':");
            for (Task task : filteredTasks) {
                System.out.println(task);
            }
        }
    }

    private void getTasksByPriority() {
        System.out.println();
        System.out.println("=== Get Tasks by Priority ===");
        System.out.print("Enter priority: ");
        int priority = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        List<Task> filteredTasks = taskManager.getTasksByPriority(priority);

        if (filteredTasks.isEmpty()) {
            System.out.println("No tasks found with priority " + priority + ".");
        } else {
            System.out.println("Tasks with priority " + priority + ":");
            for (Task task : filteredTasks) {
                System.out.println(task);
            }
        }
    }

    private void getTasksByStatus() {
        System.out.println();
        System.out.println("=== Get Tasks by Status ===");
        System.out.print("Enter status: ");
        String status = scanner.nextLine();

        List<Task> filteredTasks = taskManager.getTasksByStatus(status);

        if (filteredTasks.isEmpty()) {
            System.out.println("No tasks found with status '" + status + "'.");
        } else {
            System.out.println("Tasks with status '" + status + "':");
            for (Task task : filteredTasks) {
                System.out.println(task);
            }
        }
    }

    private void countCompletedTasks() {
        System.out.println();
        System.out.println("=== Count Completed Tasks ===");

        int completedTaskCount = taskManager.countCompletedTasks();

        System.out.println("Number of completed tasks: " + completedTaskCount);
    }

    private void countToDoTasks() {
        System.out.println();
        System.out.println("=== Count To-Do Tasks ===");

        int toDoTaskCount = taskManager.countToDoTasks();

        System.out.println("Number of to-do tasks: " + toDoTaskCount);
    }

    private void countDoingTasks() {
        System.out.println();
        System.out.println("=== Count Doing Tasks ===");

        int doingTaskCount = taskManager.countDoingTasks();

        System.out.println("Number of tasks in progress: " + doingTaskCount);
    }

    private static void checkAlarms(TaskManager taskManager) {
        List<Task> tasksWithAlarms = taskManager.getTasksWithAlarms();
        LocalDateTime now = LocalDateTime.now();

        for (Task task : tasksWithAlarms) {
            List<LocalDateTime> alarms = task.getAlarms();
            for (LocalDateTime alarmDateTime : alarms) {
                if (now.isEqual(alarmDateTime) || now.isAfter(alarmDateTime)) {
                    System.out.println("ALERTA: Tarefa '" + task.getName() + "' com alarme para " + alarmDateTime);
                }
            }
        }
    }
}
