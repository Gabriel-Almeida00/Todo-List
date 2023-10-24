package todo.list.view;

import java.util.Scanner;

public class ViewTelegram {
    public TaskView taskView;
    private  Scanner scanner;

    public ViewTelegram(TaskView taskView) {
        this.taskView = taskView;
    }

    public void exibirMenu() {
        this.scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("=== Task Manager TaskView ===");
            System.out.println("1. Configurar notificação ");
            System.out.println("2. sair ");


            int choice = readIntInput();

            switch (choice) {
                case 1:
                    receberNotificacao();
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

    public void receberNotificacao(){
        System.out.println("Para receber notificação sobre suas tarefas manda uma mensagem em nosso telegram");
        System.out.println("https://web.telegram.org/k/#@TodoListBotBotBot");
        System.out.println("Digite 'S' se consguiu enivar a mensagem: ");
        String aceite = scanner.nextLine();

        if(aceite.equalsIgnoreCase("S")){
            this.taskView.start();
        }

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

