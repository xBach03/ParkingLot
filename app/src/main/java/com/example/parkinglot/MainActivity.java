package com.example.parkinglot;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView Navigator;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Navigator = findViewById(R.id.btmNavigation);
        LoadFragment(new StatisticsFragment());
        Navigator.setOnItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if(id == R.id.historyManager) {
                LoadFragment(new ManagerFragment());
            } else if(id == R.id.reservation) {
                LoadFragment(new ReservationFragment());
            } else if(id == R.id.payment) {
                LoadFragment(new PaymentFragment());
            } else if(id == R.id.camera) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
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
}