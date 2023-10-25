package todo.list.model;

import todo.list.model.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Task {
    private UUID id;
    private  String name;
    private String description;
    private LocalDateTime deadline;
    private Integer priority;
    private Category category;
    private TaskStatus status;
    private List<Alarm> alarms;

    public Task() {
    }

    public Task(
            String name,
            String description,
            LocalDateTime deadline,
            Integer priority,
            Category category,
            TaskStatus status,
            List<Alarm> alarms
    ) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.category = category;
        this.status = status;
        this.alarms = alarms;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public Integer getPriority() {
        return priority;
    }

    public Category getCategory() {
        return category;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public List<Alarm> getAlarms() {
        return alarms;
    }
    public boolean hasAlarms() {
        return !alarms.isEmpty();
    }
}
