package advancedprog2.messageappandroid.database_classes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import advancedprog2.messageappandroid.entities.Contact;
import advancedprog2.messageappandroid.entities.Message;
import advancedprog2.messageappandroid.entities.User;
import advancedprog2.messageappandroid.toShowClasses.ContactToShow;

public class AppViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<User>> users;
    private LiveData<List<Contact>> contacts;
    private LiveData<List<Message>> messages;

    public AppViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        contacts = repository.getAllContacts();
        messages = repository.getAllMessages();
        users = repository.getAllUsers();
    }

    public LiveData<List<Contact>> getContacts() {
        return contacts;
    }
    public LiveData<List<Message>> getMessages() {
        return messages;
    }
    public LiveData<List<User>> getAllUsers() {return users;}

    public LiveData<UserWithContacts> getContacts(String username) {
        return repository.getUserWithContacts(username);
    }
    public LiveData<ContactWithMessages> getMessages(String user_contact) {
        return repository.getContactWithMessages(user_contact);
    }

    public void insert(User user) {
        repository.insert(user);
    }

    public User getUserById(String username) {
        return repository.getUserById(username);
    }

//    public LiveData<List<ContactToShow>> getContacts(String username) {
//        return repository.getContacts(username);
//    }
//
//    public LiveData<List<Message>> getMessages(String username, String contactId) {
//        return repository.getMessages(username, contactId);
//    }

//    public void update(User user) {
//        repository.update(user);
//    }
//
//    public void delete(User user) {
//        repository.delete(user);
//    }

//    public User getUserById(String id) {
//        List<User> userList = users.getValue();
//        if (userList == null) return null;
//        for (User user : userList) {
//            if (user.getUsername().equals(id)) {
//                return user;
//            }
//        }
//        return null;
//    }

//    public LiveData<User> getUserById(String id) {
//        LiveData<User> u = repository.getUserById(id);
//        return u.getValue();
//    }
}
