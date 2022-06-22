package advancedprog2.messageappandroid.database_classes;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Date;

import advancedprog2.messageappandroid.entities.User;
import advancedprog2.messageappandroid.entities.Contact;
import advancedprog2.messageappandroid.entities.Message;

@Database(entities = {User.class, Contact.class, Message.class}, version = 1)
public abstract class AppLocalDatabase extends RoomDatabase {

    private static AppLocalDatabase instance;

    public abstract UserDao userDao();
    public abstract ContactDao contactDao();
    public abstract MessageDao messageDao();

    public static synchronized AppLocalDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppLocalDatabase.class, "local_database")
                    .fallbackToDestructiveMigration()
//                    .addCallback(roomCallback) //only for debugging
                    .build();
        }
        return instance;
    }

    // only for debugging, delete afterwards!!!
//    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//            new PopulateDbAsyncTask(instance).execute();
//        }
//    };
//
//    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
//        private UserDao userDao;
//        private ContactDao contactDao;
//        private  MessageDao messageDao;
//
//        private PopulateDbAsyncTask(AppLocalDatabase db) {
//            userDao = db.userDao();
//            contactDao = db.contactDao();
//            messageDao = db.messageDao();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            userDao.insert(new User("1", "1"));
//            userDao.insert(new User("2", "2"));
//
//            contactDao.inset(new Contact("a", "aa", "", "hi there", "12:34 5.6.22", "1"));
//            contactDao.inset(new Contact("b", "bb", "", "hello", "12:34 5.6.22", "1"));
//            contactDao.inset(new Contact("c", "cc", "", "hi there", "12:34 5.6.22", "1"));
//            contactDao.inset(new Contact("d", "dd", "", "12345", "12:34 5.6.22", "1"));
//            contactDao.inset(new Contact("e", "ee", "", "hi there", "12:34 5.6.22", "1"));
//
//            messageDao.inset(new Message("aaaa", new Date(), true, "1-a"));
//            messageDao.inset(new Message("bbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbb bbbbbbbbbbbbbbb", new Date(), false, "1-a"));
//            messageDao.inset(new Message("cccc", new Date(), true, "1-a"));
//            messageDao.inset(new Message("dddd", new Date(), false, "1-a"));
//            messageDao.inset(new Message("aaaa", new Date(), true, "1-a"));
//            messageDao.inset(new Message("bbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbb bbbbbbbbbbbbbbb", new Date(), false, "1-a"));
//            messageDao.inset(new Message("cccc", new Date(), true, "1-a"));
//            messageDao.inset(new Message("dddd", new Date(), false, "1-a"));
//            messageDao.inset(new Message("aaaa", new Date(), true, "1-a"));
//            messageDao.inset(new Message("bbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbb bbbbbbbbbbbbbbb", new Date(), true, "1-a"));
//            messageDao.inset(new Message("cccc", new Date(), true, "1-a"));
//            messageDao.inset(new Message("dddd", new Date(), false, "1-a"));
//            messageDao.inset(new Message("aaaa", new Date(), true, "1-a"));
//            messageDao.inset(new Message("bbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbb bbbbbbbbbbbbbbb", new Date(), false, "1-a"));
//            messageDao.inset(new Message("cccc", new Date(), true, "1-a"));
//            messageDao.inset(new Message("dddd", new Date(), false, "1-a"));
//            messageDao.inset(new Message("aaaa", new Date(), true, "1-a"));
//            messageDao.inset(new Message("bbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbb bbbbbbbbbbbbbbb", new Date(), false, "1-a"));
//            messageDao.inset(new Message("cccc", new Date(), false, "1-a"));
//            messageDao.inset(new Message("dddd", new Date(), false, "1-a"));
//            messageDao.inset(new Message("aaaa", new Date(), true, "1-a"));
//            messageDao.inset(new Message("bbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbb bbbbbbbbbbbbbbb", new Date(), true, "1-a"));
//            messageDao.inset(new Message("cccc", new Date(), true, "1-a"));
//            messageDao.inset(new Message("dddd", new Date(), false, "1-a"));
//            messageDao.inset(new Message("aaaa", new Date(), true, "1-a"));
//            messageDao.inset(new Message("bbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbb bbbbbbbbbbbbbbb", new Date(), false, "1-a"));
//            messageDao.inset(new Message("cccc", new Date(), true, "1-a"));
//            messageDao.inset(new Message("dddd", new Date(), false, "1-a"));
//
//            return null;
//        }
//    }

}
