import UI.Menu;
import service.FileManager;
import service.TaskManager;

public class TodoListApp {
    public static void main(String[] args) {
        String filePath = "/home/gabriel/IdeaProjects/list";
        FileManager fileManager = new FileManager(filePath);
        TaskManager taskManager = new TaskManager(fileManager);
        Menu menu = new Menu(taskManager);
        menu.start();
        }
}

