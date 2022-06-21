package advancedprog2.messageappandroid.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import advancedprog2.messageappandroid.adapters.MessagesListAdapter;
import advancedprog2.messageappandroid.database_classes.AppViewModel;
import advancedprog2.messageappandroid.database_classes.ContactWithMessages;
import advancedprog2.messageappandroid.entities.Message;

import advancedprog2.messageappandroid.R;

public class ChatActivity extends AppCompatActivity {

    private AppViewModel appViewModel;
    private String username;
    private String contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        TextView contactNameChatBar = findViewById(R.id.contactNameChatBar);
        ImageButton chatBackBtn = findViewById(R.id.chatBackBtn);
        EditText edMessage = findViewById(R.id.inputMessage);
        ImageButton sendMessageBtn = findViewById(R.id.sendMessage);

        if (getIntent().hasExtra("username")) {
            username = getIntent().getStringExtra("username");
        }
        if (getIntent().hasExtra("contactId")) {
            contactId = getIntent().getStringExtra("contactId");
        }
        if (getIntent().hasExtra("contactName")) {
            String contactName = getIntent().getStringExtra("contactName");
            contactNameChatBar.setText(contactName);
        }

        RecyclerView messagesListLayout = findViewById(R.id.messagesList);
        final MessagesListAdapter adapter = new MessagesListAdapter(this);
        messagesListLayout.setAdapter(adapter);
        messagesListLayout.setLayoutManager(new LinearLayoutManager(this));

        appViewModel.getMessages(username+"-"+contactId).observe(this, new Observer<ContactWithMessages>() {
            @Override
            public void onChanged(ContactWithMessages contactWithMessages) {
                adapter.setMessages(contactWithMessages.messages);
            }
        });

//        appViewModel.getMessages().observe(this, new Observer<List<Message>>() {
//            @Override
//            public void onChanged(List<Message> messages) {
//                adapter.setMessages(messages);
//            }
//        });

        chatBackBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, ContactsActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        sendMessageBtn.setOnClickListener(v -> {
            appViewModel.sendMessage(username, contactId, "text", edMessage.getText().toString());
        });

//        List<Message> messageList = new ArrayList<>();
//        messageList.add(new Message("aaaa", new Date(), true));
//        messageList.add(new Message("bbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbb bbbbbbbbbbbbbbb", new Date(), false));
//        messageList.add(new Message("cccc", new Date(), true));
//        messageList.add(new Message("dddd", new Date(), false));
//        messageList.add(new Message("aaaa", new Date(), true));
//        messageList.add(new Message("bbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbb bbbbbbbbbbbbbbb", new Date(), false));
//        messageList.add(new Message("cccc", new Date(), true));
//        messageList.add(new Message("dddd", new Date(), false));
//        messageList.add(new Message("aaaa", new Date(), true));
//        messageList.add(new Message("bbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbb bbbbbbbbbbbbbbb", new Date(), true));
//        messageList.add(new Message("cccc", new Date(), true));
//        messageList.add(new Message("dddd", new Date(), false));
//        messageList.add(new Message("aaaa", new Date(), true));
//        messageList.add(new Message("bbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbb bbbbbbbbbbbbbbb", new Date(), false));
//        messageList.add(new Message("cccc", new Date(), true));
//        messageList.add(new Message("dddd", new Date(), false));
//        messageList.add(new Message("aaaa", new Date(), true));
//        messageList.add(new Message("bbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbb bbbbbbbbbbbbbbb", new Date(), false));
//        messageList.add(new Message("cccc", new Date(), false));
//        messageList.add(new Message("dddd", new Date(), false));
//        messageList.add(new Message("aaaa", new Date(), true));
//        messageList.add(new Message("bbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbb bbbbbbbbbbbbbbb", new Date(), true));
//        messageList.add(new Message("cccc", new Date(), true));
//        messageList.add(new Message("dddd", new Date(), false));
//        messageList.add(new Message("aaaa", new Date(), true));
//        messageList.add(new Message("bbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbb bbbbbbbbbbbbbbb", new Date(), false));
//        messageList.add(new Message("cccc", new Date(), true));
//        messageList.add(new Message("dddd", new Date(), false));

//        adapter.setMessages(messageList);



    }
}