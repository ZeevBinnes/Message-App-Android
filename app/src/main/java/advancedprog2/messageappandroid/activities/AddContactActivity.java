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

        appViewModel.errMsg().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("OK")) {
                    errMsg.setText("");
                    backBtn.callOnClick();
                } else { errMsg.setText(s); }
            }
        });

        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, ContactsActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        addContactBtn.setOnClickListener(v -> {
            if (username.equals(edContactId.getText().toString())) {
                errMsg.setText("you can't add yourself as a contact");
                return;
            }
            boolean didAdd = appViewModel.addContact(username,
                    edContactId.getText().toString(),
                    edContactName.getText().toString(),
                    edContactServer.getText().toString());
            if (!didAdd) {
                errMsg.setText("you have this user as a contact already");
            }
            return;
        });
    }
}