package todo.list.data;



import todo.list.model.Task;

import java.io.IOException;
import java.util.List;

public interface IJsonData {

    List<Task> loadTasks() throws IOException;

    void saveTasks(List<Task> tasks) throws IOException;
}
