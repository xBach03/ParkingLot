package com.example.parkinglot.fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parkinglot.R;
import com.example.parkinglot.database.DatabaseHelper;
import com.example.parkinglot.database.daos.HistoryManagerDao;
import com.example.parkinglot.database.AuthenticationManager;
import com.example.parkinglot.recyclerComponents.ManagingAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManagerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SQLiteDatabase db;
    private HistoryManagerDao historyDao;
    private ManagingAdapter managingAdapter;

    public ManagerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ManagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ManagerFragment newInstance(String param1, String param2) {
        ManagerFragment fragment = new ManagerFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_manager, container, false);

        AuthenticationManager userManager = AuthenticationManager.getInstance(requireContext());
        db = DatabaseHelper.getInstance(requireContext()).getReadableDatabase();
        historyDao = new HistoryManagerDao(db);
        managingAdapter = new ManagingAdapter(historyDao.getAll(userManager.getCurrentUser()));

        // Get recycler view
        RecyclerView recyclerView = rootView.findViewById(R.id.managerRecycler);
        recyclerView.setAdapter(managingAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        // Set linear layout for items in recycler view
        recyclerView.setLayoutManager(layoutManager);


        return rootView;
    }
}