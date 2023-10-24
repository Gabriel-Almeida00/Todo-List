/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package todo.list;

import todo.list.observers.AlarmObserverRegistry;
import todo.list.view.TaskView;
import todo.list.dao.ITaskDao;
import todo.list.dao.TaskDao;
import todo.list.data.JsonData;
import todo.list.data.IJsonData;
import todo.list.services.TaskService;

public class App {
    public static void main(String[] args) {
        String filePath = "/home/gabriel/IdeaProjects/todo-list/list.json";

        IJsonData fileService = new JsonData(filePath);
        ITaskDao taskDao = new TaskDao(fileService);
        TaskService taskService = new TaskService(taskDao);

        AlarmObserverRegistry alarmObserverRegistry = new AlarmObserverRegistry();
        TaskView menu = new TaskView(taskService, alarmObserverRegistry);
        menu.start();
    }
}
