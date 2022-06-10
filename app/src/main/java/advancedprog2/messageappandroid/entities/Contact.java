package advancedprog2.messageappandroid.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact {
    @PrimaryKey
    private String id;
    private String name;
    private String Server;
    private String Last;
    private String Lastdate;

    public Contact(String id, String name, String server, String last, String time) {
        this.id = id;
        this.name = name;
        Server = server;
        Last = last;
        Lastdate = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServer() {
        return Server;
    }

    public void setServer(String server) {
        Server = server;
    }

    public String getLast() {
        return Last;
    }

    public void setLast(String last) {
        Last = last;
    }

    public String getLastdate() {
        return Lastdate;
    }

    public void setLastdate(String lastdate) {
        Lastdate = lastdate;
    }
}
