package com.example.parkinglot.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.parkinglot.fragment.CameraFragment;
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
    private Toolbar topBar;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PERMISSIONS = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = DatabaseHelper.getInstance(this);
        db = dbHelper.getWritableDatabase();
        topBar = findViewById(R.id.mainToolbar);
        setSupportActionBar(topBar);
        topBar.setTitle("Statistic");
        LoadFragment(new StatisticsFragment());
        Navigator = findViewById(R.id.btmNavigation);
        Navigator.setOnItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if(id == R.id.historyManager) {
                topBar.setTitle("Manager");
                LoadFragment(new ManagerFragment());
            } else if(id == R.id.reservation) {
                topBar.setTitle("Reservation");
                LoadFragment(new ReservationFragment());
            } else if(id == R.id.payment) {
                topBar.setTitle("Payment");
                LoadFragment(new PaymentFragment());
            } else if(id == R.id.camera) {
                topBar.setTitle("Number plate recognizer");
//                dispatchTakePictureIntent();
                LoadFragment(new CameraFragment());
            } else {
                LoadFragment(new StatisticsFragment());
                topBar.setTitle("Statistic");
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
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }
}