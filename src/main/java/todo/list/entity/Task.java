package todo.list.entity;

import todo.list.entity.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

public class Task {
    private static  int nextId = 1;
    private Integer id;
    private final String name;
    private String description;
    private LocalDateTime deadline;
    private Integer priority;
    private Category category;
    private TaskStatus status;
    private List<Alarm> alarms;

    public Task(
            String name,
            String description,
            LocalDateTime deadline,
            Integer priority,
            Category category,
            TaskStatus status,
            List<Alarm> alarms
    ) {
        this.id = nextId++;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.category = category;
        this.status = status;
        this.alarms = alarms;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
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

    public List<Alarm> getAlarms() {
        return alarms;
    }
    public boolean hasAlarms() {
        return !alarms.isEmpty();
    }
}
