package advancedprog2.messageappandroid.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

//import android.arch.lifecycle.ViewModelProviders;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import advancedprog2.messageappandroid.R;
import advancedprog2.messageappandroid.activities.ContactsActivity;
import advancedprog2.messageappandroid.database_classes.AppViewModel;
import advancedprog2.messageappandroid.entities.Session;

public class MainActivity extends AppCompatActivity {

    private AppViewModel appViewModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        //appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);

        Button btnToContacts = findViewById(R.id.btnToContacts);
        btnToContacts.setOnClickListener(view -> {
            Intent intent = new Intent(this, ContactsActivity.class);
            startActivity(intent);
        });

        Button btnToChat = findViewById(R.id.btnToChat);
        btnToChat.setOnClickListener(view -> {
            Intent intent = new Intent(this, ChatActivity.class);
            startActivity(intent);
        });

        Button btnToLogin = findViewById(R.id.btnToLogin);
        btnToLogin.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        Button btnToRegister = findViewById(R.id.btnToRegister);
        btnToRegister.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

    }
}