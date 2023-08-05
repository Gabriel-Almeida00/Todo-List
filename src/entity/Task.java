package entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
    private String name;
    private String description;
    private String deadline;
    private int priority;
    private String category;
    private String status;

    // Construtor
    public Task(String name, String description, String deadline, int priority, String category, String status) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.category = category;
        this.status = status;
    }

    public String getFormattedDeadline() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(deadline);
    }

    public void displayTaskDetails() {
        System.out.println("Nome: " + name);
        System.out.println("Descrição: " + description);
        System.out.println("Data de Término: " + getFormattedDeadline());
        System.out.println("Prioridade: " + priority);
        System.out.println("Categoria: " + category);
        System.out.println("Status: " + status);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
