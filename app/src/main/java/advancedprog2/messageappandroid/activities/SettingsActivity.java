package advancedprog2.messageappandroid.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import advancedprog2.messageappandroid.R;

public class SettingsActivity extends AppCompatActivity {

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (getIntent().hasExtra("username")) {
            username = getIntent().getStringExtra("username");
        }

        Button back = findViewById(R.id.settings_backBtn);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(this, ContactsActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });
    }
}