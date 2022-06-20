package advancedprog2.messageappandroid.database_classes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import advancedprog2.messageappandroid.entities.Contact;

@Dao
public interface ContactDao {

    @Insert
    void inset(Contact contact);

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("SELECT * FROM Contact")
    LiveData<List<Contact>> getContacts();

    @Transaction
    @Query("SELECT * FROM Contact WHERE user_contact = :user_contact")
    LiveData<ContactWithMessages> getMessagesWithContact(String user_contact);
}
