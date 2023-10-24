package todo.list.observers.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import todo.list.model.Alarm;
import todo.list.model.Task;
import todo.list.model.enums.TaskStatus;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class BotService extends TelegramLongPollingBot {

    private ConfigTelegram configTelegram;
    private  Long userId;

    public BotService(ConfigTelegram configTelegram){
        this.configTelegram = configTelegram;
    }


    @Override
    public void onUpdateReceived(Update update) {
        Message msg = update.getMessage();
        User user = msg.getFrom();
        userId = user.getId();
    }

    @Override
    public String getBotToken() {
        return configTelegram.getToken();
    }

    @Override
    public String getBotUsername() {
        return configTelegram.getBotUsername();
    }

    public void sendMessageForDeadLine(Task task) {
        String nameTask = task.getName();
        LocalDateTime taskDeadline = task.getDeadline();

        int diasDeAntecedencia = 1;
        LocalDateTime dataAtual = LocalDateTime.now();
        LocalDateTime dataLimiteAntecipada = taskDeadline.minusDays(diasDeAntecedencia);

        if (dataAtual.isAfter(dataLimiteAntecipada) || dataAtual.isEqual(taskDeadline)) {
            SendMessage sm = SendMessage.builder()
                    .chatId(userId.toString())
                    .text("Atenção: A tarefa " + nameTask + "está se aproximando. A data de término é " + taskDeadline )
                    .build();
            try {
                execute(sm);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendMessageForTaskCompletion(Task task) {
        String nameTask = task.getName();
        TaskStatus status = task.getStatus();


        if (status == TaskStatus.DONE) {
            SendMessage sm = SendMessage.builder()
                    .chatId(userId.toString())
                    .text("Parabéns! A tarefa " + nameTask + " foi concluída e agora está no status: " + status)
                    .build();
            try {
                execute(sm);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendMessageForTaskDelay(Task task) {
        String nameTask = task.getName();
        LocalDateTime taskDeadline = task.getDeadline();
        LocalDateTime currentDateTime = LocalDateTime.now();

        if (task.getStatus() == TaskStatus.TODO && currentDateTime.isAfter(taskDeadline)) {
            long daysDelay = ChronoUnit.DAYS.between(taskDeadline, currentDateTime);
            SendMessage sm = SendMessage.builder()
                    .chatId(userId.toString())
                    .text("Aviso: A tarefa " + nameTask + " está pendente e atrasada por " + daysDelay + " dia(s).")
                    .build();
            try {
                execute(sm);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void sendMessagens(Task task){
        this.sendMessageForDeadLine(task);
        this.sendMessageForTaskCompletion(task);
        this.sendMessageForTaskDelay(task);
    }
}
