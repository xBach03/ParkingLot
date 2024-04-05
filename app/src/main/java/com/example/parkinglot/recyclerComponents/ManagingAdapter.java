package com.example.parkinglot.recyclerComponents;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.time.Duration;
import java.time.LocalDateTime;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkinglot.R;
import com.example.parkinglot.database.entities.HistoryManager;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

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

        // Set id
        holder.getHistoryId().setText("ID: " + Integer.valueOf(history.get(position).getHistoryId()).toString());
        // Set vehicle id
        holder.getVehicleId().setText(history.get(position).getVehicleId());

        // Get LocalDateTime
        LocalDateTime startTime = history.get(position).getStartTime();
        LocalDateTime endTime = history.get(position).getEndTime();
        // Reformat time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy HH:mm", Locale.ENGLISH);
        String formattedStartTime = startTime.format(formatter);
        String formattedEndTime = endTime.format(formatter);
        // Set start and end time for recycler item
        holder.getHistoryStart().setText("Start: " + formattedStartTime);
        holder.getHistoryEnd().setText("End: " + formattedEndTime);

        // Calculate total time
        Duration duration = Duration.between(startTime, endTime);
        long totalHours = duration.toHours();
        long totalMinutes = duration.toMinutes() % 60;

        // Format total hours and minutes
        String formattedTime = String.format(Locale.ENGLISH, "%02d:%02d", totalHours, totalMinutes);


        holder.getTotalTime().setText("Total time: " + formattedTime);
        holder.getMoneyPaid().setText(Double.valueOf(history.get(position).getMoneyPaid()).toString());
    }

    @Override
    public int getItemCount() {
        return history.size();
    }
}
