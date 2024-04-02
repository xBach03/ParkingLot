package com.example.parkinglot.recyclerComponents;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkinglot.R;
import com.example.parkinglot.database.entities.HistoryManager;

import java.util.List;

public class ManagingAdapter extends RecyclerView.Adapter<ManagingAdapter.HistoryHolder> {

    private List<HistoryManager> history;
    public class HistoryHolder extends RecyclerView.ViewHolder {
        private TextView historyId, vehicleId, historyStart, historyEnd, totalTime, moneyPaid;
        public HistoryHolder(@NonNull View itemView) {
            super(itemView);
            historyId = itemView.findViewById(R.id.historyId);
            vehicleId = itemView.findViewById(R.id.vehicleId);
            historyStart = itemView.findViewById(R.id.historyStart);
            historyEnd = itemView.findViewById(R.id.historyEnd);
            totalTime = itemView.findViewById(R.id.totalTime);
            moneyPaid = itemView.findViewById(R.id.moneyPaid);
        }

        public TextView getHistoryId() {
            return historyId;
        }

        public TextView getVehicleId() {
            return vehicleId;
        }

        public TextView getHistoryStart() {
            return historyStart;
        }

        public TextView getHistoryEnd() {
            return historyEnd;
        }

        public TextView getTotalTime() {
            return totalTime;
        }

        public TextView getMoneyPaid() {
            return moneyPaid;
        }

        public void setHistoryId(TextView historyId) {
            this.historyId = historyId;
        }

        public void setVehicleId(TextView vehicleId) {
            this.vehicleId = vehicleId;
        }

        public void setHistoryStart(TextView historyStart) {
            this.historyStart = historyStart;
        }

        public void setHistoryEnd(TextView historyEnd) {
            this.historyEnd = historyEnd;
        }

        public void setTotalTime(TextView totalTime) {
            this.totalTime = totalTime;
        }

        public void setMoneyPaid(TextView moneyPaid) {
            this.moneyPaid = moneyPaid;
        }
    }

    public ManagingAdapter(List<HistoryManager> history) {
        this.history = history;
    }
    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.management_recycler_item, parent, false);
        return new HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {
        holder.getHistoryId().setText(history.get(position).getHistoryId());
        holder.getVehicleId().setText(history.get(position).getVehicleId());
        holder.getHistoryStart().setText(history.get(position).getStartTime().toString());
        holder.getHistoryEnd().setText(history.get(position).getEndTime().toString());
        holder.getTotalTime().setText(Integer.valueOf(history.get(position).getEndTime().getHour() - history.get(position).getStartTime().getHour()).toString());
        holder.getMoneyPaid().setText(Double.valueOf(history.get(position).getMoneyPaid()).toString());
    }

    @Override
    public int getItemCount() {
        return history.size();
    }
}
