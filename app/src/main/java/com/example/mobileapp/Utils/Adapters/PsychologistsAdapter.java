package com.example.mobileapp.Utils.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.Model.Psychologist;
import com.example.mobileapp.R;
import com.example.mobileapp.Utils.Responses.PsychologistResponse;

import org.w3c.dom.Text;

import java.util.List;

public class PsychologistsAdapter extends RecyclerView.Adapter<PsychologistsAdapter.PsychologistAdapterVH> {

    private List<Psychologist> psychologists;
    private Context context;
    private ClickedItem clickedItem;

    public PsychologistsAdapter(ClickedItem clickedItem)
    {
        this.clickedItem = clickedItem;
    }

    public void setData(List<Psychologist> psychologists){
        this.psychologists = psychologists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PsychologistsAdapter.PsychologistAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new PsychologistsAdapter.PsychologistAdapterVH(LayoutInflater.from(context).inflate(R.layout.row_psychologist,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PsychologistsAdapter.PsychologistAdapterVH holder, int position) {
         Psychologist psychologist = psychologists.get(position);

         String nombre = psychologist.getNames() + " " + psychologist.getLastNames();

         String cpsp = psychologist.getCpsp();

         holder.nombre.setText(nombre);

         holder.imageMore.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 clickedItem.ClickedPsy(psychologist);
             }
         });
    }

    public interface ClickedItem{
        public void ClickedPsy(Psychologist psychologist);
    }

    @Override
    public int getItemCount() {
        if(psychologists.size() > 0 && psychologists != null)
            return psychologists.size();
        return 0;
    }

    public class PsychologistAdapterVH extends RecyclerView.ViewHolder {
        TextView nombre;
        ImageView imageMore;

        public PsychologistAdapterVH(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.tvNombrePsicologo);
            imageMore = itemView.findViewById(R.id.ivMore);
        }
    }
}
