import entity.Task;
import service.TaskManager;

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
                    // Adicionar Tarefa
                    System.out.print("Nome: ");
                    String name = scanner.nextLine();
                    System.out.print("Descrição: ");
                    String description = scanner.nextLine();
                    System.out.print("Data de término: ");
                    String deadline = scanner.nextLine();
                    System.out.print("Nível de prioridade (1 a 5): ");
                    int priority = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer
                    System.out.print("Categoria: ");
                    String category = scanner.nextLine();
                    System.out.print("Status (todo, doing, done): ");
                    String status = scanner.nextLine();

                    Task newTask = new Task(name, description, deadline, priority, category, status);
                    taskManager.addTask(newTask);
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
                    System.out.print("Nova data de término: ");
                    String newDeadline = scanner.nextLine();
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
}

