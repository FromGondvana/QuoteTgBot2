package command;

import botMainPackage.Storage;

public class NonCommand {
    private final String AR_PASS = "test";
    static boolean isShowCommAct, isAddCommAct, isARCommPass, isARCommAct;

    public NonCommand()
    {
        isShowCommAct = false;
        isAddCommAct = false;
        isARCommPass = false;
        isARCommAct = false;
    }
    public String nonCommandExecute(String messText, Storage storage)
    {
        String response = null;
        if(isShowCommAct)
        {
            response = storage.getTextFromNote(messText);
            isShowCommAct = false;
        }
        else if(isAddCommAct)
        {
            String key, textNote;
            int indexMark = messText.indexOf('%');

            isAddCommAct = false;

            if(indexMark == -1)
                response = "Non valid message";
            else
            {
                key = messText.substring(0, indexMark);
                textNote = messText.substring(indexMark + 1);
                if(storage.isKeyExist(key))
                    response = "Key is exist";
                else
                {
                    storage.addNote(key, textNote);

                    response = "Note added";
                }
            }
        }
        else if(isARCommPass)
        {
            if(AR_PASS.equals(messText)){
                response = "Send modificator";
                isARCommAct = true;
            }
            else
            {
                response = "Try again, pass is not valid";
            }
        }
        else if(isARCommAct && !isARCommPass)
        {
            isARCommAct = false;
            if(messText.equals("-1"))
            {
                response = "Root is off";
                isARCommAct = false;
            }
            else if(messText.equals("1"))
            {
                response = "Size of storage = ".concat(Integer.toString(storage.getSize()));
            }
            else if (messText.equals("2"))
            {
                response = storage.getAllKeysStr();
            }
            else if (messText.equals("3"))
            {
                response = storage.getAllNotesStr();
            }
            else if (messText.equals("4"))
            {
                storage.backUpDB();
                response = "backup is finally";
            }
            else if (messText.equals("5"))
            {
                response = storage.monitorCondition();
            }
            else
            {
                response = "Mod is not found, send -1 to return";
            }
        }
        else
            response =  "Is not valid message";

        if(isARCommAct && isARCommPass)
            isARCommPass = false;
        return response;
    }
}
