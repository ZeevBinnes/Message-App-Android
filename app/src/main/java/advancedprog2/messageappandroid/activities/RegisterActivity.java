package advancedprog2.messageappandroid.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import advancedprog2.messageappandroid.R;
import advancedprog2.messageappandroid.database_classes.AppViewModel;
import advancedprog2.messageappandroid.entities.User;

public class RegisterActivity extends AppCompatActivity {

    private List<User> allUsers;
    private AppViewModel appViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        appViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                allUsers = users;
            }
        });

        allUsers = appViewModel.getAllUsers().getValue();

        EditText edUserId = findViewById(R.id.registerUsername);
        EditText edPassword = findViewById(R.id.registerPassword);
        Button loginBtn = findViewById(R.id.registerSubmit);

        loginBtn.setOnClickListener(v -> {
            User u = getUserById(edUserId.getText().toString());
            if (u != null) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                appViewModel.getAllUsers().removeObservers(this);
                appViewModel.insert(new User(edUserId.getText().toString(), edPassword.getText().toString()));
                Intent intent = new Intent(this, ContactsActivity.class);
                intent.putExtra("username", edUserId.getText().toString());
                startActivity(intent);
            }
        });
    }
    private User getUserById(String username) {
        for (User u : allUsers) {
            if (u.getUsername().equals(username)){
                return u;
            }
        }
        return null;
    }
}