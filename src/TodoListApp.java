import UI.Menu;
import entity.Task;
import service.FileManager;
import service.FileUtil;
import service.TaskManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class TodoListApp {
    public static void main(String[] args) {
        String filePath = "/home/gabriel/IdeaProjects/list";
        FileUtil fileUtil = new FileManager(filePath);
        TaskManager taskManager = new TaskManager(fileUtil);
        Menu menu = new Menu(taskManager);
        menu.start();
        }
}

