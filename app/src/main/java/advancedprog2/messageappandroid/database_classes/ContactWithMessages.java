package advancedprog2.messageappandroid.database_classes;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import advancedprog2.messageappandroid.entities.Contact;
import advancedprog2.messageappandroid.entities.Message;

public class ContactWithMessages {
    @Embedded public Contact contact;
    @Relation(
            parentColumn = "user_contact",
            entityColumn = "user_contact"
    )
    public List<Message> messages;
}
