package ru.spb.reshenie.monitoring.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.spb.reshenie.monitoring.config.BotConfig;
import ru.spb.reshenie.monitoring.model.bot.DeliveryStatus;

import java.util.List;

/**
 * Created by vladimirsabo on 13.08.2023
 */
@Service
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig config;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasChannelPost() && update.getChannelPost().hasText()) {
            String text = update.getChannelPost().getText();
            logger.info("command " + text + " from user @" + update.getChannelPost().getChat().getUserName());
            consumeNewMessage(update);
        }
    }

    public void updateCommands(List<BotCommand> listOfCommands) {
        try {
            execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), "ru"));
        } catch (TelegramApiException e) {
            logger.error(e.getMessage());
        }
    }

    private void consumeNewMessage(Update update) { //todo добавить логику обработки сообщений

    }

    public DeliveryStatus sendMessageToId(long chatId, String textToSend) {
        SendMessage outputMessage = new SendMessage();
        outputMessage.setChatId(chatId);
        outputMessage.setText(textToSend);
        try {
            execute(outputMessage);
            return DeliveryStatus.GOOD;
        } catch (TelegramApiException e) {
            logger.error(
                    String.format("problems with sending message to chatId %s, text = %s", chatId, textToSend) + e.getMessage());
            if (e.getMessage().contains("bot was blocked by the user")) {
                return DeliveryStatus.BLOCKED;
            } else {
                return DeliveryStatus.BAD;
            }
        }
    }
}
