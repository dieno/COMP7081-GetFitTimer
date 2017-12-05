package com.example.ryan.getfittimer.Audio;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.CountDownTimer;

import com.example.ryan.getfittimer.R;

/**
 * Created by Ryan on 12/4/2017.
 */

public class AudioController {

    public final MediaPlayer mp;
    SoundPool sp;

    int soundId;


    public AudioController(Context context) {
        mp = MediaPlayer.create(context, R.raw.music);


        sp = new SoundPool.Builder()
                .setMaxStreams(10)
                .build();

        soundId = sp.load(context, R.raw.bell, 1);

        //sp.play(soundId, 1, 1, 0, 0, 1);

        //int musicId = sp.load(context, R.raw.music, 1);
        //sp.play(musicId, 1, 1, 0, -1, 1);


        mp.setVolume(0.5f, 0.5f);
        mp.start();
    }


    public void PlaySound() {
        //mp.seekTo(0);
        //mp.start();
        mp.setVolume(0.1f, 0.1f);
        sp.play(soundId, 1, 1, 0, 0, 1);

        CountDownTimer soundTimer = new CountDownTimer(750, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                mp.setVolume(0.5f, 0.5f);
            }
        };

        soundTimer.start();


    }

}
