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
        secondKeyRow.add(new InlineKeyboardButton("Пожаловаться"));
        secondKeyRow.get(0).setCallbackData("/appeal");
        secondKeyRow.add(new InlineKeyboardButton("Предложить"));
        secondKeyRow.get(1).setCallbackData("/offer");
        secondKeyRow.add(new InlineKeyboardButton("Инфо"));
        secondKeyRow.get(2).setCallbackData("/info");

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
        firstKeyRow.add(new InlineKeyboardButton("Show by index"));
        firstKeyRow.get(2).setCallbackData("/showindex");
        firstKeyRow.add(new InlineKeyboardButton("Delete"));
        firstKeyRow.get(3).setCallbackData("/delete");

        secondKeyRow.add(new InlineKeyboardButton("Print status"));
        secondKeyRow.get(0).setCallbackData("printstatus");
        secondKeyRow.add(new InlineKeyboardButton("Appeal list"));
        secondKeyRow.get(1).setCallbackData("/appeallist");
        secondKeyRow.add(new InlineKeyboardButton("Offer list"));
        secondKeyRow.get(2).setCallbackData("/offerlist");
        secondKeyRow.add(new InlineKeyboardButton("Users list"));
        secondKeyRow.get(3).setCallbackData("/userslist");

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
