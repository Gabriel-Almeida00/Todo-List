package todo.list.dao;

import todo.list.model.Alarm;
import todo.list.model.Task;
import todo.list.model.enums.TaskStatus;
import todo.list.exception.FileException;
import todo.list.data.IJsonData;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TaskDao implements ITaskDao{
    private final IJsonData jsonData;

    public TaskDao(IJsonData jsonData) {
        this.jsonData = jsonData;
    }

    @Override
    public List<Task> listAllTasks() {
        try {
            return jsonData.loadTasks();
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo" + e);
        }
    }

    @Override
    public void addTask(Task task) {
        try {
            List<Task> tasks = jsonData.loadTasks();

            tasks.add(task);
            tasks.sort((t1, t2) -> Integer.compare(t2.getPriority(), t1.getPriority()));

            jsonData.saveTasks(tasks);
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo" + e);
        }
    }

    @Override
    public void setTaskDone(UUID idTask) {
        try {
            List<Task> tasks = jsonData.loadTasks();
            for (Task task : tasks) {
                if (task.getId().equals(idTask)) {
                    task.setStatus(TaskStatus.DONE);
                    jsonData.saveTasks(tasks);
                    return;
                }
            }
        } catch (IOException e) {
            throw new FileException("Erro ao ler ou salvar o arquivo: " + e);
        }
    }


    @Override
    public void updateTask(Task updatedTask) {
        try {
            List<Task> tasks = jsonData.loadTasks();
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                if (task.getId().equals(updatedTask.getId())) {
                    tasks.set(i, updatedTask);
                    jsonData.saveTasks(tasks);
                    return;
                }
            }
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo" + e);
        }
    }

    @Override
    public void deleteTask(UUID taskId) {
        try {
            List<Task> tasks = jsonData.loadTasks();
            tasks.removeIf(task -> task.getId().equals(taskId));
            jsonData.saveTasks(tasks);
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo" + e);
        }
    }

    @Override
    public List<Task> getTasksByCategory(String categoryName) {
        try {
            List<Task> tasks = jsonData.loadTasks();

            return tasks.stream()
                    .filter(task -> task.getCategory().getName().equalsIgnoreCase(categoryName))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo" + e);
        }
    }

    @Override
    public List<Task> getTasksByPriority(Integer priority) {
        try {
            List<Task> tasks = jsonData.loadTasks();

            return tasks.stream()
                    .filter(task -> task.getPriority().equals(priority))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo" + e);
        }
    }

    @Override
    public List<Task> getTasksByStatus(TaskStatus status) {
        try {
            List<Task> tasks = jsonData.loadTasks();

            return tasks.stream()
                    .filter(task -> task.getStatus().equals(status))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo" + e);
        }
    }

    @Override
    public int countCompletedTasks() {
        try {
            List<Task> tasks = jsonData.loadTasks();

            long count = tasks.stream()
                    .filter(task -> task.getStatus() == TaskStatus.DONE)
                    .count();

            return (int) count;
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo" + e);
        }
    }

    @Override
    public int countToDoTasks() {
        try {
            List<Task> tasks = jsonData.loadTasks();

            long count = tasks.stream()
                    .filter(task -> task.getStatus() == TaskStatus.TODO)
                    .count();

            return (int) count;
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo" + e);
        }
    }

    @Override
    public int countDoingTasks() {
        try {
            List<Task> tasks = jsonData.loadTasks();

            long count = tasks.stream()
                    .filter(task -> task.getStatus() == TaskStatus.DOING)
                    .count();

            return (int) count;
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo" + e);
        }
    }

    @Override
    public List<Task> filterTasksByDate(LocalDate date) {
        try {
            List<Task> tasks = jsonData.loadTasks();

            return tasks.stream()
                    .filter(task -> task.getDeadline().toLocalDate().isEqual(date))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo" + e);
        }
    }

    @Override
    public List<Task> getTasksWithAlarms() {
        try {
            List<Task> tasks = jsonData.loadTasks();

            return tasks.stream()
                    .filter(Task::hasAlarms)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo" + e);
        }
    }

    @Override
    public void desativarAlarme(UUID id){
        try{
            List<Task> tasks = jsonData.loadTasks();

            for (Task task : tasks) {
                List<Alarm> alarms = task.getAlarms();

                for (Alarm alarm : alarms) {
                    if (alarm.getId().equals(id)) {
                        alarm.desativarAlarme();
                        break;
                    }
                }
            }
            jsonData.saveTasks(tasks);

        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo" + e);
        }
    }
}
