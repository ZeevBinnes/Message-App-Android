package advancedprog2.messageappandroid.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Message {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String content;
    private String created;
    private boolean sent;
    private String type = "text";
    private String user_contact;

    @Ignore
    public Message(String content, Date created, boolean sent, String user_contact) {
        this.content = content;
        this.created = getDateFormat(created);
        this.sent = sent;
        this.user_contact = user_contact;
    }

    public Message(String content, String created, boolean sent, String user_contact) {
        this.content = content;
        this.created = created;
        this.sent = sent;
        this.user_contact = user_contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = getDateFormat(created);
    }

    public void setCreated(String created) {this.created = created;}

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static String getDateFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm dd/MM/yy");
        return sdf.format(date);
    }

    public String getUser_contact() {
        return user_contact;
    }

    public void setUser_contact(String user_contact) {
        this.user_contact = user_contact;
    }

    //    public String getDateFormat() {
//        int hour = created.getHours();
//        int minute = created.getMinutes();
//        int day = created.getDate();
//        int month = created.getMonth()+1;
//        int year = created.getYear()+1900;
//        return hour + ":" + minute + " " + day + "." + month + "." + year;
//    }
}
