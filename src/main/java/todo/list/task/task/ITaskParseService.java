package todo.list.task.task;

import todo.list.entity.Task;

public interface ITaskParseService {
    Task fromStringArray(String[] parts);
    String[] toStringArray(Task task);
}
