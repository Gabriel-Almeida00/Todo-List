package todo.list.view;


import todo.list.controller.TaskController;
import todo.list.model.Alarm;
import todo.list.model.Category;
import todo.list.model.Task;
import todo.list.model.enums.AlarmType;
import todo.list.model.enums.TaskStatus;
import todo.list.observers.AlarmObserver;
import todo.list.observers.AlarmObserverRegistry;
import todo.list.services.ITaskService;

import javax.sound.midi.Soundbank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class TaskView implements AlarmObserver {
    private TaskController taskController;
    private final Scanner scanner;
    private final AlarmObserverRegistry observerRegistry;

    public TaskView(TaskController taskController , AlarmObserverRegistry observerRegistry) {
      this.taskController = taskController;

        this.observerRegistry = observerRegistry;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean exit = false;

        while (!exit) {
            checkTask(observerRegistry);
            System.out.println("=== Task Manager TaskView ===");
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

            int choice = readIntInput();

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
        List<Task> tasks = taskController.listAllTasks();

        System.out.println("=== All Tasks ===");
        for (Task task : tasks) {
            System.out.println("Name: " + task.getName());
            System.out.println("Description: " + task.getDescription());
            System.out.println("Deadline: " + task.getDeadline());
            System.out.println("Priority: " + task.getPriority());
            System.out.println("Name Category: " + task.getCategory().getName());
            System.out.println("Description Category: " + task.getCategory().getDescription());
            System.out.println("Status: " + task.getStatus());

            List<Alarm> alarms = task.getAlarms();
            if (!alarms.isEmpty()) {
                System.out.println("Alarms:");
                for (Alarm alarm : alarms) {
                    System.out.println("  - Alarm Time: " + alarm.getAlarmTime());
                    System.out.println("    Description: " + alarm.getDescription());
                    System.out.println("    Alarm Period (minutes): " + alarm.getAlarmPeriodMinutes());
                }
            }
            System.out.println();
        }

    }

    private Task createTask() {
        System.out.println();
        System.out.println("=== Add Task ===");
        System.out.print("Nome (mínimo 3 caracteres): ");
        String name = scanner.nextLine();

        System.out.print("Descrição (mínimo 3 caracteres): ");
        String description = scanner.nextLine();

        System.out.print("Data de término (formato: dd/MM/yyyy): ");
        String deadlineDateInput = scanner.nextLine();

        System.out.print("Hora de término (formato: HH:mm): ");
        String deadlineTimeInput = scanner.nextLine();

        LocalDateTime deadline = LocalDateTime.parse(
                deadlineDateInput + " " + deadlineTimeInput, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        System.out.print("Nível de prioridade (1 a 5): ");
        Integer priority = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nome da Categoria (mínimo 3 caracteres): ");
        String categoryName = scanner.nextLine();

        System.out.print("Descrição da Categoria (mínimo 3 caracteres): ");
        String categoryDescription = scanner.nextLine();

        System.out.print("Status (TODO, DOING, DONE): ");
        String statusInput = scanner.nextLine().toUpperCase();
        TaskStatus status = TaskStatus.valueOf(statusInput);

        System.out.print("Deseja adicionar um alarme? (S/N): ");
        String enableAlarmChoice = scanner.nextLine();

        List<Alarm> alarms = new ArrayList<>();

        if (enableAlarmChoice.equalsIgnoreCase("S")) {
            System.out.print("Digite a data e hora do alarme (formato: dd/MM/yyyy HH:mm): ");
            String alarmTimeInput = scanner.nextLine();
            LocalDateTime alarmTime = LocalDateTime.parse(alarmTimeInput, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

            System.out.print("Descrição do alarme: ");
            String alarmDescription = scanner.nextLine();

            System.out.print("Digite o período de antecedência do alarme (em minutos): ");
            int alarmPeriodMinutes = scanner.nextInt();
            scanner.nextLine();

            Alarm alarm = new Alarm(alarmTime, alarmDescription, alarmPeriodMinutes);
            observerRegistry.addObserver(alarm, this);
            alarms.add(alarm);
        }
        Category category = new Category(categoryName, categoryDescription);
        return new Task(
                name, description, deadline, priority, category, status, alarms
        );
    }

    private void addTask() {
        Task task = createTask();
        taskController.addTaskWithPriorityRebalance(task);
    }

    public void updateTask() {
        System.out.println("Digite o ID da tarefa que deseja atualizar: ");
        UUID taskId = UUID.fromString(scanner.nextLine());

        Task task = createTask();
        task.setId(taskId);
        taskController.updateTask(task);
    }

    private void deleteTask() {
        System.out.println("=== Delete Task ===");
        System.out.print("ID da Task: ");

        UUID taskId = UUID.fromString(scanner.nextLine());
        taskController.deleteTask(taskId);

        System.out.println("Task with ID " + taskId + " has been deleted.");
    }

    private void filterTasksByDate() {
        System.out.println("=== Filter Tasks by Date ===");
        System.out.print("Data do Filtro (formato: dd/MM/yyyy): ");
        String dateString = scanner.nextLine();

        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        List<Task> filteredTasks = taskController.filterTasksByDate(date);

        for (Task task : filteredTasks) {
            System.out.println("==========");
            System.out.println("Nome:" + task.getName());
            System.out.println("Descrição:" + task.getDescription());
            System.out.println("Data de término:" + task.getDeadline());
            System.out.println("Categoria:" + task.getCategory().getName());
            System.out.println();
        }
    }

    private void getTasksByCategory() {
        System.out.println("=== Get Tasks by Category ===");
        System.out.print("Informe o nome da categoria: ");
        String category = scanner.nextLine();

        List<Task> filteredTasks = taskController.getTasksByCategory(category);
        for (Task task : filteredTasks) {
            System.out.println("=========");
            System.out.println("Nome:" + task.getName());
            System.out.println("Descrição:" + task.getDescription());
            System.out.println("status:" + task.getStatus());
            System.out.println("Categoria:" + task.getCategory().getName());
            System.out.println();
        }
    }

    private void getTasksByPriority() {
        System.out.println("=== Get Tasks by Priority ===");
        System.out.print("Informe a prioridade (1 a 5): ");

        int priority = scanner.nextInt();
        scanner.nextLine();

        List<Task> filteredTasks = taskController.getTasksByPriority(priority);
        for (Task task : filteredTasks) {
            System.out.println("===========");
            System.out.println("Nome:" + task.getName());
            System.out.println("Descrição:" + task.getDescription());
            System.out.println("prioridade:" + task.getPriority());
            System.out.println("Categoria:" + task.getCategory().getName());
            System.out.println();
        }
    }

    private void getTasksByStatus() {
        System.out.println("=== Get Tasks by Status ===");
        System.out.print("Status (TODO, DOING, DONE): ");
        String statusInput = scanner.nextLine().toUpperCase();
        TaskStatus status = TaskStatus.valueOf(statusInput);

        List<Task> filteredTasks = taskController.getTasksByStatus(status);
        System.out.println("Tarefas com status '" + status + "':");
        for (Task task : filteredTasks) {
            System.out.println("==========");
            System.out.println("Nome:" + task.getName());
            System.out.println("Descrição:" + task.getDescription());
            System.out.println("status:" + task.getStatus());
            System.out.println("Categoria:" + task.getCategory().getName());
            System.out.println();
        }
    }

    private void countCompletedTasks() {
        System.out.println("=== Count Completed Tasks ===");
        int completedTaskCount = taskController.countCompletedTasks();
        System.out.println("Number of completed tasks: " + completedTaskCount);
    }

    private void countToDoTasks() {
        System.out.println("=== Count To-Do Tasks ===");
        int toDoTaskCount = taskController.countToDoTasks();
        System.out.println("Number of to-do tasks: " + toDoTaskCount);
    }

    private void countDoingTasks() {
        System.out.println("=== Count Doing Tasks ===");
        int doingTaskCount = taskController.countDoingTasks();
        System.out.println("Number of tasks in progress: " + doingTaskCount);
    }


    public  void checkTask(AlarmObserverRegistry observerRegistry) {
        List<Task> tasksWithAlarms = taskController.listAllTasks();
        observerRegistry.notifyObservers(tasksWithAlarms);
    }



    private int readIntInput() {
        System.out.println();
        System.out.print("Enter your choice: ");
        int input = -1;
        while (input == -1) {
            try {
                input = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                System.out.print("Enter your choice: ");
            }
        }
        return input;
    }
}
