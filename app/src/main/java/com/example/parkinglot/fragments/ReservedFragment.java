package com.example.parkinglot.fragments;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parkinglot.R;
import com.example.parkinglot.database.AuthenticationManager;
import com.example.parkinglot.database.DatabaseHelper;
import com.example.parkinglot.database.daos.ParkingSlotDao;
import com.example.parkinglot.database.daos.ReservationDao;
import com.example.parkinglot.database.entities.ParkingSlot;
import com.example.parkinglot.database.entities.Reservation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReservedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReservedFragment extends Fragment implements OnMapReadyCallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SQLiteDatabase db;
    private LatLng coordinates;

    private ImageView reservedVector;
    private TextView reservedLocation, reservedTime;
    private GoogleMap myMap;


    private AuthenticationManager authenticationManager;

    public ReservedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReservedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReservedFragment newInstance(String param1, String param2) {
        ReservedFragment fragment = new ReservedFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_reserved, container, false);
        authenticationManager = AuthenticationManager.getInstance(requireContext());
        db = DatabaseHelper.getInstance(requireContext()).getReadableDatabase();

        // Get today reservation
        ReservationDao reservationDao = new ReservationDao(db);
        Reservation reservation = reservationDao.getCurrentDayReservation(authenticationManager.getCurrentUser());

        ParkingSlotDao parkingSlotDao = new ParkingSlotDao(db);

        ParkingSlot reservedSlot = parkingSlotDao.getReservedArea(reservation.getParkingId());
        String reservedArea = reservedSlot.getArea();
        String vehicleType = reservedSlot.getVehicleType();
        String reservedSpot = reservedSlot.getParkingSpot();
        // Set up info bar
        reservedVector = rootView.findViewById(R.id.reservedVector);
        Drawable result;

        if(vehicleType.equals("Car")) {
            result = ContextCompat.getDrawable(requireContext(), R.drawable.car);
        } else if(vehicleType.equals("Motorcycle")) {
            result = ContextCompat.getDrawable(requireContext(), R.drawable.motorcycle);
        } else {
            result = ContextCompat.getDrawable(requireContext(), R.drawable.bike);
        }
        reservedVector.setImageDrawable(result);

        reservedLocation = rootView.findViewById(R.id.reservedLocation);
        reservedLocation.setText(reservedArea + " " + reservedSpot);

        reservedTime = rootView.findViewById(R.id.reservedTime);
        reservedTime.setText(reservation.getReservedTime().toString());

        // Configure coordinates for default marker and map zoom
        if(reservation == null) {
            coordinates = new LatLng(0, 0);
        } else {

            if(reservedArea.equals("C7")) {
                coordinates = new LatLng(21.005282585915346, 105.84517779999999);
            } else if(reservedArea.equals("D8")) {
                coordinates = new LatLng(21.00412417214672, 105.84299575560908);
            } else {
                coordinates = new LatLng(21.00480541246822, 105.845455681371);
            }
        }

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
        return rootView;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;
        googleMap.addMarker(new MarkerOptions()
                .position(coordinates)
                .title("Marker"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 19));
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }
}