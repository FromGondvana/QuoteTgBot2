package command;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class AdminCommand extends BasicCommand{
    String response = "???";

    public AdminCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        NonCommand.isARCommPass = true;
        sendMessage(absSender, chat.getId().toString(), response);
    }
}
