package command;

import botMainPackage.Bot;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class ShowCommand extends BasicCommand{

    String response1;
    String response2;

    public ShowCommand(String identifier, String description) {
        super(identifier, description);

        response1 = "Send key";
        response2 = "Note added";
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        NonCommand.isShowCommAct = true;
        sendMessage(absSender, chat.getId().toString(), response1);
    }
}
