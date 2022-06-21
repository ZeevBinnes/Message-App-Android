package advancedprog2.messageappandroid.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import advancedprog2.messageappandroid.R;
import advancedprog2.messageappandroid.database_classes.AppViewModel;

public class AddContactActivity extends AppCompatActivity {

    private AppViewModel appViewModel;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        if (getIntent().hasExtra("username")) {
            username = getIntent().getStringExtra("username");
        }

        EditText edContactId = findViewById(R.id.addContact_contactId);
        EditText edContactName = findViewById(R.id.addContact_contactName);
        EditText edContactServer = findViewById(R.id.addContact_contactServer);
        Button addContactBtn = findViewById(R.id.addContact_btn);
        Button backBtn = findViewById(R.id.addContact_btnBack);
        TextView errMsg = findViewById(R.id.addContact_errMsg);

        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, ContactsActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        addContactBtn.setOnClickListener(v -> {
            boolean didAdd = appViewModel.addContact(username,
                    edContactId.getText().toString(),
                    edContactName.getText().toString(),
                    edContactServer.getText().toString());
            if (didAdd) {
                Intent intent = new Intent(this, ContactsActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            } else {
                errMsg.setText("Failed To Add Contact.");
                return;
            }
        });
    }
}