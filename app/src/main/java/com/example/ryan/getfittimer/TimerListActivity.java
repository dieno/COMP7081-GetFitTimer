package com.example.ryan.getfittimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.ryan.getfittimer.Database.DatabaseController;
import com.example.ryan.getfittimer.Timer.Timer;

import java.util.ArrayList;

public class TimerListActivity extends AppCompatActivity {

    DatabaseController dc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_list);

        dc = new DatabaseController(this);

        // Setup the data source
        ArrayList<Timer> workoutsArrayList = dc.GetAllWorkouts();

        // instantiate the custom list adapter
        TimerAdapter adapter = new TimerAdapter(this, workoutsArrayList);

        // get the ListView and attach the adapter
        ListView workoutsListView  = (ListView) findViewById(R.id.timerListView);
        workoutsListView.setAdapter(adapter);

        workoutsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
                //Class targetActivity = getTargetActivityForPosition(position);

                Intent intent = new Intent(TimerListActivity.this, TimerActivity.class);

                Timer myTimer = dc.GetWorkout(position + 1);

                String warmup = Integer.toString(myTimer.warmup);
                intent.putExtra("WARMUP", warmup);

                String interval = Integer.toString(myTimer.interval);
                intent.putExtra("INTERVAL", interval);

                String rest = Integer.toString(myTimer.restPeriod);
                intent.putExtra("REST", rest);

                String rounds = Integer.toString(myTimer.rounds);
                intent.putExtra("ROUNDS", rounds);

                String cooldown = Integer.toString(myTimer.cooldown);
                intent.putExtra("COOLDOWN", cooldown);

                String name = myTimer.name;
                intent.putExtra("NAME", name);

                intent.putExtra("NEW_WORKOUT", false);

                TimerListActivity.this.startActivity(new Intent(intent));
            }
        });

    }

    public void openCreateTimer(View view) {
        Intent intent = new Intent(this, CreateTimerActivity.class);
        startActivity(intent);
    }


    public void openTimer(View view) {
        Intent intent = new Intent(this, TimerActivity.class);

        EditText editTextWarmup = (EditText) findViewById(R.id.editTextWarmup);
        String warmup = editTextWarmup.getText().toString();
        intent.putExtra("WARMUP", warmup);

        EditText editTextInterval = (EditText) findViewById(R.id.editTextInterval);
        String interval = editTextInterval.getText().toString();
        intent.putExtra("INTERVAL", interval);

        EditText editTextRest = (EditText) findViewById(R.id.editTextRest);
        String rest = editTextRest.getText().toString();
        intent.putExtra("REST", rest);

        EditText editTextRounds = (EditText) findViewById(R.id.editTextRounds);
        String rounds = editTextRounds.getText().toString();
        intent.putExtra("ROUNDS", rounds);

        EditText editTextCooldown = (EditText) findViewById(R.id.editTextCooldown);
        String cooldown = editTextCooldown.getText().toString();
        intent.putExtra("COOLDOWN", cooldown);

        EditText editTextName = (EditText) findViewById(R.id.editTextName);
        String name = editTextName.getText().toString();
        intent.putExtra("NAME", name);

        startActivity(intent);
    }

    public void openProgress(View view) {
        Intent intent = new Intent(this, ProgressActivity.class);

        String totalSeconds = dc.GetProgress();

        intent.putExtra("SECONDS", totalSeconds);

        startActivity(intent);
    }

}
