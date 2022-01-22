package data;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    Path bufferedFilePath1 = Paths.get("src/main/resources/text_files/quote_file_greatPeople.txt");
    Path bufferedFilePath2 = Paths.get("src/main/resources/text_files/quotesFile.txt");
    private static ArrayList<String> quoteList;
    private ArrayList<String> feedbackList;

    public Storage() {
        quoteList = new ArrayList<>();
        feedbackList = new ArrayList<>();

        startFillingStrorage(bufferedFilePath1);
        startFillingStrorage(bufferedFilePath2);

        System.out.println(quoteList.size());
    }

    void startFillingStrorage(Path path) {
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            lines.stream().forEach(l -> quoteList.add(l));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRandQuote() {

        int index = (int) (Math.random() * quoteList.size());

        return "Цитата #".concat(String.valueOf(index + 1)).concat("\n").concat(quoteList.get(index));
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
        String response = "10 rand quote\n";

        for(int i = 0; i < 10; i++)
        {
            response = response.concat(Integer.toString(i + 1)).concat(") ").concat(getRandQuote()).concat("\n---------\n");
        }

        return response;
    }
    public boolean delete(int index)
    {
        if(quoteList.size() > index && quoteList.size() >= 0) {
            quoteList.remove(index);
            return true;
        }
        else
            return false;
    }

    public void addQuote(String text)
    {
        quoteList.add(text);
    }
    public void addFeedback(String text)
    {
        feedbackList.add(text);
    }
    public String getFeedbackListStr()
    {
        return listToStr(feedbackList);
    }
    public int getSizeQL()
    {
        return quoteList.size();
    }
    public int getSizeFBL()
    {
        return feedbackList.size();
    }
}