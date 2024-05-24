package com.example.parkinglot.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.parkinglot.R;
import com.example.parkinglot.database.DatabaseHelper;

public class TransactionSuccessActivity extends AppCompatActivity {

    private TextView transactionId, transactionAmount, transactionUserId, transactionUserName;
    private Button navigateBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transaction_success);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        transactionId = findViewById(R.id.transactionId);
        transactionUserId = findViewById(R.id.transactionUserId);
        transactionUserName = findViewById(R.id.transactionUserName);
        transactionAmount = findViewById(R.id.transactionAmount);
        navigateBtn = findViewById(R.id.navigateBtn);

        // Get data from transaction activity
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String amount = extras.getString("transactAmount");
            String userId = extras.getString("userId");
            String userName = extras.getString("userName");
            long transactId = extras.getLong("transactId");

            transactionId.setText("Transaction ID: " + Long.valueOf(transactId).toString());
            transactionUserId.setText("User ID: " + userId);
            transactionUserName.setText("User name: " + userName);
            transactionAmount.setText("Amount: " + amount + "$");

        } else {

        }


        navigateBtn = findViewById(R.id.navigateBtn);
        navigateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TransactionSuccessActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}