package data;

import Other.WaitingResponseType;

public class User {
    String id, buffLastMessageText;
    WaitingResponseType waitingResponseType;

    public User(String id) {
        this.id = id;
        this.buffLastMessageText = new String();
        this.waitingResponseType = WaitingResponseType.NON;
    }

    public boolean equalsId(String id)
    {
        if(this.id.equals(id))
            return true;
        else
            return false;
    }
    public boolean equalsType(WaitingResponseType type)
    {
        if(waitingResponseType == type)
            return true;
        else
            return false;
    }
    public boolean equalsBuffer(String text)
    {
        if(buffLastMessageText.equals(text))
            return true;
        else
            return false;
    }
}
