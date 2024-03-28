package com.example.parkinglot.activity;

import android.content.ActivityNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.parkinglot.fragment.ManagerFragment;
import com.example.parkinglot.fragment.PaymentFragment;
import com.example.parkinglot.R;
import com.example.parkinglot.fragment.ReservationFragment;
import com.example.parkinglot.fragment.StatisticsFragment;
import com.example.parkinglot.database.DatabaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView Navigator;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = DatabaseHelper.getInstance(this);
        db = dbHelper.getWritableDatabase();
        LoadFragment(new StatisticsFragment());
        Navigator = findViewById(R.id.btmNavigation);
        Navigator.setOnItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if(id == R.id.historyManager) {
                LoadFragment(new ManagerFragment());
            } else if(id == R.id.reservation) {
                LoadFragment(new ReservationFragment());
            } else if(id == R.id.payment) {
                LoadFragment(new PaymentFragment());
            } else if(id == R.id.camera) {
                try {
                    mTakePicture.launch(null);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(MainActivity.this, "Exception: " + e, Toast.LENGTH_SHORT).show();
                }
            } else {
                LoadFragment(new StatisticsFragment());
            }
            return true;
        });
    }
    private void LoadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.replace(R.id.fragment_container_view, fragment, null);
        transaction.commit();
    }
    ActivityResultLauncher<Uri> mTakePicture = registerForActivityResult(new ActivityResultContracts.TakePicture(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean success) {
                    // Handle the result of taking a picture
                    if (success) {
                        // The picture was taken successfully
                    } else {
                        // Failed to take a picture
                    }
                }
            });
}