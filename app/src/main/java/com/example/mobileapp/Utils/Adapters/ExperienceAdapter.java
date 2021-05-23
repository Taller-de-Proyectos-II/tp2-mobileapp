package com.example.mobileapp.Utils.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.Model.DTO.WorkExperienceDTO;
import com.example.mobileapp.R;

import java.util.ArrayList;

public class ExperienceAdapter extends RecyclerView.Adapter<ExperienceAdapter.ExperienceAdapterVH> {

    ArrayList<WorkExperienceDTO> experiences;
    private Context context;

    public ExperienceAdapter(){};

    public void setData(ArrayList<WorkExperienceDTO> experiencias){
        this.experiences = experiencias;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExperienceAdapter.ExperienceAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ExperienceAdapter.ExperienceAdapterVH(LayoutInflater.from(context).inflate(R.layout.row_experiences, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExperienceAdapter.ExperienceAdapterVH holder, int position) {
        WorkExperienceDTO experienceDTO = experiences.get(position);
        String posicion = experienceDTO.getOccupation();
        String lugar = experienceDTO.getPlace();
        String desde = "Desde: " + experienceDTO.getStartDate();
        String hasta = "Hasta: " + experienceDTO.getEndDate();

        holder.tvExperienceLabel.setText(posicion + " en " + lugar);
        holder.tvDate1.setText(desde);
        holder.tvDate2.setText(hasta);

    }

    @Override
    public int getItemCount() {
        return experiences.size();
    }

    public class ExperienceAdapterVH extends RecyclerView.ViewHolder {
        TextView tvExperienceLabel;
        TextView tvDate1;
        TextView tvDate2;
        public ExperienceAdapterVH(@NonNull View itemView) {
            super(itemView);
            tvExperienceLabel = itemView.findViewById(R.id.tvExperienceLabel);
            tvDate1 = itemView.findViewById(R.id.tvExperienceDate);
            tvDate2 = itemView.findViewById(R.id.tvExperienceDate2);
        }
    }
}
