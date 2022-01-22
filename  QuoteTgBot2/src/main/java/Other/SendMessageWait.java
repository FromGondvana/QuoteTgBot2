package Other;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class SendMessageWait extends SendMessage {
    boolean isWait;


    public boolean isWait() {
        return isWait;
    }

    public void setWait(boolean wait) {
        isWait = wait;
    }
}
