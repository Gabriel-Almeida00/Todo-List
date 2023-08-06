import entity.Task;
import service.TaskManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class TodoListApp {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("MENU:");
            System.out.println("1. Adicionar Tarefa");
            System.out.println("2. Listar Tarefas por Categoria");
            System.out.println("3. Listar Tarefas por Prioridade");
            System.out.println("4. Listar Tarefas por Status");
            System.out.println("5. Atualizar Tarefa");
            System.out.println("6. Contar Tarefas Concluídas");
            System.out.println("7. Contar Tarefas para Fazer");
            System.out.println("8. Contar Tarefas em Andamento");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            switch (choice) {
                case 1:
                    checkAlarms(taskManager);
                    // Adicionar Tarefa
                    System.out.print("Nome: ");
                    String name = scanner.nextLine();
                    System.out.print("Descrição: ");
                    String description = scanner.nextLine();
                    System.out.print("Data de término (formato: dd/MM/yyyy): ");
                    String deadlineDateInput = scanner.nextLine();
                    System.out.print("Hora de término (formato: HH:mm): ");
                    String deadlineTimeInput = scanner.nextLine();

                    // Criar objeto LocalDateTime com data, hora e minuto definidos
                    LocalDateTime deadline = LocalDateTime.parse(deadlineDateInput + " " + deadlineTimeInput,
                            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                    System.out.print("Nível de prioridade (1 a 5): ");
                    int priority = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer
                    System.out.print("Categoria: ");
                    String category = scanner.nextLine();
                    System.out.print("Status (todo, doing, done): ");
                    String status = scanner.nextLine();

                    Task newTask = new Task(name, description, deadline, priority, category, status);

                    // Opção para habilitar alarme
                    System.out.print("Deseja habilitar o alarme? (S/N): ");
                    String enableAlarmChoice = scanner.nextLine();
                    boolean enableAlarm = enableAlarmChoice.equalsIgnoreCase("S");

                    if (enableAlarm) {
                        System.out.print("Digite o período de antecedência do alarme (em minutos): ");
                        int alarmPeriodMinutes = scanner.nextInt();
                        scanner.nextLine(); // Limpar o buffer

                        taskManager.addTaskWithPriorityRebalance(newTask, enableAlarm, alarmPeriodMinutes);
                    } else {
                        taskManager.addTaskWithPriorityRebalance(newTask, false, 0);
                    }
                    break;

                case 2:
                    // Listar Tarefas por Categoria
                    System.out.print("Digite a categoria: ");
                    String categoryFilter = scanner.nextLine();
                    List<Task> tasksByCategory = taskManager.getTasksByCategory(categoryFilter);
                    displayTasks(tasksByCategory);
                    break;

                case 3:
                    // Listar Tarefas por Prioridade
                    System.out.print("Digite a prioridade: ");
                    int priorityFilter = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer
                    List<Task> tasksByPriority = taskManager.getTasksByPriority(priorityFilter);
                    displayTasks(tasksByPriority);
                    break;

                case 4:
                    // Listar Tarefas por Status
                    System.out.print("Digite o status (todo, doing, done): ");
                    String statusFilter = scanner.nextLine();
                    List<Task> tasksByStatus = taskManager.getTasksByStatus(statusFilter);
                    displayTasks(tasksByStatus);
                    break;

                case 5:
                    // Atualizar Tarefa
                    System.out.print("Digite o nome da tarefa que deseja atualizar: ");
                    String taskNameToUpdate = scanner.nextLine();
                    System.out.print("Nova descrição: ");
                    String newDescription = scanner.nextLine();
                    System.out.print("Nova data de término (formato: yyyy-MM-dd HH:mm): ");
                    LocalDateTime newDeadline = LocalDateTime.parse(scanner.nextLine());
                    System.out.print("Nova prioridade (1 a 5): ");
                    int newPriority = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer
                    System.out.print("Nova categoria: ");
                    String newCategory = scanner.nextLine();
                    System.out.print("Novo status (todo, doing, done): ");
                    String newStatus = scanner.nextLine();
                    taskManager.updateTask(taskNameToUpdate, newDescription, newDeadline, newPriority, newCategory, newStatus);
                    break;

                case 6:
                    // Contar Tarefas Concluídas
                    System.out.println("Tarefas concluídas: " + taskManager.countCompletedTasks());
                    break;

                case 7:
                    // Contar Tarefas para Fazer
                    System.out.println("Tarefas para fazer: " + taskManager.countToDoTasks());
                    break;

                case 8:
                    // Contar Tarefas em Andamento
                    System.out.println("Tarefas em andamento: " + taskManager.countDoingTasks());
                    break;

                case 0:
                    // Sair do programa
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (choice != 0);

        scanner.close();
    }

    private static void displayTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
        } else {
            for (Task task : tasks) {
                System.out.println(task.getName() + " - " + task.getDescription() + " - "
                        + task.getDeadline() + " - " + task.getPriority() + " - "
                        + task.getCategory() + " - " + task.getStatus());
            }
        }
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

