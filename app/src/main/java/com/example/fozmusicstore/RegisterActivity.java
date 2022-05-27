package com.example.fozmusicstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fozmusicstore.data.UserRepository;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtFullname, txtUsername, txtPassword;
    private Button btnRegister;
    private TextView txtLogin;

    private void initComponents(){
        txtFullname = findViewById(R.id.txt_fullname);
        txtUsername = findViewById(R.id.txt_username);
        txtPassword = findViewById(R.id.txt_password);
        btnRegister = findViewById(R.id.btn_register);
        txtLogin = findViewById(R.id.txt_login);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initComponents();

        txtLogin.setOnClickListener(this);

        btnRegister.setOnClickListener(v->{
            String fullName, username, password;

            fullName = txtFullname.getText().toString();
            username = txtUsername.getText().toString();
            password = txtPassword.getText().toString();

            boolean isValid = true;

            if(fullName.isEmpty() || username.isEmpty() || password.isEmpty()){
                txtFullname.setError("Please fill all fields!");
                isValid = false;
            }else if(password.length() < 8){
                txtPassword.setError("Password must be at least 8 characters!");
                isValid = false;
            }

            if(isValid){
                UserRepository.insertUser(fullName, username, password);
            }
        });
    }




    @Override
    public void onClick(View v) {
        if(v.equals(txtLogin)){
            Intent i = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(i);
        }
    }
}