/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package todo.list;

import todo.list.UI.Menu;
import todo.list.dao.ITaskDao;
import todo.list.dao.TaskDao;
import todo.list.entity.Task;
import todo.list.services.file.FileService;
import todo.list.services.file.IFileService;
import todo.list.services.task.TaskService;
import todo.list.services.task.TaskParseService;

public class App {
    public static void main(String[] args) {
        String filePath = "/home/gabriel/IdeaProjects/todo-list/list";
        TaskParseService taskParse = new TaskParseService();
        IFileService fileService = new FileService(filePath, taskParse);
        ITaskDao taskDao = new TaskDao(fileService);
        TaskService taskService = new TaskService(taskDao);

        Menu menu = new Menu(taskService);
        menu.start();
    }
}
