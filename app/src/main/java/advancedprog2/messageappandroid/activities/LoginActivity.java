package advancedprog2.messageappandroid.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import advancedprog2.messageappandroid.R;
import advancedprog2.messageappandroid.database_classes.AppViewModel;
import advancedprog2.messageappandroid.entities.User;

public class LoginActivity extends AppCompatActivity {

    private AppViewModel appViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        EditText edUserId = findViewById(R.id.loginUsername);
        EditText edPassword = findViewById(R.id.loginPassword);
        Button loginBtn = findViewById(R.id.loginSubmit);

        loginBtn.setOnClickListener(v -> {
            // I made no checking over here, its a temporary method!!!!!!!!!
            User u = appViewModel.getUserById(edUserId.getText().toString());
            if (u == null) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, ContactsActivity.class);
                intent.putExtra("username", edUserId.getText().toString());
                startActivity(intent);
            }
        });
    }
}