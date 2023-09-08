package todo.list.UI;


import todo.list.entity.Task;
import todo.list.service.TaskManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
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

        String name;
        do {
            System.out.print("Nome (mínimo 3 caracteres): ");
            name = scanner.nextLine();
        } while (name.length() < 3);

        String description;
        do {
            System.out.print("Descrição(mínimo 3 caracteres): ");
             description = scanner.nextLine();
        } while (description.length() < 3);

        String deadlineDateInput;
        String deadlineTimeInput;
        LocalDateTime deadline;
        do {
            System.out.print("Data de término (formato: dd/MM/yyyy): ");
            deadlineDateInput = scanner.nextLine();
            System.out.print("Hora de término (formato: HH:mm): ");
            deadlineTimeInput = scanner.nextLine();

            try {
                deadline = LocalDateTime.parse(deadlineDateInput + " " + deadlineTimeInput,
                        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data ou hora inválido. Tente novamente.");
            }
        } while (true);

        int priority;
        do {
            System.out.print("Nível de prioridade (1 a 5): ");
            priority = scanner.nextInt();
            scanner.nextLine();
        } while (priority < 1 || priority > 5);

        String category;
        do {
            System.out.print("Categoria(mínimo 3 caracteres): ");
            category = scanner.nextLine();
        } while (category.length() <= 3);

        String status;
        do {
            System.out.print("Status (todo, doing, done): ");
            status = scanner.nextLine().toLowerCase();
        } while (!Arrays.asList("todo", "doing", "done").contains(status));

        Task newTask = new Task(name, description, deadline, priority, category, status);

        System.out.print("Deseja habilitar o alarme? (S/N): ");
        String enableAlarmChoice = scanner.nextLine();
        boolean enableAlarm = enableAlarmChoice.equalsIgnoreCase("S");

        if (enableAlarm) {
            int alarmPeriodMinutes;
            do {
                System.out.print("Digite o período de antecedência do alarme (em minutos): ");
                alarmPeriodMinutes = scanner.nextInt();
                scanner.nextLine();
            } while (alarmPeriodMinutes <= 0);

            taskManager.addTaskWithPriorityRebalance(newTask, enableAlarm, alarmPeriodMinutes);
        } else {
            taskManager.addTaskWithPriorityRebalance(newTask, false, 0);
        }
    }
    public void updateTask() {
        System.out.println("=== Update Task with Alarm ===");
        Scanner scanner = new Scanner(System.in);

        String name;
        do {
            System.out.print("Digite o nome da tarefa que deseja atualizar: ");
            name = scanner.nextLine();
        } while (name.isEmpty());

        String newDescripton;
        do {
            System.out.print("Nova descrição: ");
            newDescripton = scanner.nextLine();
        } while (newDescripton.isEmpty());

        String deadlineDateInput;
        String deadlineTimeInput;
        LocalDateTime deadline;
        do {
            System.out.print("Data de término (formato: dd/MM/yyyy): ");
            deadlineDateInput = scanner.nextLine();
            System.out.print("Hora de término (formato: HH:mm): ");
            deadlineTimeInput = scanner.nextLine();

            try {
                deadline = LocalDateTime.parse(deadlineDateInput + " " + deadlineTimeInput,
                        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data ou hora inválido. Tente novamente.");
            }
        } while (true);

        int newPriority;
        do {
            System.out.print("Nova prioridade (1 a 5): ");
            newPriority = scanner.nextInt();
            scanner.nextLine();
        } while (newPriority < 1 || newPriority > 5);

        String newCategory;
        do{
            System.out.print("Nova categoria: ");
             newCategory = scanner.nextLine();
        }while (newDescripton.isEmpty());


        String newStatus;
        do {
            System.out.print("Novo status (todo, doing, done): ");
            newStatus = scanner.nextLine().toLowerCase();
        } while (!Arrays.asList("todo", "doing", "done").contains(newStatus));

        System.out.print("Deseja habilitar o alarme? (S/N): ");
        String enableAlarmChoice = scanner.nextLine();
        boolean enableAlarm = enableAlarmChoice.equalsIgnoreCase("S");

        int alarmPeriodMinutes = 0;
        if (enableAlarm) {
            do {
                System.out.print("Período do alarme em minutos (maior que 0): ");
                alarmPeriodMinutes = scanner.nextInt();
                scanner.nextLine();
            } while (alarmPeriodMinutes <= 0);
        }

        Task newTask = new Task(name, newDescripton, deadline, newPriority, newCategory, newStatus);
        taskManager.updateTask(newTask, enableAlarm, alarmPeriodMinutes);
        System.out.println("Tarefa atualizada com sucesso!");
    }

    private void deleteTask() {
        System.out.println();
        System.out.println("=== Delete Task ===");

        String taskName;
        do {
            System.out.print("Nome da Task: ");
            taskName = scanner.nextLine();
        } while (taskName.isEmpty());

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

        String dateString;
        LocalDate date = null;
        do {
            System.out.print("Data do Filtro (formato: dd/MM/yyyy): ");
            dateString = scanner.nextLine();
            try {
                date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida. Insira novamente.");
            }
        } while (date == null);

        List<Task> filteredTasks = taskManager.filterTasksByDate(date);

        if (filteredTasks.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada para a data especificada.");
        } else {
            System.out.println("=== Tarefas em " + dateString + " ===");
            for (Task task : filteredTasks) {
                System.out.println(task);
            }
        }
    }


    private void getTasksByCategory() {
        System.out.println();
        System.out.println("=== Get Tasks by Category ===");

        String category;
        do {
            System.out.print("Informe a categoria: ");
            category = scanner.nextLine();
            if (category.trim().isEmpty()) {
                System.out.println("Categoria não pode estar vazia. Insira novamente.");
            }
        } while (category.trim().isEmpty());

        List<Task> filteredTasks = taskManager.getTasksByCategory(category);

        if (filteredTasks.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada na categoria '" + category + "'.");
        } else {
            System.out.println("Tarefas na categoria '" + category + "':");
            for (Task task : filteredTasks) {
                System.out.println(task);
            }
        }
    }

    private void getTasksByPriority() {
        System.out.println();
        System.out.println("=== Get Tasks by Priority ===");

        int priority;
        do {
            System.out.print("Informe a prioridade (1 a 5): ");
            if (scanner.hasNextInt()) {
                priority = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                if (priority < 1 || priority > 5) {
                    System.out.println("Prioridade deve estar entre 1 e 5. Insira novamente.");
                }
            } else {
                System.out.println("Entrada inválida. Insira novamente.");
                scanner.nextLine();
                priority = -1;
            }
        } while (priority < 1 || priority > 5);

        List<Task> filteredTasks = taskManager.getTasksByPriority(priority);

        if (filteredTasks.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada com prioridade " + priority + ".");
        } else {
            System.out.println("Tarefas com prioridade " + priority + ":");
            for (Task task : filteredTasks) {
                System.out.println(task);
            }
        }
    }
    private void getTasksByStatus() {
        System.out.println();
        System.out.println("=== Get Tasks by Status ===");

        System.out.print("Informe o status (todo, doing, done): ");
        String status = scanner.nextLine().trim().toLowerCase();

        if (status.equals("todo") || status.equals("doing") || status.equals("done")) {
            List<Task> filteredTasks = taskManager.getTasksByStatus(status);

            if (filteredTasks.isEmpty()) {
                System.out.println("Nenhuma tarefa encontrada com status '" + status + "'.");
            } else {
                System.out.println("Tarefas com status '" + status + "':");
                for (Task task : filteredTasks) {
                    System.out.println(task);
                }
            }
        } else {
            System.out.println("Status inválido. Opções válidas: todo, doing, done.");
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
