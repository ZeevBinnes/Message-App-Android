package advancedprog2.messageappandroid.database_classes;

import android.app.Application;
import android.icu.text.StringPrepParseException;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Date;
import java.util.List;

import advancedprog2.messageappandroid.entities.Message;
import advancedprog2.messageappandroid.entities.User;

public class AppViewModel extends AndroidViewModel {

    private Repository repository;

    public AppViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public LiveData<UserWithContacts> getContacts(String username) {
        return repository.getUserWithContacts(username);
    }

    public LiveData<List<Message>> getMessages(String user, String contact) {
        return repository.getContactsMessages(user, contact);
    }

    public MutableLiveData<String> errMsg() {
        return repository.errMsg;
    }

    public void insert(User user) {
        repository.insert(user);
    }

    public boolean login(User user, String token) {
        return repository.login(user, token);
    }

    public boolean register(User user, String token) {
        return repository.register(user, token);
    }

    public boolean addContact(String username, String contactId, String contactName, String contactServer) {
        return repository.addContact(username, contactId, contactName, contactServer);
    }

    public User getUserById(String username) {
        return repository.getUserById(username);
    }

    public void sendMessage(String user, String contact, String type, String content, String contactServer) {
        Date now = new Date();
        repository.addMessage(user, contact, type, content, true, now, contactServer);
    }

    public List<Message> getMessagesAsList(String user, String contact) {
        return repository.getMessagesAsList(user, contact);
    }

}
