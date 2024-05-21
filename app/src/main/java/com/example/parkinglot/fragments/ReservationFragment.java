package com.example.parkinglot.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.parkinglot.R;
import com.example.parkinglot.database.DatabaseHelper;
import com.example.parkinglot.database.daos.ParkingSlotDao;
import com.example.parkinglot.database.daos.ReservationDao;
import com.example.parkinglot.database.AuthenticationManager;
import com.example.parkinglot.database.entities.User;
import com.example.parkinglot.recyclerComponents.ParkingAdapter;

import java.time.LocalDateTime;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReservationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReservationFragment extends Fragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Button Reserver;
    private ParkingSlotDao parkDao;
    private ParkingAdapter parkingAdapter;
    private AuthenticationManager userManager;
    private LocalDateTime startTime;

    public ReservationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReservationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReservationFragment newInstance(String param1, String param2) {
        ReservationFragment fragment = new ReservationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    private void showDatePicker() {
        DatePickerFragment dateFragment = new DatePickerFragment();
        dateFragment.setListener(ReservationFragment.this);
        dateFragment.show(getParentFragmentManager(), "datePicker");
    }
    private void showTimePicker() {
        TimePickerFragment timeFragment = new TimePickerFragment();
        timeFragment.setListener(ReservationFragment.this);
        timeFragment.show(getParentFragmentManager(), "timePicker");
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        startTime = LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0);
        showTimePicker();
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        LocalDateTime now = LocalDateTime.now();
        startTime = startTime.withHour(hourOfDay).withMinute(minute).withSecond(0).withNano(0);

        if(startTime != null && startTime.isAfter(LocalDateTime.now())) {
            userManager = AuthenticationManager.getInstance(requireContext());
            User current = userManager.getCurrentUser();
            int posId = parkingAdapter.getSelectedPosId();
            boolean reserveParking = parkDao.reserveSlot(posId);
            ReservationDao reserver = new ReservationDao(db);
            boolean reservation = reserver.reserve(posId, current, startTime);
            if (reservation && reserveParking) {
                // Update dataset with new list of available slots
                parkingAdapter.updateDataSet(parkDao.getAvailableSlots());
                // Notify adapter of dataset change
                parkingAdapter.notifyDataSetChanged();
                Toast.makeText(requireContext(), "Successfully reserved seleted slot", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_reservation, container, false);
        // Find the TextView within the inflated layout
        RecyclerView recyclerView = rootView.findViewById(R.id.parkingRecycler);

        dbHelper = DatabaseHelper.getInstance(requireContext());
        db = dbHelper.getWritableDatabase();

        parkDao = new ParkingSlotDao(db);
        parkingAdapter = new ParkingAdapter(parkDao.getAvailableSlots());
        recyclerView.setAdapter(parkingAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        // Set linear layout for items in recycler view
        recyclerView.setLayoutManager(layoutManager);


        Reserver = rootView.findViewById(R.id.btnReserver);
        Reserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        return rootView;
    }



}