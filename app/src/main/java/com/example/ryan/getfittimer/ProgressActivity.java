package com.example.ryan.getfittimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ProgressActivity extends AppCompatActivity {

    public int seconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        seconds = 0;
        String totalSeconds = getIntent().getStringExtra("SECONDS");
        seconds = Integer.parseInt(totalSeconds);

        TextView textview = (TextView) findViewById(R.id.progressSeconds);
        textview.setText(totalSeconds + "s");
    }

    public void openTimerList(View view) {
        Intent intent = new Intent(this, TimerListActivity.class);
        startActivity(intent);
    }

}
