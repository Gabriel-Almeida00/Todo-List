package todo.list.services.task;

import todo.list.entity.Task;

public interface ITaskParseService {
    Task fromStringArray(String[] parts);
    String[] toStringArray(Task task);
}
