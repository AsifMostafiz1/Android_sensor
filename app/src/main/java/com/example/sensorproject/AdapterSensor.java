package com.example.sensorproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterSensor extends RecyclerView.Adapter<AdapterSensor.ViewHolder>{

    private List<Sensor> sensorList;

    public AdapterSensor() {
    }

    public AdapterSensor( List<Sensor> expenseList) {

        this.sensorList = expenseList;
    }

    @NonNull
    @Override
    public AdapterSensor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sensor_value,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSensor.ViewHolder holder, int position) {

        Sensor currentSensor = sensorList.get(position);

        holder.X.setText("ValueX: "+currentSensor.getX());
        holder.Y.setText("ValueY: "+currentSensor.getY());
        holder.Z.setText("ValueZ: "+currentSensor.getZ());
        holder.date.setText("Date: "+currentSensor.getDate());

    }

    @Override
    public int getItemCount() {
        return sensorList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView X,Y,Z,date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            X = itemView.findViewById(R.id.itemValueXid);
            Y = itemView.findViewById(R.id.itemValueYid);
            Z = itemView.findViewById(R.id.itemValueZid);

            date = itemView.findViewById(R.id.itemDateId);
        }
    }
}
