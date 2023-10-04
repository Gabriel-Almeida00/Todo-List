package todo.list.task.task;

import todo.list.entity.Alarm;
import todo.list.entity.Category;
import todo.list.entity.Task;
import todo.list.entity.enums.TaskStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TaskParse implements ITaskParse {
    public  final int NUM_FIELDS = 8;

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

        String alarmString = parts[7].trim();
        String[] alarmParts = alarmString.split(",");
        List<Alarm> alarms = parseAlarms(alarmParts);

        Category category = new Category(categoryName, categoryDescription);

        return new Task(name, description, deadline, priority, category, status, alarms);
    }

    private List<Alarm> parseAlarms(String[] alarmParts) {
        List<Alarm> alarms = new ArrayList<>();

        for (int i = 0; i < alarmParts.length - 2; i += 3) {
            LocalDateTime alarmTime = LocalDateTime.parse(alarmParts[i].trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            String alarmDescription = alarmParts[i + 1].trim();
            int alarmPeriodMinutes = Integer.parseInt(alarmParts[i + 2].trim());

            Alarm alarm = new Alarm(alarmTime, alarmDescription, alarmPeriodMinutes);
            alarms.add(alarm);
        }
        return alarms;
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
        parts[7] = formatAlarms(task.getAlarms());

        return parts;
    }

    private String formatAlarms(List<Alarm> alarms) {
        StringBuilder sb = new StringBuilder();

        for (Alarm alarm : alarms) {
            LocalDateTime alarmTime = alarm.getAlarmTime();
            String alarmTimeString = alarmTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            sb.append(alarmTimeString).append(",");
            sb.append(alarm.getDescription()).append(",");
            sb.append(alarm.getAlarmPeriodMinutes()).append(",");
        }

        return sb.toString();
    }

}
