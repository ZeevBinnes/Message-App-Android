package advancedprog2.messageappandroid.api;

public class ApiFormat {
    public String from;
    public String to;
    public String content;
    public String server;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }



    public ApiFormat(String from, String to, String content, String server) {
        this.from = from;
        this.to = to;
        this.content = content;
        this.server = server;
    }
}
