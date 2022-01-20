package main;

import command.HandlerCommand;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import settings.KeyboardSetting;

public class Bot extends TelegramLongPollingBot {
    final private String BOT_TOKEN = "5065117592:AAFYWpzTREUQ53ZdPEHo6pjhz92oRyawlNk";
    final private String BOT_NAME = "GreatQuoteBot";

    Storage storage;
    KeyboardSetting keySett;
    HandlerCommand handlerCommand;

    Bot()
    {
        super();

        storage = new Storage();
        keySett = new KeyboardSetting();
        handlerCommand = new HandlerCommand(storage, keySett);
    }

    public static void main(String[] args) {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                SendMessage outMessage = handlerCommand.getMessageUpd(update);
                execute(outMessage);
            }
            else if (update.hasCallbackQuery())
            {
                EditMessageText editMessageText = handlerCommand.getEditTextCQMessage(update);
                execute(editMessageText);
            }
        }
        catch (TelegramApiException ex)
        {
            ex.printStackTrace();
        }
    }
}
