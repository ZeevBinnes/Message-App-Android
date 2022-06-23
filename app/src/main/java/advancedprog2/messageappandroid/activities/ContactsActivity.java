package advancedprog2.messageappandroid.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import advancedprog2.messageappandroid.R;
import advancedprog2.messageappandroid.adapters.ContactsListAdapter;
import advancedprog2.messageappandroid.database_classes.AppViewModel;
import advancedprog2.messageappandroid.database_classes.UserWithContacts;
import advancedprog2.messageappandroid.entities.Contact;

public class ContactsActivity extends AppCompatActivity {

    private AppViewModel appViewModel;
    private String username;
    private List<Contact> allContacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        if (getIntent().hasExtra("username")) {
            username = getIntent().getStringExtra("username");
            TextView userNameChatBar = findViewById(R.id.usrNameContactsBar);
            userNameChatBar.setText(username);
        }

        ImageButton addContactBtn = findViewById(R.id.btnAddContact);
        addContactBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddContactActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        ImageButton settingsBtn = findViewById(R.id.btnSettings);
        settingsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });



        RecyclerView contactsListLayout = findViewById(R.id.contactsList);
        final ContactsListAdapter adapter = new ContactsListAdapter(this, username);
        contactsListLayout.setAdapter(adapter);
        contactsListLayout.setLayoutManager(new LinearLayoutManager(this));

        appViewModel.getContacts(username).observe(this, new Observer<UserWithContacts>() {
            @Override
            public void onChanged(UserWithContacts userWithContacts) {
                allContacts = userWithContacts.contacts;
                adapter.setContacts(userWithContacts.contacts);
            }
        });

        EditText edSearch = findViewById(R.id.contacts_search);
        edSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                List<Contact> contactsToShow = new ArrayList<>();
                for (Contact c : allContacts) {
                    if (c.getName().contains(edSearch.getText().toString())) {
                        contactsToShow.add(c);
                    }
                }
                adapter.setContacts(contactsToShow);
                return false;
            }
        });

//        appViewModel.getContacts().observe(this, new Observer<List<Contact>>() {
//            @Override
//            public void onChanged(List<Contact> contacts) {
//                adapter.setContacts(contacts);
//            }
//        });



    }
}
