package todo.list.service;

import todo.list.entity.Task;

public interface ITaskParse {
    Task fromStringArray(String[] parts);
    String[] toStringArray(Task task);
}
