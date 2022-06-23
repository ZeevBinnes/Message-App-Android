package advancedprog2.messageappandroid.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Contact {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String user_contact;
    private String id;
    private String name;
    private String server;
    //    private List<Message> messages;
    private String last;
    private String lastdate;
    private String user;

    public Contact(@NonNull String id, String name, String server, String last, String lastdate, String user) {
        this.id = id;
        this.name = name;
        this.server = server;
        this.last = last;
        this.lastdate = lastdate;
        this.user = user;
        this.user_contact = this.user + "-" + this.id;
    }

//    public Contact(String id, String name, String server) {
//        this.id = id;
//        this.name = name;
//        this.server = server;
////        this.messages = new ArrayList<>();
//    }
//
//    public Contact(String id, String name, String server, List<Message> messages) {
//        this.id = id;
//        this.name = name;
//        this.server = server;
////        this.messages = messages;
//    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
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

//    public List<Message> getMessages() {
//        return messages;
//    }

//    public void setMessages(List<Message> messages) {
//        this.messages = messages;
//    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getLastdate() {
        return lastdate;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser_contact() {
        return user_contact;
    }

    public void setUser_contact(String user_contact) {
        this.user_contact = user_contact;
    }
}
