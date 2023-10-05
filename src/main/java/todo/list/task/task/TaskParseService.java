package todo.list.task.task;

import todo.list.entity.Alarm;
import todo.list.entity.Category;
import todo.list.entity.Task;
import todo.list.entity.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskParseService implements ITaskParseService {
    public  final int NUM_FIELDS = 10;

    @Override
    public Task fromStringArray(String[] parts) {
        if (parts.length != NUM_FIELDS) {
            throw new IllegalArgumentException("Número incorreto de campos");
        }

        String name = parts[0].trim();
        String description = parts[1].trim();
        LocalDateTime deadline = LocalDateTime.parse(parts[2].trim());
        Integer priority = Integer.parseInt(parts[3].trim());
        String categoryName = parts[4].trim();
        String categoryDescription = parts[5].trim();
        TaskStatus status = TaskStatus.valueOf(parts[6].trim().toUpperCase());

        LocalDateTime alarmTime = LocalDateTime.parse(parts[7].trim());
        String descriptionAlarm = parts[8].trim();
        Integer alarmPeriodMinutes = Integer.parseInt(parts[9].trim());

        Alarm alarm = new Alarm(alarmTime, descriptionAlarm, alarmPeriodMinutes);
        List<Alarm> alarms = new ArrayList<>();
        alarms.add(alarm);

        Category category = new Category(categoryName, categoryDescription);

        return new Task(name, description, deadline, priority, category, status, alarms);
    }



    @Override
    public String[] toStringArray(Task task) {
        String[] parts = new String[NUM_FIELDS];
        parts[0] = task.getName();
        parts[1] = task.getDescription();
        parts[2] = task.getDeadline().toString();
        parts[3] = String.valueOf(task.getPriority());
        parts[4] = task.getCategory().getName();
        parts[5] = task.getCategory().getDescription();
        parts[6] = task.getStatus().toString();

        for (Alarm alarm : task.getAlarms()) {
            parts[7] = alarm.getAlarmTime().toString();
            parts[8] = alarm.getDescription();
            parts[9] = String.valueOf(alarm.getAlarmPeriodMinutes());
        }


        return parts;
    }
}
