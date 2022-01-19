package botMainPackage;

public class PrivateNote {
    private String key, text;

    public PrivateNote(String password, String text) {
        this.key = password;
        this.text = text;
    }

    public String getKey() {
        return key;
    }

    public String getText() {
        return text;
    }
}
