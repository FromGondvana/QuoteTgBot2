package botMainPackage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Storage {
    Date lastBackUpDate;
    long millsBackUp;

    ArrayList<PrivateNote> noteList;
    Path bufferedFilePath = Paths.get("src/main/resources/data_files/buffered.txt");
    Path mainFilePath = Paths.get("src/main/resources/data_files/main.txt");

    Storage()
    {
        lastBackUpDate = null;
        millsBackUp = 0;
        noteList = new ArrayList<>();
        initStorage();
    }

    public boolean isDBFilesExist()
    {
        if(!Files.exists(mainFilePath))
            System.out.println("ERROR main path bot found");
        if(!Files.exists(bufferedFilePath))
            System.out.println("ERROR bufffered path bot found");
        if(Files.exists(mainFilePath) && Files.exists(bufferedFilePath))
            return true;
        else
            return false;
    }

    public void backUpDB()
    {
        List<String> lines = new ArrayList<>();
        noteList.stream().forEach(note -> lines.add(note.getKey().concat("%").concat(note.getText())));
        try {
            Files.write(mainFilePath, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        millsBackUp = System.currentTimeMillis();
        lastBackUpDate = new Date();
        //lastBackUpDate = LocalDate.now();
    }

    public void initStorage()
    {
        System.out.println(Files.exists(mainFilePath));

        try {
            Files.newBufferedWriter(bufferedFilePath, StandardOpenOption.TRUNCATE_EXISTING);
            List<String> lines = Files.readAllLines(mainFilePath, StandardCharsets.UTF_8);
            lines.stream().forEach(line ->
            {
                String key, textNote;
                int indexMark = line.indexOf('%');

                key = line.substring(0, indexMark);
                textNote = line.substring(indexMark + 1);

                noteList.add(new PrivateNote(key, textNote));
            });
            Files.write(bufferedFilePath, lines);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public boolean isKeyExist(String key)
    {
        AtomicBoolean cond = new AtomicBoolean(false);
        noteList.stream().forEach(note -> {
            boolean resp = note.getKey().equals(key) ? true : false;
            cond.set(resp);
        });
        return cond.get();
    }
    public void addNote(String key, String text)
    {
        List<String> lines = new ArrayList<>();
        String noteInDb = key.concat("%").concat(text);
        lines.add(noteInDb);
        noteList.add(new PrivateNote(key, text));

        try {
            Files.write(bufferedFilePath, lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getTextFromNote(String key)
    {
        boolean isKeyExist = false;
        String text = null;
        for(PrivateNote note : noteList)
        {
            if(note.getKey().equals(key))
            {
                text = note.getText();
                isKeyExist = true;
            }
        }
        if(isKeyExist)
            return text;
        else
            return "Not found";
    }
    public int getSize()
    {
        return noteList.size();
    }
    public String getAllKeysStr()
    {
        AtomicReference<String> allKeys = new AtomicReference<>("All keys list:\n");
        noteList.stream().forEach(n -> allKeys.set(allKeys.get().concat(n.getKey()).concat("\n")));
        return allKeys.get();
    }
    public String getAllNotesStr()
    {
        AtomicReference<String> allNotes = new AtomicReference<>("All notes list:\n");
        noteList.stream().forEach(n -> allNotes.set(allNotes.get().concat("Key:").concat(n.getKey()).concat("\n").concat(n.getText()).concat("\n\n")));
        return allNotes.get();
    }
    public String monitorCondition()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy ' ' hh:mm:ss a zzz");
        String response;
        String date;
        String millsAgoResponse;
        if(lastBackUpDate == null)
            date = "In real session backup is not making";
        else
            date = simpleDateFormat.format(lastBackUpDate);

        if(millsBackUp == 0)
            millsAgoResponse = "In real session backup is not making";
        else {
            long mills = System.currentTimeMillis() - millsBackUp;
            millsAgoResponse = "\nBack up mills ago: ".concat(String.valueOf(mills))
                    .concat(" or minuts ago: ").concat(String.valueOf(mills / 60000))
                    .concat(" or hours ago: ").concat(String.valueOf(mills / 60000 / 60));
        }

        response = "BOT CONDITION\nIs databases files exist - ".concat(Boolean.toString(isDBFilesExist()))
                .concat("\nSize storage in memory: ").concat(String.valueOf(noteList.size()))
                .concat("\nLast backup date: ").concat(date)
                .concat(millsAgoResponse);

        System.out.println(response);
        return response;
    }
}
