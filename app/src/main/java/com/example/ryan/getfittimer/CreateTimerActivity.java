package com.example.ryan.getfittimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateTimerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_timer);
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

        intent.putExtra("NEW_WORKOUT", true);

        startActivity(intent);
    }
}
