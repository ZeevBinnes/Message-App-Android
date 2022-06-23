package advancedprog2.messageappandroid.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import advancedprog2.messageappandroid.R;
import advancedprog2.messageappandroid.adapters.ContactsListAdapter;
import advancedprog2.messageappandroid.database_classes.AppViewModel;
import advancedprog2.messageappandroid.database_classes.UserWithContacts;

public class ContactsActivity extends AppCompatActivity {

    private AppViewModel appViewModel;
    private String username;

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


        RecyclerView contactsListLayout = findViewById(R.id.contactsList);
        final ContactsListAdapter adapter = new ContactsListAdapter(this, username);
        contactsListLayout.setAdapter(adapter);
        contactsListLayout.setLayoutManager(new LinearLayoutManager(this));

        appViewModel.getContacts(username).observe(this, new Observer<UserWithContacts>() {
            @Override
            public void onChanged(UserWithContacts userWithContacts) {
                adapter.setContacts(userWithContacts.contacts);
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
