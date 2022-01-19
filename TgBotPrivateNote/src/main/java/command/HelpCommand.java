package command;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class HelpCommand extends  BasicCommand{
    String responseText;

    public HelpCommand(String identifier, String description)
    {
        super(identifier, description);
        responseText = "Full list of commands:\n" +
                "/start - use to get started\n" +
                "/help - use to get help on commands\n" +
                "/show - use to get note (First send command, then send key)\n" +
                "/add - use to add note (First send command, then send key and text. Use to '%' to mark the key. Example: \"MyKey%MyNote\")\n";
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        sendMessage(absSender, chat.getId().toString(), responseText);
    }
}
