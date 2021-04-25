package com.example.mobileapp.Utils.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.Model.Test;
import com.example.mobileapp.R;

import java.util.ArrayList;

public class TestsAdapter extends RecyclerView.Adapter<TestsAdapter.TestAdapterVH> {

    ArrayList<Test> testList;
    private Context context;
    private ClickedItem clickedItem;

    public TestsAdapter(ClickedItem clickedItem){this.clickedItem = clickedItem;}

    public void setData(ArrayList<Test> tests){
        this.testList = tests;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TestsAdapter.TestAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new TestsAdapter.TestAdapterVH(LayoutInflater.from(context).inflate(R.layout.row_tests, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TestsAdapter.TestAdapterVH holder, int position) {
        Test test = testList.get(position);

        String nombre = test.getTestType();
        String desc  = "Test de " + nombre;

        holder.testName.setText(nombre);
        holder.testDesc.setText(desc);
        holder.ivTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedItem.ClickedTest(test);
            }
        });

    }

    public interface ClickedItem{
        public void ClickedTest(Test test);
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    public class TestAdapterVH extends RecyclerView.ViewHolder {
        TextView testName;
        TextView testDesc;
        ImageView ivTest;

        public TestAdapterVH(@NonNull View itemView) {
            super(itemView);
            testName = itemView.findViewById(R.id.tvTestName);
            testDesc = itemView.findViewById(R.id.tvTestDescription);
            ivTest = itemView.findViewById(R.id.ivTest);
        }
    }
}
