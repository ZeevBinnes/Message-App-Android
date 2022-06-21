package advancedprog2.messageappandroid.api;

public class ApiFormat {
    public String from;
    public String to;
    public String content;
    public String server;

    public ApiFormat(String from, String to, String content, String server) {
        this.from = from;
        this.to = to;
        this.content = content;
        this.server = server;
    }
}
