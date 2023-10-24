package todo.list.observers;

import todo.list.model.Alarm;
import todo.list.model.Task;
import todo.list.model.enums.AlarmType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AlarmObserverRegistry {
    private final Map<UUID, List<AlarmObserver>> observersMap = new HashMap<>();

    public void addObserver(Alarm alarm, AlarmObserver observer) {
        UUID alarmId = alarm.getId();
        List<AlarmObserver> observers = observersMap.computeIfAbsent(alarmId, k -> new ArrayList<>());
        observers.add(observer);
    }

    public void removeObserver(Alarm alarm, AlarmObserver observer) {
        UUID alarmId = alarm.getId();
        List<AlarmObserver> observers = observersMap.get(alarmId);
        if (observers != null) {
            observers.remove(observer);
            if (observers.isEmpty()) {
                observersMap.remove(alarmId);
            }
        }
    }

    public void notifyObservers(Alarm alarm, Task task, AlarmType alarmType) {
        UUID alarmId = alarm.getId();
        List<AlarmObserver> observers = observersMap.get(alarmId);
        if (observers != null) {
            for (AlarmObserver observer : observers) {
                observer.onAlarmTriggered(task, alarm, alarmType);
            }
        }
    }
}
