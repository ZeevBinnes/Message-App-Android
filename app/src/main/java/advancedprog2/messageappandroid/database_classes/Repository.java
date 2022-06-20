package advancedprog2.messageappandroid.database_classes;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import advancedprog2.messageappandroid.entities.Contact;
import advancedprog2.messageappandroid.entities.Message;
import advancedprog2.messageappandroid.entities.User;
import advancedprog2.messageappandroid.toShowClasses.ContactToShow;

public class Repository {
    private AppLocalDatabase localDb;
    private UserDao userDao;
    private ContactDao contactDao;
    private MessageDao messageDao;
//    private LiveData<List<ContactToShow>> contacts;
//    private LiveData<List<Message>> messages;
//    private DgDatabase dgDatabase;
    private LiveData<List<User>> allUsers;
    private LiveData<List<Contact>> allContacts;
    private LiveData<List<Message>> allMessages;
    private LiveData<UserWithContacts> userWithContacts;
    private LiveData<ContactWithMessages> contactWithMessages;

    public Repository(Application application) {
        localDb = AppLocalDatabase.getInstance(application.getApplicationContext());
        userDao = localDb.userDao();
        contactDao = localDb.contactDao();
        messageDao = localDb.messageDao();
//        dgDatabase = new DgDatabase();
//        AppLocalDatabase db = AppLocalDatabase.getInstance(application);
//        userDao = db.userDao();
        allUsers = userDao.getAllUsers();
        allContacts = contactDao.getContacts();
        allMessages = messageDao.getAllMessages();
    }

    // temporary
    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }
    public LiveData<List<Message>> getAllMessages() {
        return allMessages;
    }
    public LiveData<UserWithContacts> getUserWithContacts(String username) {
        userWithContacts = userDao.getContactsOfUser(username);
        return userWithContacts;
    }
    public LiveData<ContactWithMessages> getContactWithMessages(String user_contact) {
        contactWithMessages = contactDao.getMessagesWithContact(user_contact);
        return contactWithMessages;
    }

//    public void insert(User user) {
//        userDao.insert(user);
//    }

    public User getUserById(String username) {
        return userDao.getUserById(username);
    }


//    public LiveData<List<ContactToShow>> getContacts(String username) {
//        return dgDatabase.getContacts(username);
//    }

//    public LiveData<List<Message>> getMessages(String username, String contactId) {
//        return dgDatabase.getMessages(username, contactId);
//    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public void insert(User user){
        new InsertUserAsyncTask(userDao).execute(user);
    }
//    public void update(User user) {
//        new UpdateUserAsyncTask(userDao).execute(user);
//    }
//    public void delete(User user) {
//        new DeleteUserAsyncTask(userDao).execute(user);
//    }
//
////    public LiveData<List<User>> getAllUsers() {
////        return allUsers;
////    }
//
//    public LiveData<List<User>> getAllUsers() {
//        return localDb.userDao().getAllUsers();
//    }
//
//    public LiveData<User> getUserById(String id) {
//        return localDb.userDao().getUserById(id);
//    }
//
////    public AsyncTask<String, Void, LiveData<User>> getUserById(String id) {
////        GetUserAsyncTask uas = new GetUserAsyncTask(userDao);
////        return uas.execute(id);
////    }
//
    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao userDao;

        private InsertUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }
//
//    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void> {
//
//        private UserDao userDao;
//
//        private UpdateUserAsyncTask(UserDao userDao) {
//            this.userDao = userDao;
//        }
//
//        @Override
//        protected Void doInBackground(User... users) {
//            userDao.update(users[0]);
//            return null;
//        }
//    }
//
//    private static class DeleteUserAsyncTask extends AsyncTask<User, Void, Void> {
//
//        private UserDao userDao;
//
//        private DeleteUserAsyncTask(UserDao userDao) {
//            this.userDao = userDao;
//        }
//
//        @Override
//        protected Void doInBackground(User... users) {
//            userDao.delete(users[0]);
//            return null;
//        }
//    }
//
    private static class GetUserAsyncTask extends AsyncTask<String, Void, User> {

        private UserDao userDao;

        private GetUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected User doInBackground(String... id) {
            return userDao.getUserById(id[0]);
        }
    }
}
