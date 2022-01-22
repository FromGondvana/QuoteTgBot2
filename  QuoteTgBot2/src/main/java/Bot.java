import handler.Handler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.stream.Stream;


public class Bot extends TelegramLongPollingBot {
    final private String BOT_TOKEN = "5065117592:AAFYWpzTREUQ53ZdPEHo6pjhz92oRyawlNk";
    final private String BOT_NAME = "GreatQuoteBot";

    Handler handlerCommand;

    Bot()
    {
        super();

        handlerCommand = new Handler();
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
        if (update.hasMessage() || update.hasCallbackQuery()) {
            handlerCommand.newUpdateMessage(update);

            executeSendMsgList();
        }
    }
    public void executeSendMsgList() {
        Stream.of(handlerCommand.getMsg().getSendMessageList(),
                handlerCommand.getMsg().getDelMessageList(),
                handlerCommand.getMsg().getEditMessageList())
                .forEach(list -> {
                    for(int i = 0; i < list.size(); i++)
                    {
                        try {
                            execute(list.get(i));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                });


        handlerCommand.getMsg().clear();
    }
}
