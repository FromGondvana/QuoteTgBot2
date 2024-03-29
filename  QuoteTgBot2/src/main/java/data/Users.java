package data;

import Other.WaitingResponseType;

import java.util.ArrayList;
import java.util.List;

public class Users {
    private List<User> userList;


    public Users() {
        userList = new ArrayList<>();
    }

    public void add(String chatId)
    {
        List<String> idList = new ArrayList<>();
        userList.stream().forEach(user -> idList.add(user.id));


        if(!idList.contains(chatId))
            userList.add(new User(chatId));
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
    public String getUsersIdStr()
    {
        String response = "User id list:\n";

        for(int i = 0; i < userList.size(); i++)
        {
            response = response.concat("\n").concat(String.valueOf(i).concat(")")).concat(userList.get(i).id);
        }
        return response;
    }
    public int getSize()
    {
        return userList.size();
    }
}
