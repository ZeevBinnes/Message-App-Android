package advancedprog2.messageappandroid.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import advancedprog2.messageappandroid.adapters.MessagesListAdapter;
import advancedprog2.messageappandroid.entities.Message;

import advancedprog2.messageappandroid.R;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        if (getIntent().hasExtra("contactName")) {
            String contactName = getIntent().getStringExtra("contactName");
            TextView contactNameChatBar = findViewById(R.id.contactNameChatBar);
            contactNameChatBar.setText(contactName);
        }
        if (getIntent().hasExtra("contactId")) {
            String contactId = getIntent().getStringExtra("contactId");
            // for handling data soon
        }

        List<Message> messageList = new ArrayList<>();
        messageList.add(new Message("aaaa", new Date(), true));
        messageList.add(new Message("bbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbb bbbbbbbbbbbbbbb", new Date(), false));
        messageList.add(new Message("cccc", new Date(), true));
        messageList.add(new Message("dddd", new Date(), false));
        messageList.add(new Message("aaaa", new Date(), true));
        messageList.add(new Message("bbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbb bbbbbbbbbbbbbbb", new Date(), false));
        messageList.add(new Message("cccc", new Date(), true));
        messageList.add(new Message("dddd", new Date(), false));
        messageList.add(new Message("aaaa", new Date(), true));
        messageList.add(new Message("bbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbb bbbbbbbbbbbbbbb", new Date(), true));
        messageList.add(new Message("cccc", new Date(), true));
        messageList.add(new Message("dddd", new Date(), false));
        messageList.add(new Message("aaaa", new Date(), true));
        messageList.add(new Message("bbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbb bbbbbbbbbbbbbbb", new Date(), false));
        messageList.add(new Message("cccc", new Date(), true));
        messageList.add(new Message("dddd", new Date(), false));
        messageList.add(new Message("aaaa", new Date(), true));
        messageList.add(new Message("bbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbb bbbbbbbbbbbbbbb", new Date(), false));
        messageList.add(new Message("cccc", new Date(), false));
        messageList.add(new Message("dddd", new Date(), false));
        messageList.add(new Message("aaaa", new Date(), true));
        messageList.add(new Message("bbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbb bbbbbbbbbbbbbbb", new Date(), true));
        messageList.add(new Message("cccc", new Date(), true));
        messageList.add(new Message("dddd", new Date(), false));
        messageList.add(new Message("aaaa", new Date(), true));
        messageList.add(new Message("bbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbb bbbbbbbbbbbbbbb", new Date(), false));
        messageList.add(new Message("cccc", new Date(), true));
        messageList.add(new Message("dddd", new Date(), false));

        RecyclerView messagesListLayout = findViewById(R.id.messagesList);
        final MessagesListAdapter adapter = new MessagesListAdapter(this);
        messagesListLayout.setAdapter(adapter);
        messagesListLayout.setLayoutManager(new LinearLayoutManager(this));

        adapter.setMessages(messageList);

        ImageButton chatBackBtn = findViewById(R.id.chatBackBtn);
        chatBackBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, ContactsActivity.class);
            startActivity(intent);
        });



    }
}