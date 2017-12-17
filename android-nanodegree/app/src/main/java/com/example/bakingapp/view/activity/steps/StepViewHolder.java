package com.example.bakingapp.view.activity.steps;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Step;
import com.example.bakingapp.thumbnail.GlideApp;
import com.example.bakingapp.view.activity.ListItemClickListener;

/**
 * Created by jose on 08/06/17.
 */

public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private View itemView;

    private Step step;

    private Context context;

    private ImageView stepView;

    private ListItemClickListener lstnr;

    public StepViewHolder(View itemView, ListItemClickListener lstnr) {
        super(itemView);
        this.itemView = itemView;
        this.context = itemView.getContext();
        this.lstnr = lstnr;
        this.stepView = (ImageView) this.itemView.findViewById(R.id.step_image);
        ImageButton playStepButton = (ImageButton) itemView.findViewById(R.id.play_step_button);
        playStepButton.setOnClickListener(this);
    }

    public void bind(Step step){
        this.step = step;
        TextView tvDescription = (TextView) itemView.findViewById(R.id.tv_description);
        tvDescription.setText(step.getShortDescription());
        if(!"".equals(this.step.getThumbnailURL())){
            GlideApp
                .with(itemView)
                .load(this.step.getThumbnailURL())
                .into(stepView);
        }else{
            if(this.step.getVideoURL() != null){
                GlideApp
                    .with(itemView)
                    .load(this.step.getVideoURL())
                    .into(stepView);
            }
        }
    }

    @Override
    public void onClick(View v) {
        v.setTag(this.step);
        this.lstnr.onClick(v);
    }
}
