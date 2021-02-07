package com.example.jokeslib2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jokes);

        TextView jokeView = (TextView) findViewById(R.id.jokeTextView);

        if(getIntent().hasExtra("joke")){
            String joke = getIntent().getStringExtra("joke");
            jokeView.setText(joke);
        }else {
            jokeView.setText("No joke was found. :(");
        }
    }
}
