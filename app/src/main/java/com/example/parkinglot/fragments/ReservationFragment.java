package com.example.parkinglot.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
public class ReservationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button available, reserved;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_reservation, container, false);
        available = rootView.findViewById(R.id.horizontalMenuAvailable);
        reserved = rootView.findViewById(R.id.horizontalMenuReserved);
        configureHorizontalBar();

        LoadFragment(new AvailableFragment());
        // Set up top navigation bar
        return rootView;
    }
    public void configureHorizontalBar() {
        available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                available.setBackgroundColor(requireContext().getResources().getColor(R.color.setBtn));
                reserved.setBackgroundColor(requireContext().getResources().getColor(R.color.defaultBg));

                LoadFragment(new AvailableFragment());
            }
        });
        reserved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reserved.setBackgroundColor(requireContext().getResources().getColor(R.color.setBtn));
                available.setBackgroundColor(requireContext().getResources().getColor(R.color.defaultBg));
                LoadFragment(new ReservedFragment());
            }
        });
    }
    private void LoadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.replace(R.id.reservation_fragment_container, fragment, null);
        transaction.commit();
    }
}