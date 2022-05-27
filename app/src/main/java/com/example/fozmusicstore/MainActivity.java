package com.example.fozmusicstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fozmusicstore.data.UserRepository;
import com.example.fozmusicstore.data.models.User;
import com.example.fozmusicstore.interfaces.FinishListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, FinishListener<User> {

    private EditText txtUsername, txtPassword;
    private Button btnLogIn;
    private TextView txtRegister;

    private void initComponents(){
        txtUsername = findViewById(R.id.txt_username);
        txtPassword = findViewById(R.id.txt_password);
        btnLogIn = findViewById(R.id.btn_log_in);
        txtRegister = findViewById(R.id.txt_register);
    }

    private void checkLoggedInUser(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        if(prefs.contains("userKey")){
            UserRepository.find(prefs.getString("userKey", null), (data, msg) -> {

                UserRepository.LoggedInUser = data;

                if(data != null){
                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.clear();
                    editor.commit();
                }
            });
        }else{
            Toast.makeText(this, "PLEASE LOG IN FIRST", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkLoggedInUser();
        initComponents();

        txtRegister.setOnClickListener(this);

//        UserRepository.auth("tes123", "tes", this);

        btnLogIn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.equals(txtRegister)){
            Intent i = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(i);
        }else if(v.equals(btnLogIn)){
            String username, password;
            username = txtUsername.getText().toString();
            password = txtPassword.getText().toString();

            UserRepository.auth(username, password, this);
        }
    }


    @Override
    public void onFinish(User data, String msg) {
        UserRepository.LoggedInUser = data;
        if(data == null){
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }else{
//            Log.d("FIREBASE RESULT", data.getFullName());
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("userKey", data.getUserKey());
            editor.putString("username", data.getUsername());
            editor.putString("fullName", data.getFullName());
//            editor.putString("password", data.getPassword());

            editor.commit();

            Intent i = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(i);

            finish();
        }


    }
}