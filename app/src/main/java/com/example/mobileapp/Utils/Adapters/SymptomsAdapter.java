package com.example.mobileapp.Utils.Adapters;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.Model.Symptom;
import com.example.mobileapp.R;

import java.util.ArrayList;

public class SymptomsAdapter extends RecyclerView.Adapter<SymptomsAdapter.SymptomsAdapterVH> {


    ArrayList<Symptom> symptomList;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    private Context context;
    private ClickedItem clickedItem;

    public SymptomsAdapter(ClickedItem clickedItem) {
        this.clickedItem = clickedItem;
    }

    public void setData(ArrayList<Symptom> symptoms){
        this.symptomList = symptoms;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SymptomsAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new SymptomsAdapter.SymptomsAdapterVH(LayoutInflater.from(context).inflate(R.layout.row_symptoms, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SymptomsAdapterVH holder, int position) {
        Symptom symptom = symptomList.get(position);

        String nombre = symptom.getName();
        String desc = symptom.getDescription();

        holder.symptomName.setText(nombre);
        holder.symptomDesc.setText(desc);

        holder.ivSymptom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedItem.ClickedSymptom(symptom);
            }
        });


    }

    public interface ClickedItem{
        public void ClickedSymptom(Symptom symptom);
    }

    @Override
    public int getItemCount() {
        return symptomList.size();
    }

    public class SymptomsAdapterVH extends RecyclerView.ViewHolder {

        TextView symptomName;
        TextView symptomDesc;
        ImageView ivSymptom;

        public SymptomsAdapterVH(@NonNull View itemView) {
            super(itemView);
            symptomDesc = itemView.findViewById(R.id.tvSymptomDescription);
            symptomName = itemView.findViewById(R.id.tvSymptomName);
            ivSymptom = itemView.findViewById(R.id.ivSymptom);
        }
    }
}
