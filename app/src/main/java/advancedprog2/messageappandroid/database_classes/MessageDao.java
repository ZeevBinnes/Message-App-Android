package advancedprog2.messageappandroid.database_classes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import advancedprog2.messageappandroid.entities.Message;

@Dao
public interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void inset(Message message);

    @Update
    void update(Message message);

    @Delete
    void delete(Message message);

    @Query("SELECT * FROM Message")
    LiveData<List<Message>> getAllMessages();

    @Query("DELETE FROM Message WHERE user_contact = :user_contact")
    void clearMessagesOfContact(String user_contact);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(List<Message> messages);

    @Query("SELECT * FROM Message WHERE user_contact = :user_contact")
    LiveData<List<Message>> getContactsMessages(String user_contact);

    @Query("SELECT * FROM Message WHERE user_contact = :user_contact")
    List<Message> getMessagesAsList(String user_contact);
}
