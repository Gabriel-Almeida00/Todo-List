package todo.list.services.task;

import todo.list.entity.Alarm;
import todo.list.entity.Category;
import todo.list.entity.Task;
import todo.list.entity.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskParseService implements ITaskParseService {
    public final int NUM_FIELDS = 12;

    @Override
    public Task fromStringArray(String[] parts) {
        if (parts.length != NUM_FIELDS) {
            throw new IllegalArgumentException("Número incorreto de campos");
        }
        UUID id = UUID.fromString(parts[0].trim());
        String name = parts[1].trim();
        String description = parts[2].trim();
        LocalDateTime deadline = LocalDateTime.parse(parts[3].trim());
        Integer priority = Integer.parseInt(parts[4].trim());
        String categoryName = parts[5].trim();
        String categoryDescription = parts[6].trim();
        TaskStatus status = TaskStatus.valueOf(parts[7].trim().toUpperCase());

        UUID idAlarm = UUID.fromString(parts[8].trim());
        LocalDateTime alarmTime = LocalDateTime.parse(parts[9].trim());
        String descriptionAlarm = parts[10].trim();
        Integer alarmPeriodMinutes = Integer.parseInt(parts[11].trim());

        Alarm alarm = new Alarm(alarmTime, descriptionAlarm, alarmPeriodMinutes);
        alarm.setId(idAlarm);
        List<Alarm> alarms = new ArrayList<>();
        alarms.add(alarm);

        Category category = new Category(categoryName, categoryDescription);
        Task task = new Task(name, description, deadline, priority, category, status, alarms);
        task.setId(id);
        return task;
    }

    @Override
    public String[] toStringArray(Task task) {
        String[] parts = new String[NUM_FIELDS];
        parts[0] = String.valueOf(task.getId());
        parts[1] = task.getName();
        parts[2] = task.getDescription();
        parts[3] = task.getDeadline().toString();
        parts[4] = String.valueOf(task.getPriority());
        parts[5] = task.getCategory().getName();
        parts[6] = task.getCategory().getDescription();
        parts[7] = task.getStatus().toString();

        for (Alarm alarm : task.getAlarms()) {
            parts[8] = String.valueOf(alarm.getId());
            parts[9] = alarm.getAlarmTime().toString();
            parts[10] = alarm.getDescription();
            parts[11] = String.valueOf(alarm.getAlarmPeriodMinutes());
        }
        return parts;
    }
}
