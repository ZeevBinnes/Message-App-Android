package advancedprog2.messageappandroid.api;

public class ApiContact {
    public String Id;
    public String Name;
    public String Server;
    public String Last;
    public String Lastdate;

    public ApiContact(String id, String name, String server, String last, String lastdate) {
        Id = id;
        Name = name;
        Server = server;
        Last = last;
        Lastdate = lastdate;
    }
}
