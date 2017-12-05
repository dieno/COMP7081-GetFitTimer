package com.example.ryan.getfittimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ryan.getfittimer.Database.DatabaseController;
import com.example.ryan.getfittimer.Notifications.NotificationController;
import com.example.ryan.getfittimer.Timer.Timer;
import com.example.ryan.getfittimer.Timer.TimerController;

public class TimerActivity extends AppCompatActivity {

    TimerController tc;
    DatabaseController dc;
    NotificationController nc;

    public Timer currentTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        nc = new NotificationController(this);
        dc = new DatabaseController(this);

        TextView textViewTimer = (TextView) findViewById(R.id.textViewTimer);
        TextView textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        tc = new TimerController(this, textViewTimer, textViewStatus);

        int warmup = Integer.parseInt(getIntent().getStringExtra("WARMUP"));
        int interval = Integer.parseInt(getIntent().getStringExtra("INTERVAL"));
        int rest = Integer.parseInt(getIntent().getStringExtra("REST"));
        int rounds = Integer.parseInt(getIntent().getStringExtra("ROUNDS"));
        int cooldown = Integer.parseInt(getIntent().getStringExtra("COOLDOWN"));
        String name = getIntent().getStringExtra("NAME");
        boolean newWorkout = getIntent().getBooleanExtra("NEW_WORKOUT", true);

        Timer newTimer = tc.createTimer(warmup, interval, rest, rounds, cooldown, name);
        currentTimer = newTimer;

        if(newWorkout) {
            dc.addWorkout(newTimer);
        }

        tc.startTimer();
    }


    public void pauseOrResumeTimer(View view) {
        if(!tc.isFinished) {
            Button pauseButton = (Button) view;

            if(!tc.isPaused) {
                pauseButton.setText("Resume");
                tc.pauseTimer();
            } else {
                pauseButton.setText("Pause");
                tc.resumeTimer();
            }
        }
    }

    public void stopTimer(View view) {
        dc.AddProgress(tc.totalSeconds);
        tc.StopTimer();
        Intent intent = new Intent(this, TimerListActivity.class);
        startActivity(intent);
    }

}
