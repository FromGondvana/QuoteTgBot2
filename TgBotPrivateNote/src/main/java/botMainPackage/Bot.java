package botMainPackage;

import command.*;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingCommandBot {
    final private String BOT_TOKEN = "5062034725:AAFThEABiwKp97N72-ybRKyw3UKk2GYnjaA";
    final private String BOT_NAME = "PrivAssistantBot";
    Storage storage;
    KeyboardSetting keyboardSetting;
    NonCommand nonCommand;

    public Bot()
    {
        super();

        storage = new Storage();
        keyboardSetting = new KeyboardSetting();
        nonCommand = new NonCommand();

        register(new StartCommand("start", "Start"));
        register(new AddCommand("add", "Add"));
        register(new ShowCommand("show", "Show"));
        register(new HelpCommand("help", "Help"));
        register(new AdminCommand("admin", "admin"));
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }
    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void processNonCommandUpdate(Update update) {

        Message inMessage = update.getMessage();
        SendMessage outMessage = new SendMessage();

        String chatId = inMessage.getChatId().toString();
        String text = inMessage.getText();
        String response = nonCommand.nonCommandExecute(text, storage);

        outMessage.setText(response);
        outMessage.setChatId(chatId);
        outMessage.setReplyMarkup(keyboardSetting.getReplyKeyboardMarkup());

        try {
            execute(outMessage);
        }
        catch (TelegramApiException ex){
            ex.printStackTrace();
        }
    }
}
