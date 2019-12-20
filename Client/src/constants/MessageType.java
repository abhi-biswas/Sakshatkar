package constants;

public enum MessageType {
    TEXT("TXT"),VIDEO("VID"),IMAGE("IMG");
    private String prefix;
    MessageType(String prefix)
    {
        this.prefix = prefix;
    }
    @Override
    public String toString()
    {
        return prefix;
    }
}
