package advancedprog2.messageappandroid.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import advancedprog2.messageappandroid.R;
import advancedprog2.messageappandroid.adapters.ContactsListAdapter;
import advancedprog2.messageappandroid.entities.Contact;

public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        List<Contact> contactsList = new ArrayList<>();
        contactsList.add(new Contact("a", "aa", "s", "hello", "12:34 5.6.22"));
        contactsList.add(new Contact("b", "bb", "s", "hello", "12:34 5.6.22"));
        contactsList.add(new Contact("c", "cc", "s", "hello", "12:34 5.6.22"));
        contactsList.add(new Contact("d", "dd", "s", "hello", "12:34 5.6.22"));
        contactsList.add(new Contact("e", "ee", "s", "hello", "12:34 5.6.22"));
        contactsList.add(new Contact("f", "ff", "s", "hello", "12:34 5.6.22"));
        contactsList.add(new Contact("g", "gg", "s", "hello", "12:34 5.6.22"));
        contactsList.add(new Contact("h", "hh", "s", "hello", "12:34 5.6.22"));
        contactsList.add(new Contact("i", "ii", "s", "hello", "12:34 5.6.22"));
        contactsList.add(new Contact("j", "jj", "s", "hello", "12:34 5.6.22"));
        contactsList.add(new Contact("k", "kk", "s", "hello", "12:34 5.6.22"));
        contactsList.add(new Contact("l", "ll", "s", "hello", "12:34 5.6.22"));
        contactsList.add(new Contact("m", "mm", "s", "hello", "12:34 5.6.22"));
        contactsList.add(new Contact("n", "nn", "s", "hello", "12:34 5.6.22"));
        contactsList.add(new Contact("o", "oo", "s", "hello", "12:34 5.6.22"));
        contactsList.add(new Contact("p", "pp", "s", "hello", "12:34 5.6.22"));

        RecyclerView contactsListLayout = findViewById(R.id.contactsList);
        final ContactsListAdapter adapter = new ContactsListAdapter(this);
        contactsListLayout.setAdapter(adapter);
        contactsListLayout.setLayoutManager(new LinearLayoutManager(this));

        adapter.setContacts(contactsList);


    }
}