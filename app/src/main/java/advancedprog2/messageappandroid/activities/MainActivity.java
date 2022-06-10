package advancedprog2.messageappandroid.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import advancedprog2.messageappandroid.R;
import advancedprog2.messageappandroid.activities.ContactsActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }
}