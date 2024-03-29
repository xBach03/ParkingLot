package com.example.parkinglot.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.parkinglot.R;
import com.example.parkinglot.database.DatabaseHelper;
import com.example.parkinglot.database.daos.UserDao;
import com.example.parkinglot.database.entities.User;

public class RegisterActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private TextView LoginText;
    private Button BtnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbHelper = DatabaseHelper.getInstance(this);
        db = dbHelper.getWritableDatabase();
        LoginText = findViewById(R.id.loginText);
        LoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        // Set onclick for register button
        BtnRegister = findViewById(R.id.btnRegister);
        BtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText UserReg = findViewById(R.id.userNameReg);
                EditText PasswordReg = findViewById(R.id.passwordReg);
                EditText CfPasswordReg = findViewById(R.id.cfPasswordReg);
                UserDao userDao = new UserDao(db);
                String username = UserReg.getText().toString();
                String password = PasswordReg.getText().toString();
                String confirmpassword = CfPasswordReg.getText().toString();
                // Check if username already exists
                boolean check = userDao.insertUser(new User(username, confirmpassword));
                if(password.equals(confirmpassword) && check) {
                    Toast.makeText(RegisterActivity.this, "You have registered as " + username, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    i.setClass(RegisterActivity.this, MainActivity.class);
                    startActivity(i);
                } else if(!check) {
                    Toast.makeText(RegisterActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Confirmation password does not match original one", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}