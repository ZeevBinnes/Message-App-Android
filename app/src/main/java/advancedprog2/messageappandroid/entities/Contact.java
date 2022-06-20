package advancedprog2.messageappandroid.entities;

//import androidx.room.Entity;
//import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

//@Entity
public class Contact {
//    @PrimaryKey(autoGenerate = false)
    private String id;
    private String name;
    private String server;
    private List<Message> messages;

    public Contact(String id, String name, String server) {
        this.id = id;
        this.name = name;
        this.server = server;
        this.messages = new ArrayList<>();
    }

    public Contact(String id, String name, String server, List<Message> messages) {
        this.id = id;
        this.name = name;
        this.server = server;
        this.messages = messages;
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
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
