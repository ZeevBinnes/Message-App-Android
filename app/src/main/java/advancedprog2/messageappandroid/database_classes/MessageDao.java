package advancedprog2.messageappandroid.database_classes;

//import androidx.room.Dao;
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.Update;

import advancedprog2.messageappandroid.entities.Message;

//@Dao
public interface MessageDao {

//    @Insert
    void inset(Message message);

//    @Update
    void update(Message message);

//    @Delete
    void delete(Message message);
}
