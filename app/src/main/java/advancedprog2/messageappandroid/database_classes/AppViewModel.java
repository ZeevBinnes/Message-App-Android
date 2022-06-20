package advancedprog2.messageappandroid.database_classes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import advancedprog2.messageappandroid.entities.User;

public class AppViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<User>> users;

    public AppViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        users = repository.getAllUsers();
    }

    public void insert(User user) {
        repository.insert(user);
    }

    public void update(User user) {
        repository.update(user);
    }

    public void delete(User user) {
        repository.delete(user);
    }

    public User getUserById(String id) {
        List<User> userList = users.getValue();
        if (userList == null) return null;
        for (User user : userList) {
            if (user.getUsername().equals(id)) {
                return user;
            }
        }
        return null;
    }
}
