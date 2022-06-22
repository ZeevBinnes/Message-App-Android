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

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this,
                instanceIdResult ->
                        Session.Token = instanceIdResult.getToken());

        EditText edUserId = findViewById(R.id.registerUsername);
        EditText edPassword = findViewById(R.id.registerPassword);
        Button loginBtn = findViewById(R.id.registerSubmit);
        TextView errMsg = findViewById(R.id.registerErrMsg);

        loginBtn.setOnClickListener(v -> {
            User u = new User(edUserId.getText().toString(), edPassword.getText().toString());
            if (appViewModel.findUserInWeb(u.getUsername())) {
                errMsg.setText("Register Failed. please try a different username");
                return;
            } else {
                if (checkValidity(u.getUsername(), u.getPassword())) {
                    if (appViewModel.register(u, Session.Token)) {
                        Intent intent = new Intent(this, ContactsActivity.class);
                        intent.putExtra("username", u.getUsername());
                        startActivity(intent);
                    } else {
                        errMsg.setText("Register Failed");
                    }
                } else {
                    errMsg.setText("invalid username or password");
                }
            }


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
    }

    private boolean checkValidity(String username, String password) {
        return true;
    }
}