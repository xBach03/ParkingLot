package com.example.parkinglot;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.parkinglot.database.DatabaseHelper;
import com.example.parkinglot.database.daos.UserDao;
import com.example.parkinglot.database.entities.User;

public class RegisterActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Button BtnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        BtnRegister = findViewById(R.id.btnRegister);
        BtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText UserReg = findViewById(R.id.userNameReg);
                EditText PasswordReg = findViewById(R.id.passwordReg);
                EditText CfPasswordReg = findViewById(R.id.cfPasswordReg);
                UserDao user = new UserDao(db);
                String username = UserReg.getText().toString();
                String password = PasswordReg.getText().toString();
                String confirmpassword = CfPasswordReg.getText().toString();
                if(password.equals(confirmpassword)) {
                    user.insertUser(new User(username, confirmpassword), RegisterActivity.this);
                    Toast.makeText(RegisterActivity.this, "You have registered as " + username, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    i.setClass(RegisterActivity.this, MainActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(RegisterActivity.this, "Confirmation password does not match original one", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}