package advancedprog2.messageappandroid.database_classes;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import advancedprog2.messageappandroid.entities.User;

public class Repository {
    private UserDao userDao;
    private LiveData<List<User>> allUsers;

    public Repository(Application application) {
        AppLocalDatabase db = AppLocalDatabase.getInstance(application);
        userDao = db.userDao();
        allUsers = userDao.getAllUsers();
    }

    public void insert(User user){
        new InsertUserAsyncTask(userDao).execute(user);
    }
    public void update(User user) {
        new UpdateUserAsyncTask(userDao).execute(user);
    }
    public void delete(User user) {
        new DeleteUserAsyncTask(userDao).execute(user);
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

//    public AsyncTask<String, Void, LiveData<User>> getUserById(String id) {
//        GetUserAsyncTask uas = new GetUserAsyncTask(userDao);
//        return uas.execute(id);
//    }

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

    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao userDao;

        private UpdateUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.update(users[0]);
            return null;
        }
    }

    private static class DeleteUserAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao userDao;

        private DeleteUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.delete(users[0]);
            return null;
        }
    }

    private static class GetUserAsyncTask extends AsyncTask<String, Void, LiveData<User>> {

        private UserDao userDao;

        private GetUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected LiveData<User> doInBackground(String... id) {
            LiveData<User> user = userDao.getUserById(id[0]);
            return user;
        }
    }
}
