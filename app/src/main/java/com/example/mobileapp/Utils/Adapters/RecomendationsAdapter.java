package com.example.mobileapp.Utils.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.Model.DTO.recomendationDTO;
import com.example.mobileapp.R;

import java.util.ArrayList;

public class RecomendationsAdapter extends RecyclerView.Adapter<RecomendationsAdapter.RecomendationsAdapterVH> {

    ArrayList<recomendationDTO> recomendations;
    private Context context;
    private ClickedItem clickedItem;

    public RecomendationsAdapter(ClickedItem clickedItem) {
        this.clickedItem = clickedItem;
    }

    public void setData(ArrayList<recomendationDTO> recomendations){
        this.recomendations = recomendations;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecomendationsAdapter.RecomendationsAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new RecomendationsAdapter.RecomendationsAdapterVH(LayoutInflater.from(context).inflate(R.layout.row_recomendations,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecomendationsAdapter.RecomendationsAdapterVH holder, int position) {
        recomendationDTO recomendationDTO = recomendations.get(position);
        int recNumber = position + 1;
        String nombre = "Recomendación " + (position + 1);
        String desc = recomendationDTO.getDescription();

        holder.recDesc.setText(desc);
        holder.recName.setText(nombre);

        holder.imageVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedItem.ClickedRecomendation(recomendationDTO, recNumber);
            }
        });


    }

    public interface ClickedItem{
        public void ClickedRecomendation(recomendationDTO recomendationDTO, int recNumber);
    }

    @Override
    public int getItemCount() {
        return recomendations.size();
    }

    public class RecomendationsAdapterVH extends RecyclerView.ViewHolder {
        TextView recName;
        TextView recDesc;
        ImageView imageVideo;

        public RecomendationsAdapterVH(@NonNull View itemView) {
            super(itemView);
            recName = itemView.findViewById(R.id.tvRecomendationName);
            recDesc = itemView.findViewById(R.id.tvRecomendationDescription);
            imageVideo = itemView.findViewById(R.id.ivRecVideo);

        }
    }
}
