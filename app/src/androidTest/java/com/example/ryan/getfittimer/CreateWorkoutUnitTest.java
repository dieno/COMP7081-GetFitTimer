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
public class CreateWorkoutUnitTest {
    @Test
    public void testCreateWorkout() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        String name = "Workout1";
        String warmup = "1";
        String interval = "2";
        String rest = "3";
        String rounds = "4";
        String cooldown = "5";

        DatabaseController dc = new DatabaseController(appContext);

        Timer newTimer = dc.GetWorkout(1);

        assertEquals(newTimer.name, name);
        assertEquals(newTimer.warmup, Integer.parseInt(warmup));
        assertEquals(newTimer.interval, Integer.parseInt(interval));
        assertEquals(newTimer.restPeriod, Integer.parseInt(rest));
        assertEquals(newTimer.rounds,Integer.parseInt(rounds));
        assertEquals(newTimer.cooldown, Integer.parseInt(cooldown));
    }

}
