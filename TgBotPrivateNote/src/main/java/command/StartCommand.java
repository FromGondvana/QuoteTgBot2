package command;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class StartCommand extends  BasicCommand{
    String responseText;

    public StartCommand(String identifier, String description)
    {
        super(identifier, description);
        responseText = "Welcome to PrivAssistandBot. This is testing version. If you need more information, press /help\n\n" +
                "Created by OlKosTech 2022";
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        sendMessage(absSender, chat.getId().toString(), responseText);
    }
}
