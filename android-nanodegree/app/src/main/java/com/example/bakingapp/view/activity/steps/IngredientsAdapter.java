package com.example.bakingapp.view.activity.steps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bakingapp.R;

import java.util.List;

/**
 * Created by jose on 12/09/17.
 */

public class IngredientsAdapter extends BaseAdapter{

    private List<String> ingredients;
    private Context context;

    public IngredientsAdapter(Context context, List<String> ingredients){
        this.context = context;
        this.ingredients = ingredients;
    }

    @Override
    public int getCount() {
        return this.ingredients.size();
    }

    @Override
    public Object getItem(int position) {
        return this.ingredients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inf.inflate(R.layout.item_ingredient, parent, false);

        TextView ingredientTextView = (TextView) view.findViewById(R.id.text1);
        ingredientTextView.setText(this.ingredients.get(position));

        return view;
    }
}
