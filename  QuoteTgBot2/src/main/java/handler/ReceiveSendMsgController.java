package handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

public class ReceiveSendMsgController {
    private List<SendMessage> sendMessageList;
    private List<EditMessageText> editMessageList;
    private List<DeleteMessage> delMessageList;

    public ReceiveSendMsgController() {
        sendMessageList = new ArrayList<>();;
        editMessageList = new ArrayList<>();
        delMessageList = new ArrayList<>();
    }
    public List<SendMessage> getSendMessageList() {
        return sendMessageList;
    }

    public List<EditMessageText> getEditMessageList() {
        return editMessageList;
    }

    public List<DeleteMessage> getDelMessageList() {
        return delMessageList;
    }

    public void sendMessage(String chatId, String textMsg,InlineKeyboardMarkup keyboardMarkup)
    {
        sendMessageList.add(getSendForm(chatId, textMsg, keyboardMarkup));
    }
    public void deleteMessage(String chatId, int messId)
    {
        delMessageList.add(getDelForm(chatId, messId));
    }
    public void editMessageText(String chatId, String textMsg, int messId, InlineKeyboardMarkup keyboardMarkup)
    {
        editMessageList.add(getEditForm(chatId, textMsg, messId, keyboardMarkup));
    }

    private SendMessage getSendForm(String chatId, String textMsg, InlineKeyboardMarkup keyboardMarkup)
    {
        SendMessage outMessage = new SendMessage();

        outMessage.setText(textMsg);
        outMessage.setChatId(chatId);
        outMessage.setReplyMarkup(keyboardMarkup);

        return outMessage;
    }
    private DeleteMessage getDelForm(String chatId, int messId)
    {
        DeleteMessage outMessage = new DeleteMessage();

        outMessage.setMessageId(messId);
        outMessage.setChatId(chatId);

        return outMessage;
    }
    private EditMessageText getEditForm(String chatId, String textMsg, int messId, InlineKeyboardMarkup keyboardMarkup)
    {
        EditMessageText outMessage = new EditMessageText();

        outMessage.setMessageId(messId);
        outMessage.setChatId(chatId);
        outMessage.setText(textMsg);
        outMessage.setReplyMarkup(keyboardMarkup);

        return outMessage;
    }


    public void clear()
    {
        sendMessageList.clear();
        editMessageList.clear();
        delMessageList.clear();
    }
}
