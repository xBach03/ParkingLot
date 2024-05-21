package com.example.parkinglot.activities;

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

import com.example.parkinglot.R;
import com.example.parkinglot.database.AuthenticationManager;
import com.example.parkinglot.database.DatabaseHelper;
import com.example.parkinglot.database.daos.PaymentDao;
import com.example.parkinglot.database.daos.TransactionDao;
import com.example.parkinglot.database.daos.UserDao;
import com.example.parkinglot.database.entities.Payment;

public class TransactionActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private EditText debitIdInputTransact, moneyTransact;
    private Button transactBtn;
    private AuthenticationManager authenticationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transaction);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        transactBtn = findViewById(R.id.btnTransact);
        transactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticationManager = AuthenticationManager.getInstance(TransactionActivity.this);
                debitIdInputTransact = findViewById(R.id.debitIdInputTransact);
                moneyTransact = findViewById(R.id.moneyTransact);
                dbHelper = DatabaseHelper.getInstance(TransactionActivity.this);
                db = dbHelper.getWritableDatabase();
                PaymentDao paymentDao = new PaymentDao(db);
                Payment transactPayment = paymentDao.transactById(debitIdInputTransact.toString(), Double.valueOf(moneyTransact.toString()));
                TransactionDao transactionDao = new TransactionDao(db);
                boolean transactor = transactionDao.insertTransaction(paymentDao.getCurrentPayment(authenticationManager.getCurrentUser()).getId(), debitIdInputTransact.toString(), Double.parseDouble(moneyTransact.toString()));
                if(transactPayment != null && transactor) {
                    UserDao userDao = new UserDao(db);
                    Intent i = new Intent(TransactionActivity.this, TransactionSuccessActivity.class);
                    i.putExtra("transactAmount", moneyTransact.toString());
                    i.putExtra("userId", debitIdInputTransact.toString());
                    i.putExtra("userName", userDao.getUserName(transactPayment.getUserId()));
                    startActivity(i);
                } else {
                    Toast.makeText(TransactionActivity.this, "Transaction failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}