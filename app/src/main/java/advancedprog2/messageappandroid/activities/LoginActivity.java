package advancedprog2.messageappandroid.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import advancedprog2.messageappandroid.R;
import advancedprog2.messageappandroid.database_classes.AppViewModel;
import advancedprog2.messageappandroid.entities.Contact;
import advancedprog2.messageappandroid.entities.User;

public class LoginActivity extends AppCompatActivity {

    private AppViewModel appViewModel;
    private List<User> allUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        appViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                allUsers = users;
            }
        });

        EditText edUserId = findViewById(R.id.loginUsername);
        EditText edPassword = findViewById(R.id.loginPassword);
        Button loginBtn = findViewById(R.id.loginSubmit);

        loginBtn.setOnClickListener(v -> {
            User u = getUserById(edUserId.getText().toString());
            if (u == null || !(u.getPassword().equals(edPassword.getText().toString()))) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
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