package com.example.mobileapp.Utils.Adapters;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.Model.Symptom;
import com.example.mobileapp.R;
import com.example.mobileapp.Utils.Responses.ManifestationsResponse;

import java.util.HashMap;
import java.util.List;

public class ManifestationsAdapter  extends RecyclerView.Adapter<ManifestationsAdapter.ManifestationsAdapterVH> {

    private ManifestationsResponse manifestationsResponse;
    HashMap<String, List<Symptom>> symptomList;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    private Context context;

    public ManifestationsAdapter(Context context, HashMap<String, List<Symptom>> symptomList) {
        this.symptomList = symptomList;
        this.context = context;
        for(int i = 0; i<symptomList.size();i++){
            expandState.append(i, false);
        }
    }

    public void setData(ManifestationsResponse manifestationsResponse){
        this.manifestationsResponse = manifestationsResponse;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ManifestationsAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ManifestationsAdapter.ManifestationsAdapterVH(LayoutInflater.from(context).inflate(R.layout.row_psychologist, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ManifestationsAdapterVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return manifestationsResponse.getManifestationsDTO().size() ;
    }

    public class ManifestationsAdapterVH extends RecyclerView.ViewHolder {

        TextView username;
        TextView prefix;
        ImageView imageMore;
        public ManifestationsAdapterVH(@NonNull View itemView) {
            super(itemView);
            prefix = itemView.findViewById(R.id.tvPrefix);
            imageMore = itemView.findViewById(R.id.ivMore);
        }
    }
}
