package command;

import main.Storage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import settings.KeyboardSetting;

public class HandlerCommand {
    Storage storage;
    KeyboardSetting keySett;
    final private String MAIN_ID = "846305186";
    String bufferLastMess;
    boolean isNeedKeyboard, isAdminModActive;
    public HandlerCommand(Storage storage, KeyboardSetting keySett)
    {
        isAdminModActive = false;
        isNeedKeyboard = false;
        this.storage = storage;
        this.keySett = keySett;
    }

    public EditMessageText getEditTextCQMessage(Update update)
    {

        EditMessageText outMessage = new EditMessageText();
        String chatId, textMsg , response;
        int messId;
        Message inMessage = update.getCallbackQuery().getMessage();

        messId = inMessage.getMessageId();
        chatId = inMessage.getChatId().toString();
        textMsg = update.getCallbackQuery().getData();

        response = parseMessage(textMsg, chatId);

        outMessage.setMessageId(messId);
        outMessage.setChatId(chatId);
        outMessage.setText(response);
        outMessage.setReplyMarkup(keySett.getKeyMark(isAdminModActive, chatId));

        return outMessage;
    }
    public SendMessage getMessageUpd(Update update)
    {
        Message inMessage = update.getMessage();
        SendMessage outMessage = new SendMessage();
        String chatId, textMsg , response;

        chatId = inMessage.getChatId().toString();
        textMsg = inMessage.getText();
        response = parseMessage(textMsg, chatId);

        if(isAdminModActive)
        {
            bufferLastMess = textMsg;
        }
        if(isNeedKeyboard)
        {
            outMessage.setReplyMarkup(keySett.getKeyMark(isAdminModActive, chatId));
            isNeedKeyboard = false;
        }
        outMessage.setText(response);
        outMessage.setChatId(chatId);

        return outMessage;
    }

    public String parseMessage(String textMsg, String chatId)
    {
        System.out.println(isAdminModActive);
        String response;

        if (textMsg.equals("/start")) {
            storage.checkNewChatId(chatId);
            response = "Приветствую, бот знает много цитат. Жми \"show\", чтобы получить случайную из них";
        }
        else if (textMsg.equals("/info"))
            response = "Created by Oleg Kosenko 2022";
        else if (textMsg.equals("/help"))
            response = "comming soon";
        else if (textMsg.equals("/show"))
            response = storage.getRandQuote();
        else if (textMsg.equals("/menu")) {
            response = " Клава))";
            isNeedKeyboard = true;;
        }
        else if (textMsg.equals("/admin")) {
            if(MAIN_ID.equals(chatId)) {
                isAdminModActive = true;
                response = "Я вас приветствую";
            }
            else
                response = "Отказано";
        }
        else if(isAdminModActive)
        {
            response = parseAdmMessage(textMsg);
        }
        else
            response = "Не понял вас";

        return response;
    }
    public String parseAdmMessage(String textMsg)
    {
        //Integer index = Integer.parseInt(bufferLastMess);
        String response;
        if (textMsg.equals("/show"))
            response = storage.getRandQuote();
        else if (textMsg.equals("/show10"))
            response = storage.get10RandQuote();
        /*else if (textMsg.equals("/showindex") && index != null)
            response = storage.getQuote(index);
        else if (textMsg.equals("/delete") && index != null) {
            response = storage.getRandQuote().concat("\n\nУдалено");
            storage.delete(index);
        }*/
        else if (textMsg.equals("/size"))
            response = Integer.toString(storage.getSizeQuoteList());
        else if (textMsg.equals("/userCount"))
            response = Integer.toString(storage.getSizeUserIdList());
        else if (textMsg.equals("/add"))
            response = "Hz";
        else if (textMsg.equals("/offAdmMod")) {
            isAdminModActive = false;
            response = "Admin mod is off";
        }
        else
            response = "Hzz";
        return response;
    }
}
