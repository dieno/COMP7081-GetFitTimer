package com.example.ryan.getfittimer;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;

import com.example.ryan.getfittimer.Database.DatabaseController;
import com.example.ryan.getfittimer.Timer.Timer;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.Collection;
import java.util.Iterator;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.junit.Assert.assertEquals;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class aCreateWorkoutTest {
    @Rule
    public ActivityTestRule<TimerListActivity> mActivityRule =
            new ActivityTestRule<>(TimerListActivity.class);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.ryan.getfittimer", appContext.getPackageName());
    }

    @Test
    public void ensureCreateWorkoutWorks() {

        Context appContext = InstrumentationRegistry.getTargetContext();

        onView(withId(R.id.createWorkoutButton)).perform(click());

        String name = "Workout1";
        String warmup = "1";
        String interval = "2";
        String rest = "3";
        String rounds = "4";
        String cooldown = "5";


        onView(withId(R.id.editTextName)).perform(replaceText(name));
        onView(withId(R.id.editTextWarmup)).perform(replaceText(warmup));
        onView(withId(R.id.editTextInterval)).perform(replaceText(interval));
        onView(withId(R.id.editTextRest)).perform(replaceText(rest));
        onView(withId(R.id.editTextRounds)).perform(replaceText(rounds));
        onView(withId(R.id.editTextCooldown)).perform(replaceText(cooldown));

        onView(withId(R.id.beginWorkoutButton)).perform(click());

        DatabaseController dc = new DatabaseController(appContext);

        Timer newTimer = dc.GetWorkout(1);

        assertEquals(newTimer.name, name);
        assertEquals(newTimer.warmup, Integer.parseInt(warmup));
        assertEquals(newTimer.interval, Integer.parseInt(interval));
        assertEquals(newTimer.restPeriod, Integer.parseInt(rest));
        assertEquals(newTimer.rounds,Integer.parseInt(rounds));
        assertEquals(newTimer.cooldown, Integer.parseInt(cooldown));

    }


    private Activity getActivityInstance(){
        final Activity[] currentActivity = {null};

        getInstrumentation().runOnMainSync(new Runnable(){
            public void run(){
                Collection<Activity> resumedActivity = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                Iterator<Activity> it = resumedActivity.iterator();
                currentActivity[0] = it.next();
            }
        });

        return currentActivity[0];
    }
}
