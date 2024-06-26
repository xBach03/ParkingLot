package com.example.parkinglot.fragments;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.parkinglot.R;
import com.example.parkinglot.activities.TransactionActivity;
import com.example.parkinglot.database.DatabaseHelper;
import com.example.parkinglot.database.daos.HistoryManagerDao;
import com.example.parkinglot.database.daos.PaymentDao;
import com.example.parkinglot.database.AuthenticationManager;
import com.example.parkinglot.database.entities.User;
import com.example.parkinglot.recyclerComponents.ManagingAdapter;

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
    private HistoryManagerDao historyDao;
    private ImageButton transaction, history, deposit;
    private ManagingAdapter managingAdapter;

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

        db = DatabaseHelper.getInstance(requireContext()).getWritableDatabase();

        // Get current user to setting up card information
        AuthenticationManager userManager = AuthenticationManager.getInstance(requireContext());
        User current = userManager.getCurrentUser();

        // Set debitOwner
        TextView debitOwner = rootView.findViewById(R.id.debitOwner);
        debitOwner.setText(current.getUserName().toUpperCase());

        // Set debitId
        TextView debitId = rootView.findViewById(R.id.debitId);
        PaymentDao paymentDao = new PaymentDao(db);

        StringBuilder paymentId = new StringBuilder();
        String currentPaymentId = paymentDao.getCurrentPayment(current).getId().replaceAll("\\s+", "");
        int s = 0;
        for(int i = 0; i < 16; i++) {
            paymentId.append(currentPaymentId.charAt(s));
            s++;
            if ((i + 1) % 4 == 0 && i != 15) {
                paymentId.append("   ");
            }
        }
        debitId.setText(paymentId.toString());

        // Set balance
        TextView balance = rootView.findViewById(R.id.balance);
        balance.setText(paymentDao.getCurrentPayment(current).getBalance().toString() + "$");

        // Set onclick for history image button
        history = rootView.findViewById(R.id.transHistory);
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

        transaction = rootView.findViewById(R.id.transaction);
        transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(requireContext(), TransactionActivity.class);
                startActivity(i);
            }
        });

        historyDao = new HistoryManagerDao(db);
        managingAdapter = new ManagingAdapter(historyDao.getAll(userManager.getCurrentUser()));


        return rootView;
    }
}