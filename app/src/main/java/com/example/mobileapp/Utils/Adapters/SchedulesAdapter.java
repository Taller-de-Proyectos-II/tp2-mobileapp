package com.example.mobileapp.Utils.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.Model.Schedule;
import com.example.mobileapp.R;

import java.util.List;

public class SchedulesAdapter extends RecyclerView.Adapter<SchedulesAdapter.ViewHolder> {


    List<Schedule> schedules;
    private Context context;
    private ClickedItem clickedItem;

    public SchedulesAdapter(ClickedItem clickedItem){
        this.clickedItem = clickedItem;
    }

    public void setData(List<Schedule> schedules){
        this.schedules = schedules;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SchedulesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new SchedulesAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_grid_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SchedulesAdapter.ViewHolder holder, int position) {

        Schedule schedule = schedules.get(position);


        String dia = "";
        String hora = "";


        switch (schedule.getDay()){
            case 1:
                dia = "Lunes";
                break;
            case 2:
                dia = "Martes";
                break;
            case 3:
                dia = "Miércoles";
                break;
            case 4:
                dia = "Jueves";
                break;
            case 5:
                dia = "Viernes";
                break;
            case 6:
                dia = "Sábado";
                break;
            case 7:
                dia = "Domingo";
                break;
        }
        switch (schedule.getHour()){
            case 8:
                hora = "08:00";
                break;
            case 9:
                hora = "09:00";
                break;
            case 10:
                hora = "10:00";
                break;
            case 11:
                hora = "11:00";
                break;
            case 12:
                hora = "12:00";
                break;
            case 13:
                hora = "13:00";
                break;
            case 14:
                hora = "14:00";
                break;
            case 15:
                hora = "15:00";
                break;
            case 16:
                hora = "16:00";
                break;
            case 17:
                hora = "17:00";
                break;
            case 18:
                hora = "18:00";
                break;
            case 19:
                hora = "19:00";
                break;
            case 20:
                hora = "20:00";
                break;
            case 21:
                hora = "21:00";
                break;
            case 22:
                hora = "22:00";
                break;

        }

        holder.tvHour.setText(hora);
        holder.tvDay.setText(dia);

        holder.cvSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedItem.ClickedSche(schedule);
            }
        });
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public interface ClickedItem{
        public void ClickedSche(Schedule schedule);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvDay, tvHour, tvDate;
        CardView cvSchedule;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDaySchedule);
            tvHour = itemView.findViewById(R.id.tvHourSchedule);
            cvSchedule = itemView.findViewById(R.id.cvSchedule);
            tvDate = itemView.findViewById(R.id.tvDateSchedule);
        }
    }

}
