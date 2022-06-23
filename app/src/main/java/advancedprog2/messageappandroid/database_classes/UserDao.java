package advancedprog2.messageappandroid.database_classes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;
import java.util.concurrent.Future;

import advancedprog2.messageappandroid.entities.Contact;
import advancedprog2.messageappandroid.entities.User;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addContact(Contact contact);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM User WHERE username = :id")
    User getUserById(String id);

    @Query("SELECT * FROM User")
    LiveData<List<User>> getAllUsers();

    @Transaction
    @Query("SELECT * FROM User WHERE username = :username")
    LiveData<UserWithContacts> getContactsOfUser(String username);
}
