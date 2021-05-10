package com.example.mobileapp.Utils.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.Model.StudiesDTO;
import com.example.mobileapp.Model.WorkExperienceDTO;
import com.example.mobileapp.R;

import java.util.ArrayList;

public class StudiesAdapter extends RecyclerView.Adapter<StudiesAdapter.StudyAdapterVH> {

    ArrayList<StudiesDTO> studies;
    private Context context;

    public StudiesAdapter(){};

    public void setData(ArrayList<StudiesDTO> estudios){
        this.studies = estudios;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudiesAdapter.StudyAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new StudiesAdapter.StudyAdapterVH(LayoutInflater.from(context).inflate(R.layout.row_studies, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StudiesAdapter.StudyAdapterVH holder, int position) {
        StudiesDTO studiesDTO = studies.get(position);
        String disciplina = studiesDTO.getAcademicDiscipline();
        String titulo = studiesDTO.getTitle();
        String lugar = studiesDTO.getStudyCenter();
        String desde = "Desde: " + studiesDTO.getStartDate();
        String hasta = "Hasta: " + studiesDTO.getEndDate();

        holder.tvStudyLabel.setText(titulo + " en " + disciplina + " en " + lugar);
        holder.tvDate1.setText(desde);
        holder.tvDate2.setText(hasta);

    }

    @Override
    public int getItemCount() {
        return studies.size();
    }

    public class StudyAdapterVH extends RecyclerView.ViewHolder {
        TextView tvStudyLabel;
        TextView tvDate1;
        TextView tvDate2;
        public StudyAdapterVH(@NonNull View itemView) {
            super(itemView);
            tvStudyLabel = itemView.findViewById(R.id.tvStudyLabel);
            tvDate1 = itemView.findViewById(R.id.tvStudyDate);
            tvDate2 = itemView.findViewById(R.id.tvStudyDate2);
        }
    }
}
