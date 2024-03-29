package com.example.parkinglot.fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.parkinglot.R;
import com.example.parkinglot.database.DatabaseHelper;
import com.example.parkinglot.database.daos.ParkingspaceDao;
import com.example.parkinglot.database.daos.ReservationDao;
import com.example.parkinglot.database.entities.AuthenticationManager;
import com.example.parkinglot.database.entities.User;
import com.example.parkinglot.recyclerComponents.ParkingAdapter;

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
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    Button Reserver;
    AuthenticationManager userManager;

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
        // Find the TextView within the inflated layout
        RecyclerView recyclerView = rootView.findViewById(R.id.parkingRecycler);

        dbHelper = new DatabaseHelper(requireContext());
        db = dbHelper.getWritableDatabase();

        ParkingspaceDao parkDao = new ParkingspaceDao(db);
        ParkingAdapter parkingAdapter = new ParkingAdapter(parkDao.getAvailableSlots());
        recyclerView.setAdapter(parkingAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        // Set linear layout for items in recycler view
        recyclerView.setLayoutManager(layoutManager);


        Reserver = rootView.findViewById(R.id.btnReserver);
        Reserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userManager = AuthenticationManager.getInstance(requireContext());
                User current = userManager.getCurrentUser();
                int posId = parkingAdapter.getSelectedPosId();
//                Toast.makeText(requireContext(), "id: " + pos, Toast.LENGTH_SHORT).show();
                boolean reserveParking = parkDao.reserveSlot(posId);
                ReservationDao reserver = new ReservationDao(db);
                boolean reservation = reserver.reserve(posId, current);
                Toast.makeText(requireContext(), "ParkingSpace table: " + reserveParking + " Reservation table: " + reservation, Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }
}