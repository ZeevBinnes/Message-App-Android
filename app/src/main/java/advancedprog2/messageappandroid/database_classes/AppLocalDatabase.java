package advancedprog2.messageappandroid.database_classes;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import advancedprog2.messageappandroid.entities.User;
//import advancedprog2.messageappandroid.entities.Contact;
//import advancedprog2.messageappandroid.entities.Message;

//@Database(entities = {User.class, Contact.class, Message.class}, version = 1)
@Database(entities = {User.class}, version = 1)
public abstract class AppLocalDatabase extends RoomDatabase {

    private static AppLocalDatabase instance;

    public abstract UserDao userDao();
//    public abstract ContactDao contactDao();
//    public abstract MessageDao messageDao();

    public static synchronized AppLocalDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppLocalDatabase.class, "local_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
