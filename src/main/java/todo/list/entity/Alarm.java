package todo.list.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Alarm {
    private List<LocalDateTime> alarmTimes;
    private String description;
    private boolean isActive;

    public Alarm(List<LocalDateTime> alarmTimes, String description) {
        this.alarmTimes = alarmTimes;
        this.description = description;
        this.isActive = true;
    }

}
