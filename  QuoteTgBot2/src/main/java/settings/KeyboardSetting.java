package settings;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class KeyboardSetting {
    private InlineKeyboardMarkup mainKeyMark;
    private InlineKeyboardMarkup admKeyMark;
    private boolean admKBMod;

    public KeyboardSetting() {
        admKBMod = false;
        mainKeyMark = new InlineKeyboardMarkup();
        initStartKeyList();
        admKeyMark = new InlineKeyboardMarkup();
        initAdmKeyList();
    }

    public void initStartKeyList()
    {
        List<List<InlineKeyboardButton>>keyRows = new ArrayList<>();
        List<InlineKeyboardButton> firstKeyRow = new ArrayList<>();
        List<InlineKeyboardButton> secondKeyRow = new ArrayList<>();

        firstKeyRow.add(new InlineKeyboardButton("Цитата"));
        firstKeyRow.get(0).setCallbackData("/show");
        secondKeyRow.add(new InlineKeyboardButton("Обратная связь"));
        secondKeyRow.get(0).setCallbackData("/feedback");
        secondKeyRow.add(new InlineKeyboardButton("Инфо"));
        secondKeyRow.get(1).setCallbackData("/info");

        keyRows.add(firstKeyRow);
        keyRows.add(secondKeyRow);
        mainKeyMark.setKeyboard(keyRows);
    }
    public void initAdmKeyList()
    {
        List<List<InlineKeyboardButton>> keyRows = new ArrayList<>();
        List<InlineKeyboardButton> firstKeyRow = new ArrayList<>();
        List<InlineKeyboardButton> secondKeyRow = new ArrayList<>();
        List<InlineKeyboardButton> thirdKeyRow = new ArrayList<>();

        firstKeyRow.add(new InlineKeyboardButton("Clean chat"));
        firstKeyRow.get(0).setCallbackData("/clean");
        firstKeyRow.add(new InlineKeyboardButton("Show 10"));
        firstKeyRow.get(1).setCallbackData("/show10");
        firstKeyRow.add(new InlineKeyboardButton("Show index"));
        firstKeyRow.get(2).setCallbackData("/showindex");
        firstKeyRow.add(new InlineKeyboardButton("Delete"));
        firstKeyRow.get(3).setCallbackData("/delete");

        secondKeyRow.add(new InlineKeyboardButton("Print status"));
        secondKeyRow.get(0).setCallbackData("/printstatus");
        secondKeyRow.add(new InlineKeyboardButton("Feedback list"));
        secondKeyRow.get(1).setCallbackData("/feedbacklist");
        secondKeyRow.add(new InlineKeyboardButton("Users list"));
        secondKeyRow.get(2).setCallbackData("/userslist");
        secondKeyRow.add(new InlineKeyboardButton("Add"));
        secondKeyRow.get(3).setCallbackData("/add");

        thirdKeyRow.add(new InlineKeyboardButton("Off adm mod"));
        thirdKeyRow.get(0).setCallbackData("/offAdmMod");

        keyRows.add(firstKeyRow);
        keyRows.add(secondKeyRow);
        keyRows.add(thirdKeyRow);
        admKeyMark.setKeyboard(keyRows);
    }

    public InlineKeyboardMarkup getKeyMark(String chatId) {
        String id = "846305186";
        if(admKBMod && chatId.equals(id))
            return admKeyMark;
        else
            return mainKeyMark;
    }

    public boolean isAdmKBMod() {
        return admKBMod;
    }

    public void setAdmKBMod(boolean admKBMod) {
        this.admKBMod = admKBMod;
    }
}
