package main;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    Path bufferedFilePath = Paths.get("src/main/resources/text_files/quotesFile.txt");
    private static ArrayList<String> quoteList;
    private ArrayList<String> userIdList;
    private ArrayList<Integer> appealIndexList;
    private ArrayList<String> offerList;

    int lastQuoteIndex;

    public Storage() {
        quoteList = new ArrayList<>();
        userIdList = new ArrayList<>();
        appealIndexList = new ArrayList<>();
        offerList = new ArrayList<>();

        startFillingStrorage();
    }

    void startFillingStrorage() {
        try {
            List<String> lines = Files.readAllLines(bufferedFilePath, StandardCharsets.UTF_8);
            lines.stream().forEach(l -> quoteList.add(l));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRandQuote() {
        lastQuoteIndex = (int) (Math.random() * quoteList.size());

        return quoteList.get(lastQuoteIndex);
    }
    public boolean checkNewUser(String chatId)
    {
        if(!userIdList.contains(chatId)) {
            userIdList.add(chatId);
            return true;
        }
        else
            return false;
    }
    static String listToStr(ArrayList<String> list)
    {
        String response = "";
        for(int i = 0; i < list.size(); i++)
            response = response.concat("\n").concat(String.valueOf(i + 1)).concat(")").concat(list.get(i));

        return response;
    }
    public static String getQuote(int index)
    {
        return quoteList.get(index);
    }
    public String get10RandQuote()
    {
        String response = "";

        for(int i = 0; i < 10; i++)
        {
            response = Integer.toString(i).concat(") ").concat(response).concat("\n---------\n").concat(getRandQuote());
        }

        return response;
    }
    public void delete(int index)
    {
        quoteList.remove(index);
    }

    public String getStatus()
    {
        String response = "Status message \n" +
                "Size storage: \n".concat(String.valueOf(quoteList.size())) +
                "Users :\n".concat(String.valueOf(userIdList));
        return response;
    }
    public void addAppeal()
    {
        appealIndexList.add(lastQuoteIndex);
    }
    public void addQuote(String text)
    {
        quoteList.add(text);
    }
    public void addOfferQuote(String text)
    {
        offerList.add(text);
    }
    public String getUserIdListStr()
    {
        return listToStr(userIdList);
    }
    public String getOfferListStr()
    {
        return listToStr(offerList);
    }
    public String getAppealStr()
    {
        String response = "List index appeal\n";
        appealIndexList.stream().forEach(i -> response.concat(i.toString()).concat(" "));

        return response;
    }
}