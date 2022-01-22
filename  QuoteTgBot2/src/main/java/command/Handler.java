package command;
import main.MessageToSendStorage;
import main.Storage;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import settings.KeyboardSetting;

public class Handler {
    private Logger logger = Logger.getLogger(Handler.class);
    private Storage storage;
    private KeyboardSetting keySett;
    private MessageToSendStorage messSendStore;
    private boolean isBuffStrOpen;

    final private String MAIN_ID = "846305186";
    public Handler()
    {
        storage = new Storage();
        keySett = new KeyboardSetting();
        messSendStore = new MessageToSendStorage();
        isBuffStrOpen = false;
    }

    public void addMessage(Update update)
    {
        String textMsg, chatId, response;
        int messId;

        if (update.hasCallbackQuery()) {
            textMsg = update.getCallbackQuery().getData();
            chatId = update.getCallbackQuery().getMessage().getChatId().toString();
            messId = update.getCallbackQuery().getMessage().getMessageId();
            response = parseMessage(textMsg, chatId, messId);

            startFormCallbackMessage(chatId, response, messId);
        }
        else if (update.hasMessage() && update.getMessage().hasText()) {

            textMsg = update.getMessage().getText();
            chatId = update.getMessage().getChatId().toString();
            messId = update.getMessage().getMessageId();
            response = parseMessage(textMsg, chatId, messId);

            startFormBaseMessage(chatId, response, messId);
        }
        else
            System.out.println("Error addMessageHandler");


    }

    private void startFormBaseMessage(String chatId, String textMsg, int messId)
    {
        messSendStore.add(getSendMessage(chatId, textMsg));
        messSendStore.add(getDelMessage(chatId, messId));
    }
    private void startFormCallbackMessage(String chatId, String textMsg, int messId)
    {
        messSendStore.add(getEditTextMessage(chatId, textMsg, messId));
    }
    private String parseMessage(String textMsg, String chatId, int messId)
    {
        logger.info("parseMessage ".concat(textMsg));
        String response;

        if (textMsg.equals("/start")) {
            if(storage.checkNewUser(chatId))
            {
                logger.info("new user ".concat(chatId));
                messSendStore.add(getDelMessage(chatId, messId - 1));
            }
            response = "Приветствую, бот знает много цитат. Жми \"Цитата\", чтобы получить случайную из них";
        }
        else if (textMsg.equals("/info"))
            response = "Created by Oleg Kosenko 2022";
        else if (textMsg.equals("/help"))
            response = "Hz";
        else if (textMsg.equals("/show"))
            response = storage.getRandQuote();
        else if (textMsg.equals("/appeal"))
            response = storage.getRandQuote();
        else if (textMsg.equals("/offer"))
            response = "To be future";
        else if (textMsg.equals("/menu")) {
            response = "Приступайте к работе с ботом";
        }
        else if (textMsg.equals("/admin")) {
            logger.info("/admin push ");
            if(MAIN_ID.equals(chatId)) {
                logger.info("/admin is true");
                response = "Я вас приветствую";
                keySett.setAdmKBMod(true);
            }
            else
                response = "Отказано";
        }
        else if(chatId.equals("846305186"))
        {
            response = parseAdmMessage(textMsg, chatId, messId);
        }
        else {
            response = "HZZZZZ";
        }

        return response;
    }
    private String parseAdmMessage(String textMsg, String chatId, int messId)
    {
        String response;

        if(messSendStore.isHaveMessWaitWithChatId(chatId))
        {
            response = "";
            if(textMsg.equals("/showindex"))
            {

            }
        }
        else if (textMsg.equals("/clean")) {
            cleanChat(chatId, messId);
            response = "Clean last 10 mess";
        }
        else if (textMsg.equals("/showindex10"))
            response = storage.get10RandQuote();
        else if (textMsg.equals("/showindex")) {
            response = "Send index";
        }
        else if (textMsg.equals("/delete")) {
            response = "Send index";
        }
        else if (textMsg.equals("/printstatus"))
            response = storage.getStatus();
        else if (textMsg.equals("/appeallist"))
            response = storage.getAppealStr();
        else if (textMsg.equals("/add"))///////////////////
            response = "Hz";
        else if (textMsg.equals("/offerlist"))
            response = storage.getOfferListStr();
        else if (textMsg.equals("/userslist"))
            response = storage.getUserIdListStr();
        else if (textMsg.equals("/offAdmMod")) {
            keySett.setAdmKBMod(false);
            response = "Admin mod is off";
        }
        else
            response = null;
        return response;
    }
    private SendMessage getSendMessage(String chatId, String textMsg)
    {
        SendMessage outMessage = new SendMessage();

        outMessage.setText(textMsg);
        outMessage.setChatId(chatId);
        outMessage.setReplyMarkup(keySett.getKeyMark(chatId));

        return outMessage;
    }
    private DeleteMessage getDelMessage(String chatId, int messId)
    {
        DeleteMessage outMessage = new DeleteMessage();

        outMessage.setMessageId(messId);
        outMessage.setChatId(chatId);

        return outMessage;
    }
    private EditMessageText getEditTextMessage(String chatId, String textMsg, int messId)
    {
        EditMessageText outMessage = new EditMessageText();

        outMessage.setMessageId(messId);
        outMessage.setChatId(chatId);
        outMessage.setText(textMsg);
        outMessage.setReplyMarkup(keySett.getKeyMark(chatId));

        return outMessage;
    }
    public MessageToSendStorage getMessSendStore()
    {
        return messSendStore;
    }

    private void cleanChat(String chatId, int messId)
    {
        for(int i = 0; i < 10; i++)
        messSendStore.add(getDelMessage(chatId, messId));
    }
}
