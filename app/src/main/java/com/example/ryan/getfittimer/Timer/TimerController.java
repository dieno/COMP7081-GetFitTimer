package com.example.ryan.getfittimer.Timer;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.example.ryan.getfittimer.Audio.AudioController;
import com.example.ryan.getfittimer.Database.DatabaseController;
import com.example.ryan.getfittimer.Notifications.NotificationController;
import com.example.ryan.getfittimer.R;

import org.w3c.dom.Text;

/**
 * Created by Ryan on 11/28/2017.
 */

public class TimerController {
    Timer currentTimer;

    TextView timerTextView;
    TextView statusTextView;

    int currentRound;

    CountDownTimerPausable warmupTimer;
    CountDownTimerPausable intervalTimer;
    CountDownTimerPausable restTimer;
    CountDownTimerPausable cooldownTimer;

    CountDownTimerPausable currentCountDownTimer;

    DatabaseController dbController;

    public boolean isPaused;

    public boolean isFinished;

    public int totalSeconds;

    //final MediaPlayer mp;

    AudioController ac;

    NotificationController nc;

    public TimerController(Context context, TextView timer, TextView status) {
        dbController = new DatabaseController(context);
        timerTextView = timer;
        statusTextView = status;
        currentRound = 0;
        totalSeconds = 0;
        isPaused = true;
        isFinished = false;
        //mp = MediaPlayer.create(context, R.raw.bell);
        ac = new AudioController(context);
        nc = new NotificationController(context);
    }


    public Timer createTimer(int warmup, int interval, int rest, int rounds, int cooldown, String name) {
        Timer timer = new Timer();

        timer.warmup = warmup;
        timer.interval = interval;
        timer.restPeriod = rest;
        timer.rounds = rounds;
        timer.cooldown = cooldown;
        timer.name = name;


        currentTimer = timer;
        return timer;
    }


    public void startTimer() {

        final long warmupMillis = (long) currentTimer.warmup * 1000;
        final long intervalMillis = (long) currentTimer.interval * 1000;
        final long restMillis = (long) currentTimer.restPeriod * 1000;
        final long cooldownMillis = (long) currentTimer.cooldown * 1000;

        cooldownTimer = new CountDownTimerPausable(cooldownMillis, 1000) {
            public void onTick(long millisUntilFinished) {
                statusTextView.setText("Cooldown");

                String timeString = Long.toString(millisUntilFinished / 1000);
                timerTextView.setText(timeString);
                totalSeconds++;
            }

            public void onFinish() {
                timerTextView.setText("0");
                statusTextView.setText("Done!");
                isFinished = true;
                ac.PlaySound();
            }
        };


        restTimer = new CountDownTimerPausable(restMillis, 1000) {
            public void onTick(long millisUntilFinished) {
                statusTextView.setText("Rest");

                String timeString = Long.toString(millisUntilFinished / 1000);
                timerTextView.setText(timeString);
                totalSeconds++;
            }

            public void onFinish() {
                currentCountDownTimer = intervalTimer;
                intervalTimer.millisRemaining = intervalMillis;
                intervalTimer.millisInFuture = intervalMillis;
                intervalTimer.isPaused = true;
                ac.PlaySound();
                nc.sendNotification("Interval");
                intervalTimer.start();
            }
        };

        intervalTimer = new CountDownTimerPausable(intervalMillis, 1000) {
            public void onTick(long millisUntilFinished) {
                statusTextView.setText("Interval");

                String timeString = Long.toString(millisUntilFinished / 1000);
                timerTextView.setText(timeString);
                totalSeconds++;
            }

            public void onFinish() {
                currentRound++;

                if(currentRound < currentTimer.rounds) {
                    currentCountDownTimer = restTimer;
                    restTimer.millisRemaining = restMillis;
                    restTimer.millisInFuture = restMillis;
                    restTimer.isPaused = true;
                    restTimer.start();
                    nc.sendNotification("Rest");
                    ac.PlaySound();
                } else {
                    currentCountDownTimer = cooldownTimer;
                    ac.PlaySound();
                    nc.sendNotification("Cooldown");
                    cooldownTimer.start();
                }
            }
        };


        warmupTimer = new CountDownTimerPausable(warmupMillis, 1000) {
            public void onTick(long millisUntilFinished) {
                statusTextView.setText("Warmup");

                String timeString = Long.toString(millisUntilFinished / 1000);
                timerTextView.setText(timeString);
                totalSeconds++;
            }

            public void onFinish() {
                currentCountDownTimer = intervalTimer;
                ac.PlaySound();
                nc.sendNotification("Interval");
                intervalTimer.start();
            }
        };


        currentCountDownTimer = warmupTimer;
        isPaused = false;
        nc.sendNotification("Warmup");
        ac.PlaySound();
        warmupTimer.start();
    }

    public void pauseTimer() {
        isPaused = true;
        currentCountDownTimer.pause();
    }

    public void resumeTimer() {
        isPaused = false;
        currentCountDownTimer.start();
    }

    public void StopTimer() {
        currentCountDownTimer.cancel();
    }
}
