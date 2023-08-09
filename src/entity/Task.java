package entity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Task {
    private String name;
    private String description;
    private LocalDateTime deadline;
    private int priority;
    private String category;
    private String status;
    private List<LocalDateTime>  alarms; // Lista de alarmes para a tarefa

    // Construtor

    public Task(String name, String description, LocalDateTime deadline, int priority, String category, String status) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.category = category;
        this.status = status;
        this.alarms = new ArrayList();
    }

    public boolean hasAlarms() {
        return !alarms.isEmpty();
    }

    // Método para adicionar um alarme à tarefa
    public void addAlarm(LocalDateTime alarmDateTime) {
        alarms.add(alarmDateTime);
    }

    // Método para retornar os alarmes da tarefa
    public List<LocalDateTime> getAlarms() {
        return alarms;
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

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
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
