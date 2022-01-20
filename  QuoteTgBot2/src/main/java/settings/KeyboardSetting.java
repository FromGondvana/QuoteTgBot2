package settings;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class KeyboardSetting {
    private InlineKeyboardMarkup mainKeyMark;
    private InlineKeyboardMarkup admKeyMark;

    public KeyboardSetting() {
        mainKeyMark = new InlineKeyboardMarkup();
        admKeyMark = new InlineKeyboardMarkup();
        initStartKeyList();
        initAdmKeyList();
    }

    public void initStartKeyList()
    {
        List<List<InlineKeyboardButton>>keyRows = new ArrayList<>();
        List<InlineKeyboardButton> firstKeyRow = new ArrayList<>();
        List<InlineKeyboardButton> secondKeyRow = new ArrayList<>();

        firstKeyRow.add(new InlineKeyboardButton("Show"));
        firstKeyRow.get(0).setCallbackData("/show");
        secondKeyRow.add(new InlineKeyboardButton("Info"));
        secondKeyRow.add(new InlineKeyboardButton("Help"));
        secondKeyRow.get(0).setCallbackData("/info");
        secondKeyRow.get(1).setCallbackData("/help");

        keyRows.add(firstKeyRow);
        keyRows.add(secondKeyRow);
        mainKeyMark.setKeyboard(keyRows);
    }
    public void initAdmKeyList()
    {
        List<List<InlineKeyboardButton>> keyRows = new ArrayList<>();
        List<InlineKeyboardButton> firstKeyRow = new ArrayList<>();
        List<InlineKeyboardButton> secondKeyRow = new ArrayList<>();

        firstKeyRow.add(new InlineKeyboardButton("Show "));
        firstKeyRow.get(0).setCallbackData("/show");
        firstKeyRow.add(new InlineKeyboardButton("Show 10"));
        firstKeyRow.get(1).setCallbackData("/show10");
        firstKeyRow.add(new InlineKeyboardButton("Show by index"));
        firstKeyRow.get(2).setCallbackData("/showindex");
        firstKeyRow.add(new InlineKeyboardButton("Delete"));
        firstKeyRow.get(3).setCallbackData("/delete");
        secondKeyRow.add(new InlineKeyboardButton("Size"));
        secondKeyRow.add(new InlineKeyboardButton("Count chat"));
        secondKeyRow.get(0).setCallbackData("/size");
        secondKeyRow.get(1).setCallbackData("/userCount");
        secondKeyRow.add(new InlineKeyboardButton("Add"));
        secondKeyRow.add(new InlineKeyboardButton("Off adm mod"));
        secondKeyRow.get(2).setCallbackData("/add");
        secondKeyRow.get(3).setCallbackData("/offAdmMod");

        keyRows.add(firstKeyRow);
        keyRows.add(secondKeyRow);
        admKeyMark.setKeyboard(keyRows);
    }

    public InlineKeyboardMarkup getKeyMark(boolean isAdmModAct, String idFrom) {
        String id = "846305186";
        if(isAdmModAct && id.equals(idFrom))
            return admKeyMark;
        else
            return mainKeyMark;
    }
}
