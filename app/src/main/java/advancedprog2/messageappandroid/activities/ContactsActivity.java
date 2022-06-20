package advancedprog2.messageappandroid.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import advancedprog2.messageappandroid.R;
import advancedprog2.messageappandroid.adapters.ContactsListAdapter;
import advancedprog2.messageappandroid.entities.Contact;
import advancedprog2.messageappandroid.toShowClasses.ContactToShow;

public class ContactsActivity extends AppCompatActivity {

    public String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        if (getIntent().hasExtra("username")) {
            username = getIntent().getStringExtra("username");
            TextView userNameChatBar = findViewById(R.id.usrNameContactsBar);
            userNameChatBar.setText(username);
        }

        List<ContactToShow> contactsList = new ArrayList<>();
        contactsList.add(new ContactToShow("a", "aa", "s", "hello", "12:34 5.6.22"));
        contactsList.add(new ContactToShow("b", "bb", "s", "hello", "12:34 5.6.22"));
        contactsList.add(new ContactToShow("c", "cc", "s", "hello", "12:34 5.6.22"));
        contactsList.add(new ContactToShow("d", "dd", "s", "hello", "12:34 5.6.22"));
        contactsList.add(new ContactToShow("e", "ee", "s", "hello", "12:34 5.6.22"));
        contactsList.add(new ContactToShow("f", "ff", "s", "hello", "12:34 5.6.22"));
        contactsList.add(new ContactToShow("g", "gg", "s", "hello", "12:34 5.6.22"));
        contactsList.add(new ContactToShow("h", "hh", "s", "hello", "12:34 5.6.22"));
        contactsList.add(new ContactToShow("i", "ii", "s", "hello", "12:34 5.6.22"));
        contactsList.add(new ContactToShow("j", "jj", "s", "hello", "12:34 5.6.22"));
        contactsList.add(new ContactToShow("k", "kk", "s", "hello", "12:34 5.6.22"));
        contactsList.add(new ContactToShow("l", "ll", "s", "hello", "12:34 5.6.22"));
        contactsList.add(new ContactToShow("m", "mm", "s", "hello", "12:34 5.6.22"));
        contactsList.add(new ContactToShow("n", "nn", "s", "hello", "12:34 5.6.22"));
        contactsList.add(new ContactToShow("o", "oo", "s", "hello", "12:34 5.6.22"));
        contactsList.add(new ContactToShow("p", "pp", "s", "hello", "12:34 5.6.22"));

        RecyclerView contactsListLayout = findViewById(R.id.contactsList);
        final ContactsListAdapter adapter = new ContactsListAdapter(this);
        contactsListLayout.setAdapter(adapter);
        contactsListLayout.setLayoutManager(new LinearLayoutManager(this));

        adapter.setContacts(contactsList);


    }
}