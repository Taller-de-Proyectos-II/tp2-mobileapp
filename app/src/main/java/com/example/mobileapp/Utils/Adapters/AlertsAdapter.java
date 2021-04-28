package com.example.mobileapp.Utils.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.Model.Alert;
import com.example.mobileapp.R;

import java.util.ArrayList;

public class AlertsAdapter extends RecyclerView.Adapter<AlertsAdapter.AlertAdapterVH> {

    ArrayList<Alert> alertList;
    private Context context;
    private ClickedItem clickedItem;

    public AlertsAdapter(){}

    public void setData(ArrayList<Alert> alerts){
        this.alertList = alerts;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public AlertAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new AlertsAdapter.AlertAdapterVH(LayoutInflater.from(context).inflate(R.layout.row_alerts, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlertAdapterVH holder, int position) {
        Alert alert = alertList.get(position);
        String date = alert.getDate();

        holder.alertDate.setText(date);

    }

    @Override
    public int getItemCount() {
        return alertList.size();
    }

    public interface ClickedItem{
        public void ClickedAlert(Alert alert);
    }

    public class AlertAdapterVH extends RecyclerView.ViewHolder {
        TextView alertDate;
        public AlertAdapterVH(@NonNull View itemView) {
            super(itemView);
            alertDate = itemView.findViewById(R.id.tvAlertDate);
        }
    }
}
