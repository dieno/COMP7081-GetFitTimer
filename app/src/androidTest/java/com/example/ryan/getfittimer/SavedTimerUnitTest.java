package com.example.ryan.getfittimer;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.ryan.getfittimer.Database.DatabaseController;
import com.example.ryan.getfittimer.Timer.Timer;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

/**
 * Created by Ryan on 12/5/2017.
 */

@RunWith(AndroidJUnit4.class)
public class SavedTimerUnitTest {
    @Test
    public void testSavedWorkout() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        String name = "Workout2";
        String warmup = "2";
        String interval = "2";
        String rest = "2";
        String rounds = "2";
        String cooldown = "2";

        Timer newTimer = new Timer();
        newTimer.name = name;
        newTimer.warmup = Integer.parseInt(warmup);
        newTimer.interval = Integer.parseInt(interval);
        newTimer.restPeriod = Integer.parseInt(rest);
        newTimer.rounds = Integer.parseInt(rounds);
        newTimer.cooldown = Integer.parseInt(cooldown);

        DatabaseController dc = new DatabaseController(appContext);

        dc.addWorkout(newTimer);

        Timer testTimer = dc.GetWorkout(name);

        assertEquals(testTimer.name, name);
        assertEquals(testTimer.warmup, Integer.parseInt(warmup));
        assertEquals(testTimer.interval, Integer.parseInt(interval));
        assertEquals(testTimer.restPeriod, Integer.parseInt(rest));
        assertEquals(testTimer.rounds,Integer.parseInt(rounds));
        assertEquals(testTimer.cooldown, Integer.parseInt(cooldown));
    }

}
