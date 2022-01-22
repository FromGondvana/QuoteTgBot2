package main;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import java.util.ArrayList;
import java.util.List;

public class MessageToSendStorage {
    private List<SendMessage> sendMessageList;
    private List<EditMessageText> editMessageList;
    private List<DeleteMessage> delMessageList;

    public MessageToSendStorage() {
        sendMessageList = new ArrayList<>();;
        editMessageList = new ArrayList<>();
        delMessageList = new ArrayList<>();
    }

    public void add(SendMessage Message) {
        sendMessageList.add(Message);
    }

    public void add(EditMessageText Message) {
        editMessageList.add(Message);
    }

    public void add(DeleteMessage Message) {
        delMessageList.add(Message);
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


    public void clear()
    {
        sendMessageList.clear();
        editMessageList.clear();
        delMessageList.clear();
    }
}
