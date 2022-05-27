package com.example.fozmusicstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class HomeActivity extends AppCompatActivity{

    private Button btnLogout;
    private ImageButton btnChat;

    private void initComponents(){
        btnLogout = findViewById(R.id.btn_log_out);
        btnChat = findViewById(R.id.btn_chat);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initComponents();

//        btnChat.setOnClickListener(this);

        btnLogout.setOnClickListener(v -> {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.commit();
            Intent i = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        });

        btnChat.setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, ChatActivity.class);
            startActivity(i);
        });
    }


}