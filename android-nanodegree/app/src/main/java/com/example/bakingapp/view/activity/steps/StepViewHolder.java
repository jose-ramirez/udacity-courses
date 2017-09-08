package com.example.bakingapp.view.activity.steps;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.bakingapp.R;
import com.example.bakingapp.view.activity.ListItemClickListener;
import com.example.bakingapp.model.Step;

/**
 * Created by jose on 08/06/17.
 */

public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private Step step;

    private Context context;

    private ListItemClickListener lstnr;

    public StepViewHolder(View itemView, ListItemClickListener lstnr) {
        super(itemView);
        this.context = itemView.getContext();
        this.lstnr = lstnr;
        itemView.setOnClickListener(this);
    }

    public void bind(Step step){
        this.step = step;
        TextView tvDescription = (TextView) itemView.findViewById(R.id.tv_description);
        tvDescription.setText(step.getShortDescription());
    }

    @Override
    public void onClick(View v) {
        v.setTag(this.step);
        this.lstnr.onClick(v);
    }
}
