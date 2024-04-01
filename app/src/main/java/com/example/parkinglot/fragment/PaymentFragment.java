package com.example.parkinglot.fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.parkinglot.R;
import com.example.parkinglot.database.DatabaseHelper;
import com.example.parkinglot.database.daos.PaymentDao;
import com.example.parkinglot.database.entities.AuthenticationManager;
import com.example.parkinglot.database.entities.HistoryManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public PaymentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PaymentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PaymentFragment newInstance(String param1, String param2) {
        PaymentFragment fragment = new PaymentFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_payment, container, false);
        // Find the TextView within the inflated layout
//        RecyclerView recyclerView = rootView.findViewById(R.id.parkingRecycler);

        dbHelper = DatabaseHelper.getInstance(requireContext());
        db = dbHelper.getWritableDatabase();

        // Get current user to setting up card information
        AuthenticationManager userManager = AuthenticationManager.getInstance(requireContext());

        // Set debitOwner
        TextView debitOwner = rootView.findViewById(R.id.debitOwner);
        debitOwner.setText(userManager.getCurrentUser().getUserName().toUpperCase());

        // Set debitId
        TextView debitId = rootView.findViewById(R.id.debitId);
        PaymentDao paymentDao = new PaymentDao(db);
        debitId.setText(paymentDao.getCurrentPayment(userManager.getCurrentUser()).getId());

        // Set onclick for history image button
        ImageButton history = rootView.findViewById(R.id.transHistory);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setReorderingAllowed(true);
                transaction.replace(R.id.fragment_container_view, ManagerFragment.class, null);
                transaction.commit();
            }
        });
        return rootView;
    }
}