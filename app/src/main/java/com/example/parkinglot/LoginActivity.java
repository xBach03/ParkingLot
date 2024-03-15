package com.example.parkinglot;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.parkinglot.database.DatabaseHelper;
import com.example.parkinglot.database.User;

public class LoginActivity extends AppCompatActivity {
    private Button loginBtn;
    private TextView accRgs;
    private DatabaseHelper dbHelper;
    private EditText userNameLogin, passwordLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbHelper = new DatabaseHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = findViewById(R.id.btnLogin);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameLogin = findViewById(R.id.userNameLogin);
                passwordLogin = findViewById(R.id.passwordLogin);
                String user = userNameLogin.getText().toString();
                String password = passwordLogin.getText().toString();
                User User = new User(user, password);
                InsertUser(User);
                Toast.makeText(LoginActivity.this, "You have login as " + user, Toast.LENGTH_SHORT).show();
                Intent i = new Intent();
                i.setClass(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        accRgs = findViewById(R.id.accRgs);
        accRgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }
    private void InsertUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", user.getUserName());
        values.put("password", user.getPassword());
        db.insert("user", null, values);
        db.close();
    }
}