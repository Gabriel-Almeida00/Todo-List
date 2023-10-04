package todo.list.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Alarm {
    private LocalDateTime alarmTime;
    private String description;
    private Integer alarmPeriodMinutes;

    public Alarm(LocalDateTime alarmTime, String description, Integer alarmPeriodMinutes) {
        this.alarmTime = alarmTime;
        this.description = description;
        this.alarmPeriodMinutes = alarmPeriodMinutes;
    }

    public LocalDateTime getAlarmTime() {
        return alarmTime;
    }

    public String getDescription() {
        return description;
    }

    public Integer getAlarmPeriodMinutes() {
        return alarmPeriodMinutes;
    }
}
