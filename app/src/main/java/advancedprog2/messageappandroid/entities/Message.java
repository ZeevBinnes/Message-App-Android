package advancedprog2.messageappandroid.entities;

//import androidx.room.Entity;
//import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;

//@Entity
public class Message {

//    @PrimaryKey(autoGenerate = true)
    private int id;
    private String content;
    private Date created;
    private boolean sent;
    private String type = "text";

    public Message(String content, Date created, boolean sent) {
        this.content = content;
        this.created = created;
        this.sent = sent;
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public String getDateFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm dd/MM/yy");
        return sdf.format(created);
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
