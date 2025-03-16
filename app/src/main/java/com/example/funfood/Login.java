package com.example.funfood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog;

public class Login extends AppCompatActivity {

    EditText email,password;
    String user,pass;
    Button blogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        blogin=findViewById(R.id.btn_login);
        blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user=email.getText().toString();
                pass=password.getText().toString();
                //jika benar
                if(user.equals("example")&&pass.equals("12345678")) {
                    Toast.makeText(getApplicationContext(),"Login Berhasil", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this,Dashboard.class));
                    finish();
                }
            }
        });
    }
}