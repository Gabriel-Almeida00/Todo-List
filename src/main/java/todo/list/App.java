/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package todo.list;

import todo.list.UI.Menu;
import todo.list.task.file.FileService;
import todo.list.task.task.TaskService;
import todo.list.task.task.TaskParseService;

public class App {
    public static void main(String[] args) {
        String filePath = "/home/gabriel/IdeaProjects/list";
        TaskParseService taskParse = new TaskParseService();
        FileService fileManager = new FileService(filePath, taskParse);
        TaskService taskManager = new TaskService(fileManager);
        Menu menu = new Menu(taskManager);
        menu.start();
    }
}
