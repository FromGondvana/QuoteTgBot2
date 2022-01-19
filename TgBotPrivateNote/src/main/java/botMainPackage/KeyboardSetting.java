package botMainPackage;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class KeyboardSetting {
    private ReplyKeyboardMarkup replyKeyboardMarkup;
    private List<KeyboardRow> keyboardRows;

    KeyboardSetting()
    {
        replyKeyboardMarkup = new ReplyKeyboardMarkup();
        keyboardRows = new ArrayList<>();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        initMainKeyboard();
    }

    void initMainKeyboard()
    {
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("/show");
        keyboardRow.add("/add");
        keyboardRow.add("/help");

        keyboardRows.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }

    public ReplyKeyboardMarkup getReplyKeyboardMarkup() {
        return replyKeyboardMarkup;
    }
}
