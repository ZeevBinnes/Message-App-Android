package advancedprog2.messageappandroid.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.List;

import advancedprog2.messageappandroid.R;
import advancedprog2.messageappandroid.database_classes.AppViewModel;
import advancedprog2.messageappandroid.database_classes.UserWithContacts;
import advancedprog2.messageappandroid.entities.Contact;
import advancedprog2.messageappandroid.entities.Session;
import advancedprog2.messageappandroid.entities.User;

public class LoginActivity extends AppCompatActivity {

    private AppViewModel appViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(LoginActivity.this,
                instanceIdResult ->
                        Session.Token = instanceIdResult.getToken());

        EditText edUserId = findViewById(R.id.loginUsername);
        EditText edPassword = findViewById(R.id.loginPassword);
        Button loginBtn = findViewById(R.id.loginSubmit);
        Button btnToRegister = findViewById(R.id.login_btnToRegister);
        TextView errMsg = findViewById(R.id.loginErrMsg);

        appViewModel.errMsg().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("OK")) {
                    errMsg.setText("");
                    serverLogin(edUserId.getText().toString());
                } else { errMsg.setText(s); }
            }
        });

        loginBtn.setOnClickListener(v -> {

            User u = new User(edUserId.getText().toString(), edPassword.getText().toString());
            if (appViewModel.login(u, Session.Token)) {
                Intent intent = new Intent(this, ContactsActivity.class);
                intent.putExtra("username", edUserId.getText().toString());
                startActivity(intent);
            } else {
                return;
            }
        });

        btnToRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void serverLogin(String username) {
        Intent intent = new Intent(this, ContactsActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

}