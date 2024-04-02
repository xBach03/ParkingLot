package com.example.parkinglot.fragment;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parkinglot.R;
import com.example.parkinglot.database.DatabaseHelper;
import com.example.parkinglot.database.daos.ParkingSlotDao;
import com.example.parkinglot.database.entities.ParkingSlot;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticsFragment newInstance(String param1, String param2) {
        StatisticsFragment fragment = new StatisticsFragment();
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
//        dbHelper = new DatabaseHelper(requireContext());
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM user", null);
//        if (cursor.moveToFirst()) {
//            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
//            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndexOrThrow("username"));
//            System.out.println("id: " + id + " name: " + name);
//        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_statistics, container, false);
        // Find the TextView within the inflated layout
        BarChart barChart = rootView.findViewById(R.id.barChart);
        barChart.getAxisRight().setDrawLabels(false);

        dbHelper = DatabaseHelper.getInstance(requireContext());
        db = dbHelper.getWritableDatabase();
        ParkingSlotDao parkingDao = new ParkingSlotDao(db);

        // Query for all type of slots
        List<ParkingSlot> availableSlots = parkingDao.getAvailableSlots();
        List<ParkingSlot> reservedSlots = parkingDao.getReservedSlots();
        List<ParkingSlot> parkedSlots = parkingDao.getParkedSlots();
        List<String> type = Arrays.asList("Available", "Reserved", "Parked");

        // Configure entries for chart
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, availableSlots.size()));
        entries.add(new BarEntry(1, reservedSlots.size()));
        entries.add(new BarEntry(2, parkedSlots.size()));

        YAxis yAxis = barChart.getAxisLeft();
        // Set maximum for Oy = 50
        yAxis.setAxisMaximum(50);
        // Set minimum for Oy = 0
        yAxis.setAxisMinimum(0f);
        // Set the width for Oy
        yAxis.setAxisLineWidth(1f);
        // Set Oy color
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);

        BarDataSet bardataSet = new BarDataSet(entries, "Parking slots statistic");
        bardataSet.setColors(ColorTemplate.MATERIAL_COLORS, 255);
        bardataSet.setValueTextColor(Color.BLACK);

        BarData barData = new BarData(bardataSet);

        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(type));
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setGranularityEnabled(true);
        barChart.setFitBars(true);
        barChart.getDescription().setEnabled(false);
        barChart.setData(barData);
        return rootView;
    }
}