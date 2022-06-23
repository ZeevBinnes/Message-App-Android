package advancedprog2.messageappandroid.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.List;
import java.util.Locale;

import advancedprog2.messageappandroid.R;
import advancedprog2.messageappandroid.database_classes.AppViewModel;
import advancedprog2.messageappandroid.entities.Session;
import advancedprog2.messageappandroid.entities.User;

public class RegisterActivity extends AppCompatActivity {

    private AppViewModel appViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);



        EditText edUserId = findViewById(R.id.registerUsername);
        EditText edPassword = findViewById(R.id.registerPassword);
        Button loginBtn = findViewById(R.id.registerSubmit);
        Button btnToLogin = findViewById(R.id.register_btnToLogin);
        TextView errMsg = findViewById(R.id.registerErrMsg);

        loginBtn.setOnClickListener(v -> {
            String username = edUserId.getText().toString();
            String password = edPassword.getText().toString();
            String errorMessage[] = {""};
            if (!checkUsernameValidity(username, errorMessage) || !checkPasswordValidity(password, errorMessage)) {
                errMsg.setText(errorMessage[0]);
                return;
            }
            User u = new User(username, password);
            if (appViewModel.register(u, Session.Token)) {
                Intent intent = new Intent(this, ContactsActivity.class);
                intent.putExtra("username", u.getUsername());
                startActivity(intent);
            } else {
                errMsg.setText("Register Failed.\n" +
                        "might be waiting for response from the web.\n" +
                        "if that is the case, wait a few seconds and try to Login.");
                return;
            }
//
//            if (appViewModel.findUserInWeb(u.getUsername())) {
//                errMsg.setText("Register Failed. please try a different username");
//                return;
//            } else {
//                if (checkValidity(u.getUsername(), u.getPassword())) {
//                    if (appViewModel.register(u, Session.Token)) {
//                        Intent intent = new Intent(this, ContactsActivity.class);
//                        intent.putExtra("username", u.getUsername());
//                        startActivity(intent);
//                    } else {
//                        errMsg.setText("Register Failed");
//                    }
//                } else {
//                    errMsg.setText("invalid username or password");
//                }
//            }


//            User u = appViewModel.getUserById(edUserId.getText().toString());
//            if (u != null) {
//                errMsg.setText("Register Failed. please try a different username");
//                return;
//            } else {
//                appViewModel.insert(new User(edUserId.getText().toString(), edPassword.getText().toString()));
//                Intent intent = new Intent(this, ContactsActivity.class);
//                intent.putExtra("username", edUserId.getText().toString());
//                startActivity(intent);
//            }
        });

        btnToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private boolean checkUsernameValidity(String username, String errorMessage[]) {
        if (username.length() < 3) {
            errorMessage[0] = "username should be al least 3 characters";
            return false;
        }
        if (!username.matches("^[a-zA-Z0-9]+$")) {
            errorMessage[0] = "username should contain only characters and numbers";
            return false;
        }
        return true;
    }

    private boolean checkPasswordValidity(String password, String errorMessage[]) {
        if (password.length() < 4) {
            errorMessage[0] = "password should be al least 4 characters";
            return false;
        }
        boolean hasLetter = false;
        boolean hasDigit = false;
        for (char c : password.toCharArray()) {
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) hasLetter = true;
            if (c >= '0' && c <= '9') hasDigit = true;
        }
        if (!hasLetter) {
            errorMessage[0] = "password should contain at least one letter";
            return false;
        }
        if (!hasDigit) {
            errorMessage[0] = "password should contain at least one digit";
            return false;
        }
        return true;
    }
}