package todo.list.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task {
    private String name;
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
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.category = category;
        this.status = status;
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

}
