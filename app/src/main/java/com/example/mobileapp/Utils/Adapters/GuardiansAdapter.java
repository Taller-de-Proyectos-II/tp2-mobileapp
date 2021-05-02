package com.example.mobileapp.Utils.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.Model.Guardian;
import com.example.mobileapp.R;

import java.util.List;

public class GuardiansAdapter extends RecyclerView.Adapter<GuardiansAdapter.GuardiansAdapterVH> {

    private List<Guardian> guardians;
    private Context context;
    private ClickedItem clickedItem;

    public GuardiansAdapter(ClickedItem clickedItem) { this.clickedItem = clickedItem; }

    public void setData(List<Guardian> guardians){
        this.guardians = guardians;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GuardiansAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new GuardiansAdapter.GuardiansAdapterVH(LayoutInflater.from(context).inflate(R.layout.row_guardian, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GuardiansAdapterVH holder, int position) {
        Guardian guardian = guardians.get(position);

        String nombre = guardian.getNames() + " " + guardian.getLastNames();

        holder.nombre.setText(nombre);

        holder.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedItem.ClickedGuard(guardian);
            }
        });

    }

    public interface ClickedItem{
        public void ClickedGuard(Guardian guardian);
    }

    @Override
    public int getItemCount() {
        return guardians.size();
    }

    public class GuardiansAdapterVH extends RecyclerView.ViewHolder {
        TextView nombre;
        ImageView ivMore;


        public GuardiansAdapterVH(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.tvGuardianName);
            ivMore = itemView.findViewById(R.id.ivGuardianMore);
        }
    }
}
