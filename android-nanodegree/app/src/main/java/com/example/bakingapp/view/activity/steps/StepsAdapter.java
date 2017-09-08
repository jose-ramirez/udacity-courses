package com.example.bakingapp.view.activity.steps;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bakingapp.R;
import com.example.bakingapp.view.activity.ListItemClickListener;
import com.example.bakingapp.model.Step;

import java.util.List;

/**
 * Created by jose on 08/06/17.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepViewHolder>{

    private List<Step> steps;

    private ListItemClickListener lstnr;

    public StepsAdapter(List<Step> steps, ListItemClickListener lstnr){
        this.steps = steps;
        this.lstnr = lstnr;
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View stepView = inflater.inflate(R.layout.item, parent, false);
        return new StepViewHolder(stepView, this.lstnr);
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        holder.bind(this.steps.get(position));
    }

    @Override
    public int getItemCount() {
        if(this.steps != null){
            return this.steps.size();
        }else{
            return 0;
        }
    }
}
