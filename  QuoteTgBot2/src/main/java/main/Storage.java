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

    public Storage() {
        quoteList = new ArrayList<>();
        userIdList = new ArrayList<>();

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
        int randVar = (int) (Math.random() * quoteList.size());
        return quoteList.get(randVar);
    }
    public void checkNewChatId(String chatId)
    {
        if(!userIdList.contains(chatId))
            userIdList.add(chatId);

        System.out.println(chatId);
    }
    static String listToStr(ArrayList<String> list)
    {
        String response = "";
        for(String str : list)
            response = response.concat("\n").concat(str);

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
            response = response.concat("\n\n").concat(getRandQuote());
        }

        return response;
    }
    public void delete(int index)
    {
        quoteList.remove(index);
    }

    public int getSizeQuoteList()
    {
        return quoteList.size();
    }
    public int getSizeUserIdList()
    {
        return userIdList.size();
    }
    public void addQuote(String text)
    {
        quoteList.add(text);
    }
}