package com.example.parkinglot.recyclerComponents;



import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkinglot.R;
import com.example.parkinglot.database.entities.ParkingSlot;

import java.util.List;

public class ParkingAdapter extends RecyclerView.Adapter<ParkingAdapter.ParkingHolder> {

    private List<ParkingSlot> parkingspaces;
    private int selectedPos = -1;

    public class ParkingHolder extends RecyclerView.ViewHolder {
        private ImageView ImageView;
        private TextView Information;
        public ParkingHolder(@NonNull View itemView) {
            super(itemView);
            ImageView = itemView.findViewById(R.id.recyclerVector);
            Information = itemView.findViewById(R.id.recyclerText);
            Information.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos == RecyclerView.NO_POSITION) return;
                    notifyItemChanged(selectedPos);
                    selectedPos = getAdapterPosition();
                    notifyItemChanged(selectedPos);
                }
            });
        }

        public android.widget.ImageView getImageView() {
            return ImageView;
        }

        public TextView getInformation() {
            return Information;
        }

        public void setImageView(android.widget.ImageView imageView) {
            ImageView = imageView;
        }

        public void setInformation(TextView information) {
            Information = information;
        }
    }
    public ParkingAdapter(List<ParkingSlot> listSpaces) {
        parkingspaces = listSpaces;
    }
    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ParkingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parking_recycler_item, parent, false);
        return new ParkingHolder(view);
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ParkingHolder holder, int position) {
        String type = parkingspaces.get(position).getVehicleType();
        String area = parkingspaces.get(position).getArea();
        String spot = parkingspaces.get(position).getParkingSpot();
        String displayer = "Area: " + area + " - Spot: " + spot;
        holder.getInformation().setText(" " + displayer);
        Drawable drawable;
        switch (type) {
            case "Car":
                drawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.car);
                break;
            case "Motorcycle":
                drawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.motorcycle);
                break;
            case "Bike":
                drawable = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.bike);
                break;
            default:
                drawable = null;
                break;
        }
        if(drawable != null) {
            holder.getImageView().setImageDrawable(drawable);
        }
        if(selectedPos == position) {
            holder.itemView.setBackgroundColor(holder.Information.getContext().getColor(R.color.selectedPos));
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }
    public int getSelectedPosId() {
        return parkingspaces.get(selectedPos).getParkingId();
    }
    @Override
    public int getItemCount() {
        return parkingspaces.size();
    }
    public void updateDataSet(List<ParkingSlot> newParkingspaces) {
        parkingspaces = newParkingspaces;
    }
}
