package todo.list.service;

import todo.list.entity.Task;

import java.time.LocalDateTime;

public class TaskParse implements ITaskParse {
    public  final int NUM_FIELDS = 6;

    @Override
    public Task fromStringArray(String[] parts) {
        if (parts.length != NUM_FIELDS) {
            throw new IllegalArgumentException("Número incorreto de campos");
        }

        String name = parts[0].trim();
        String description = parts[1].trim();
        LocalDateTime deadline = LocalDateTime.parse(parts[2].trim());
        int priority = Integer.parseInt(parts[3].trim());
        String category = parts[4].trim();
        String status = parts[5].trim();

        return new Task(name, description, deadline, priority, category, status);
    }

    @Override
    public String[] toStringArray(Task task) {
        String[] parts = new String[NUM_FIELDS];
        parts[0] = task.getName();
        parts[1] = task.getDescription();
        parts[2] = task.getDeadline().toString();
        parts[3] = String.valueOf(task.getPriority());
        parts[4] = task.getCategory();
        parts[5] = task.getStatus();

        return parts;
    }
}
