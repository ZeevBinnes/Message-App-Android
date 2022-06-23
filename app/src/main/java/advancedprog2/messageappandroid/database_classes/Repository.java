package advancedprog2.messageappandroid.database_classes;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import advancedprog2.messageappandroid.api.AppApi;
import advancedprog2.messageappandroid.entities.Contact;
import advancedprog2.messageappandroid.entities.Message;
import advancedprog2.messageappandroid.entities.User;

public class Repository {
    private AppLocalDatabase localDb;
    private UserDao userDao;
    private ContactDao contactDao;
    private MessageDao messageDao;
    private LiveData<UserWithContacts> userWithContacts;
//    private LiveData<ContactWithMessages> contactWithMessages;
    private LiveData<List<Message>> messages;
//    private AppApi appApi;
    public MutableLiveData<String> errMsg;

    public Repository(Application application) {
        localDb = AppLocalDatabase.getInstance(application.getApplicationContext());
        userDao = localDb.userDao();
        contactDao = localDb.contactDao();
        messageDao = localDb.messageDao();
        //appApi = new AppApi(localDb, this);
        errMsg = new MutableLiveData<>();
    }

    public LiveData<UserWithContacts> getUserWithContacts(String username) {
        userWithContacts = userDao.getContactsOfUser(username);
        AppApi appApi = new AppApi(localDb, this);
        if (appApi != null) appApi.getContacts(username);
        return userWithContacts;
    }

    public LiveData<List<Message>> getContactsMessages(String user, String contact) {
        String user_contact = user+"-"+contact;
        messages = messageDao.getContactsMessages(user_contact);
        AppApi appApi = new AppApi(localDb, this);
        if(appApi != null) appApi.getMessages(user, contact);
        return messages;
    }
//    public LiveData<ContactWithMessages> getContactWithMessages(String user, String contact) {
//        String user_contact = user+"-"+contact;
//        contactWithMessages = contactDao.getMessagesWithContact(user_contact);
//        if(appApi != null) appApi.getMessages(user, contact);
//        return contactWithMessages;
//    }

    public User getUserById(String username) {
        try {
            return new GetUserAsyncTask(userDao).execute(username).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean login(User user, String token) {
        AppApi appApi = new AppApi(localDb, this);
        appApi.login(user, token);
        User u1 = getUserById(user.getUsername());
        if (u1 != null && u1.getPassword().equals(user.getPassword())) {
            return true;
        } else return false;
    }

    public boolean register(User user, String token) {
        AppApi appApi = new AppApi(localDb, this);
        appApi.register(user, token);
        return false;
    }

    public void insert(User user){
        new InsertUserAsyncTask(userDao).execute(user);
    }

    public Contact findContact(String username, String contactId) {
        try {
            return new GetContactAsyncTask(contactDao).execute(username, contactId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addContact(String username, String contactId, String contactName, String contactServer) {
        if (username.equals(contactId)) return false;
        if (this.findContact(username, contactId) != null) return false;
        Contact contact = new Contact(contactId, contactName, contactServer, null, null, username);
//        new InsertContactAsyncTask(userDao).execute(contact); // maybe not needed
        AppApi appApi = new AppApi(localDb, this);
        appApi.addContact(username, contact);
        return true;
    }

    public void addMessage(String username, String contactId, String type, String content, boolean sent, Date time, String contactServer) {
        String user_contact = username+"-"+contactId;
        Message message = new Message(content, time, sent, user_contact);
        new InsertMessageAsyncTask(messageDao).execute(message);
        new UpdateContactWithLast(contactDao).execute(username, contactId, content, message.getCreated());
        AppApi appApi = new AppApi(localDb, this);
        if(appApi != null) appApi.sendMessage(username, contactId, contactServer, message);
        return;
    }

    public List<Message> getMessagesAsList(String user, String contact) {
        String user_contact = user+"-"+contact;
        try {
            return new MessagesListAsyncTask(messageDao).execute(user_contact).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

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

    private static class InsertContactAsyncTask extends AsyncTask<Contact, Void, Void> {

        private UserDao userDao;

        private InsertContactAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            userDao.addContact(contacts[0]);
            return null;
        }
    }

    private static class InsertMessageAsyncTask extends AsyncTask<Message, Void, Void> {

        private MessageDao messageDao;

        private InsertMessageAsyncTask(MessageDao messageDao) {
            this.messageDao = messageDao;
        }

        @Override
        protected Void doInBackground(Message... messages) {
            messageDao.inset(messages[0]);
            return null;
        }
    }

    private static class UpdateContactWithLast extends AsyncTask<String, Void, Void> {

        private ContactDao contactDao;

        private UpdateContactWithLast(ContactDao contactDao) {
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            contactDao.updateLastMessage(strings[0], strings[1], strings[2], strings[3]);
            return null;
        }
    }

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

    private static class GetContactAsyncTask extends AsyncTask<String, Void, Contact> {

        private ContactDao contactDao;

        private GetContactAsyncTask(ContactDao contactDao) {
            this.contactDao = contactDao;
        }

        @Override
        protected Contact doInBackground(String... id) {
            return contactDao.findContact(id[0], id[1]);
        }
    }

    private static class MessagesListAsyncTask extends AsyncTask<String, Void, List<Message>> {

        private MessageDao messageDao;

        private MessagesListAsyncTask(MessageDao messageDao) {
            this.messageDao = messageDao;
        }

        @Override
        protected List<Message> doInBackground(String... strings) {
            messageDao.getMessagesAsList(strings[0]);
            return null;
        }
    }

}
