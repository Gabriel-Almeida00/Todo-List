package todo.list.UI;


import todo.list.entity.Task;
import todo.list.service.task.TaskService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private TaskService taskManager;
    private Scanner scanner;

    public Menu(TaskService taskManager) {
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
        checkAlarms(taskManager);
        System.out.println("=== Add Task ===");
        System.out.print("Nome (mínimo 3 caracteres): ");
        String name = scanner.nextLine();

        System.out.print("Descrição(mínimo 3 caracteres): ");
        String description = scanner.nextLine();

        System.out.print("Data de término (formato: dd/MM/yyyy): ");
        String deadlineDateInput = scanner.nextLine();

        System.out.print("Hora de término (formato: HH:mm): ");
        String deadlineTimeInput = scanner.nextLine();

        LocalDateTime deadline = LocalDateTime.parse(deadlineDateInput + " " + deadlineTimeInput, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        System.out.print("Nível de prioridade (1 a 5): ");
        int priority = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Categoria(mínimo 3 caracteres): ");
        String category = scanner.nextLine();

        System.out.print("Status (todo, doing, done): ");
        String status = scanner.nextLine().toLowerCase();

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
        String newDescripton = scanner.nextLine();

        System.out.print("Data de término (formato: dd/MM/yyyy): ");
        String deadlineDateInput = scanner.nextLine();

        System.out.print("Hora de término (formato: HH:mm): ");
        String deadlineTimeInput = scanner.nextLine();

        LocalDateTime deadline = LocalDateTime.parse(
                deadlineDateInput + " " + deadlineTimeInput, DateTimeFormatter
                        .ofPattern("dd/MM/yyyy HH:mm"));

        System.out.print("Nova prioridade (1 a 5): ");
        int newPriority = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nova categoria: ");
        String newCategory = scanner.nextLine();

        System.out.print("Novo status (todo, doing, done): ");
        String newStatus = scanner.nextLine().toLowerCase();

        System.out.print("Deseja habilitar o alarme? (S/N): ");
        String enableAlarmChoice = scanner.nextLine();
        boolean enableAlarm = enableAlarmChoice.equalsIgnoreCase("S");

        System.out.print("Período do alarme em minutos (maior que 0): ");
        int alarmPeriodMinutes = scanner.nextInt();
        scanner.nextLine();

        Task newTask = new Task(name, newDescripton, deadline, newPriority, newCategory, newStatus);
        taskManager.updateTask(newTask, enableAlarm, alarmPeriodMinutes);
        System.out.println("Tarefa atualizada com sucesso!");
    }

    private void deleteTask() {
        System.out.println("=== Delete Task ===");

        System.out.print("Nome da Task: ");
        String taskName = scanner.nextLine();

        List<Task> tasks = taskManager.listAllTasks();

        for (Task task : tasks) {
            if (task.getName().equalsIgnoreCase(taskName)) {
                taskManager.deleteTask(taskName);
                System.out.println("Task '" + taskName + "' has been deleted.");

            }
        }
    }

    private void filterTasksByDate() {
        System.out.println("=== Filter Tasks by Date ===");
        System.out.print("Data do Filtro (formato: dd/MM/yyyy): ");
        String dateString = scanner.nextLine();

        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        List<Task> filteredTasks = taskManager.filterTasksByDate(date);

        for (Task task : filteredTasks) {
            System.out.println(task);
        }
    }

    private void getTasksByCategory() {
        System.out.println("=== Get Tasks by Category ===");
        System.out.print("Informe a categoria: ");
        String category = scanner.nextLine();

        List<Task> filteredTasks = taskManager.getTasksByCategory(category);
        for (Task task : filteredTasks) {
            System.out.println(task);
        }
    }

    private void getTasksByPriority() {
        System.out.println("=== Get Tasks by Priority ===");
        System.out.print("Informe a prioridade (1 a 5): ");

        int priority = scanner.nextInt();
        scanner.nextLine();

        List<Task> filteredTasks = taskManager.getTasksByPriority(priority);
        for (Task task : filteredTasks) {
            System.out.println(task);
        }
    }

    private void getTasksByStatus() {
        System.out.println("=== Get Tasks by Status ===");
        System.out.print("Informe o status (todo, doing, done): ");
        String status = scanner.nextLine().trim().toLowerCase();

        List<Task> filteredTasks = taskManager.getTasksByStatus(status);
        System.out.println("Tarefas com status '" + status + "':");
        for (Task task : filteredTasks) {
            System.out.println(task);
        }
    }

    private void countCompletedTasks() {
        System.out.println("=== Count Completed Tasks ===");
        int completedTaskCount = taskManager.countCompletedTasks();
        System.out.println("Number of completed tasks: " + completedTaskCount);
    }

    private void countToDoTasks() {
        System.out.println("=== Count To-Do Tasks ===");
        int toDoTaskCount = taskManager.countToDoTasks();
        System.out.println("Number of to-do tasks: " + toDoTaskCount);
    }

    private void countDoingTasks() {
        System.out.println("=== Count Doing Tasks ===");
        int doingTaskCount = taskManager.countDoingTasks();
        System.out.println("Number of tasks in progress: " + doingTaskCount);
    }

    private static void checkAlarms(TaskService taskManager) {
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
}
