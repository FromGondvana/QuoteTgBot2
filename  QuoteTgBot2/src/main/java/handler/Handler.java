package handler;
import Other.WaitingResponseType;
import data.Storage;
import data.User;
import data.Users;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import settings.KeyboardSetting;

import java.util.Scanner;

public class Handler {
    private Logger logger = Logger.getLogger(Handler.class);
    private Storage storage;
    private KeyboardSetting keySett;
    private ReceiveSendMsgController msg;
    private Users users;
    boolean adm;

    final private String MAIN_ID = "846305186";
    public Handler()
    {
        storage = new Storage();
        keySett = new KeyboardSetting();
        users = new Users();
        msg = new ReceiveSendMsgController();
        adm = false;
    }

    public void newUpdateMessage(Update update)
    {
        String textMsg, chatId;
        int messId;

        if (update.hasCallbackQuery()) {
            textMsg = update.getCallbackQuery().getData();
            chatId = update.getCallbackQuery().getMessage().getChatId().toString();
            messId = update.getCallbackQuery().getMessage().getMessageId();

            sendMessageFromCBQuere(chatId, textMsg, messId);
        }
        else if (update.hasMessage() && update.getMessage().hasText()) {

            textMsg = update.getMessage().getText();
            chatId = update.getMessage().getChatId().toString();
            messId = update.getMessage().getMessageId();

            sendMessageFromBase(chatId, textMsg, messId);
        }
        else
            System.out.println("Error addMessageHandler");


    }

    private void sendMessageFromBase(String chatId, String textMsg, int messId)
    {
        msg.sendMessage(chatId, parseQuereText(textMsg, chatId, messId), keySett.getKeyMark(chatId));
        msg.deleteMessage(chatId, messId);
        msg.deleteMessage(chatId, messId - 1);
    }
    private void sendMessageFromCBQuere(String chatId, String textMsg, int messId)
    {
        msg.editMessageText(chatId, parseQuereText(textMsg, chatId, messId), messId, keySett.getKeyMark(chatId));
    }
    private String parseQuereText(String textMsg, String chatId, int messId)
    {
        String response;
        if(chatId.equals(MAIN_ID) && adm)
        {
            response = responseToAdmRequest(textMsg, chatId, messId);
        }

        else if(textMsg.startsWith("/"))
        {
            response = responseToUserRequest(textMsg, chatId);
        }
        else {
            response = "Не понимаю";
        }

        if(response.equals(users.getBuffer(chatId))) {
            response = "Great Quote bot 2022. Created by OlegKo";
            users.setBuffer(chatId, response);
        }

        users.setBuffer(chatId, response);
        return response;
    }
    private String responseToUserRequest(String textMsg, String chatId)
    {
        users.add(chatId);

        logger.info("parseMessage ".concat(textMsg));
        String response;
        if (!users.getExpectedAction(chatId).equals(WaitingResponseType.NON))
        {
            if(users.getExpectedAction(chatId).equals(WaitingResponseType.FEEDBACK))
            {
                storage.addFeedback(textMsg);
                logger.warn(chatId.concat(" Feedback").concat(textMsg));
                response = "Запрс принят";
            }
            else if(users.getExpectedAction(chatId).equals(WaitingResponseType.SHOW_INDEX))
            {
                if(new Scanner(textMsg).hasNextInt())
                {
                    response = storage.getQuote(Integer.parseInt(textMsg));
                }
                else
                    response = "Не числовое значение";

            }
            else if(users.getExpectedAction(chatId).equals(WaitingResponseType.ADD))
            {
               storage.addQuote(textMsg);
               response = "OK";
            }
            else if(users.getExpectedAction(chatId).equals(WaitingResponseType.DELETE))
            {
                System.out.println("Delete");
                if(new Scanner(textMsg).hasNextInt())
                {
                    response = String.valueOf(storage.delete(Integer.parseInt(textMsg)));
                }
                else
                    response = "Не числовое значение";
            }
            else
                response = "Hz ExAct";

            users.setWRType(chatId, WaitingResponseType.NON);
        }
        else if (textMsg.equals("/start")) {
            response = "Приветствую, бот знает много цитат. Жми \"Цитата\", чтобы получить случайную из них";
        }
        else if (textMsg.equals("/info"))
            response = "Great Quote bot 2022. Created by OlegKo";
        else if (textMsg.equals("/help"))
            response = "Кнопка \"Цитата\" - присылает рандомную цитату" +
                    "\n\"Обратная связь\" - отправляйте жалобы и предложения";
        else if (textMsg.equals("/show")) {
            response = storage.getRandQuote();
        }
        else if (textMsg.equals("/feedback")) {
            users.setWRType(chatId, WaitingResponseType.FEEDBACK);
            response = "Отправьте ваше обращение в следующем сообщении ";
        }
        else if (textMsg.equals("/admin")) {
            logger.info("/admin push ");
            if(MAIN_ID.equals(chatId)) {
                logger.info("/admin is true");
                response = "Я вас приветствую";
                adm = true;
                keySett.setAdmKBMod(true);
            }
            else
                response = "Отказано";
        }
        else
            response = "Повторите запрос";
        return response;
    }
    private String responseToAdmRequest(String textMsg, String chatId, int messId)
    {
        String response;
        if (textMsg.equals("/clean")) {
            cleanChat(chatId, messId);
            response = "Clean last 10 mess";
        }
        else if (textMsg.equals("/show10"))
            response = storage.get10RandQuote();
        else if (textMsg.equals("/showindex")) {
            users.setWRType(chatId, WaitingResponseType.SHOW_INDEX);
            response = "Send index";
        }
        else if (textMsg.equals("/delete")) {
            users.setWRType(chatId, WaitingResponseType.DELETE);
            response = "Send index";
        }
        else if (textMsg.equals("/printstatus"))
            response = printStatus();
        else if (textMsg.equals("/feedbacklist"))
            response = storage.getFeedbackListStr();
        else if (textMsg.equals("/size"))
            response = String.valueOf(storage.getSizeQL());
        else if (textMsg.equals("/add")) {
            users.setWRType(chatId, WaitingResponseType.ADD);
            response = "Send text";
        }
        else if (textMsg.equals("/userslist"))
            response = users.getUsersIdStr();
        else if (textMsg.equals("/offAdmMod")) {
            keySett.setAdmKBMod(false);
            adm = false;
            response = "Admin mod is off";
        }
        else
            response = null;
        return response;
    }

    public ReceiveSendMsgController getMsg()
    {
        return msg;
    }

    private void cleanChat(String chatId, int messId)
    {
        for(int i = 2; i < 12; i++) {
            msg.deleteMessage(chatId, messId - i);
        }
    }
    private String printStatus()
    {
        String response = "Status variable:\n" +
                "Size quote list" + storage.getSizeQL() +
                "\nSize uset list" + users.getSize() +
                "\nSize feedback list" + storage.getSizeFBL();
        return response;
    }


}
