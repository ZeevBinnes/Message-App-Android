package advancedprog2.messageappandroid.database_classes;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import advancedprog2.messageappandroid.entities.Contact;
import advancedprog2.messageappandroid.entities.User;

public class UserWithContacts {
    @Embedded public User user;
    @Relation(
            parentColumn = "username",
            entityColumn = "user"
    )
    public List<Contact> contacts;
}
