package advancedprog2.messageappandroid.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String username;
    private String password;
//    private String picture;
//    private List<Contact> contacts;

//    public User(String username, String password, String picture, List<Contact> contacts) {
//        this.username = username;
//        this.password = password;
//        this.picture = picture;
//        this.contacts = contacts;
//    }

    public User(@NonNull String username, String password, String picture) {
        this.username = username;
        this.password = password;
//        this.picture = picture;
//        this.contacts = new ArrayList<>();
    }

    public User(@NonNull String username, String password) {
        this.username = username;
        this.password = password;
//        this.picture = "";
//        this.contacts = new ArrayList<>();
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
