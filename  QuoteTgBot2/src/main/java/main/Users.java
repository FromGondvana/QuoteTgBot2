package main;

import Other.WaitingResponseType;

import java.util.ArrayList;
import java.util.List;

public class Users {
    private List<User> userList;


    public Users() {
        userList = new ArrayList<>();
    }

    public void add(User user)
    {
        if(!userList.contains(user))
            userList.add(user);
    }

    public WaitingResponseType getExpectedAction(String id)
    {
        if(!userList.contains(getIndexUser(id)))
            return userList.get(getIndexUser(id)).waitingResponseType;
        else
            return null;
    }
    public void setWRType(String id, WaitingResponseType type)
    {
        if(!userList.contains(getIndexUser(id)))
            userList.get(getIndexUser(id)).waitingResponseType = type;
    }
    public int getIndexUser(String id)
    {
        int index = -1;
        for(int i = 0; i < userList.size(); i++) {
            if (userList.get(i).equalsId(id)) {
                index = i;
                break;
            }
        }
        return index;
    }
    public String getBuffer(String id)
    {
        if(!userList.contains(getIndexUser(id)))
            return userList.get(getIndexUser(id)).buffLastMessageText;
        else
            return null;
    }
    public void setBuffer(String id, String text)
    {
        if(!userList.contains(getIndexUser(id)))
            userList.get(getIndexUser(id)).buffLastMessageText = text;
    }

}
