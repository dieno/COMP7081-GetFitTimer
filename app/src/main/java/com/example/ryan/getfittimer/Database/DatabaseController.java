package com.example.ryan.getfittimer.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ryan.getfittimer.Timer.Timer;

import java.util.ArrayList;

/**
 * Created by Ryan on 12/2/2017.
 */

public class DatabaseController {
    public Context mContext;
    String dbName = "GetFitTimer.db";
    SQLiteDatabase db;

    public DatabaseController(Context context) {
        mContext = context;
        db = context.openOrCreateDatabase(dbName, 0, null);

        if (!db.isOpen()) {
            return;
        }

        db.beginTransaction();
        try {
            //db.execSQL("DROP TABLE IF EXISTS " + "Workouts");
            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + "Workouts"
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, warmup VARCHAR, interval VARCHAR, rest VARCHAR, rounds VARCHAR, cooldown VARCHAR);");

            //db.execSQL("DROP TABLE IF EXISTS " + "Progress");

            db.execSQL("CREATE TABLE IF NOT EXISTS "
                    + "Progress"
                    + " (id INTEGER PRIMARY KEY AUTOINCREMENT, seconds VARCHAR);");

            String count = "SELECT count(*) FROM Progress";
            Cursor mcursor = db.rawQuery(count, null);
            mcursor.moveToFirst();
            int icount = mcursor.getInt(0);
            if(icount == 0) {
                String zeroSeconds = Integer.toString(0);
                String sqlq = "INSERT INTO Progress (seconds) VALUES ('" + zeroSeconds +  "');";
                db.execSQL(sqlq);
            }


            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

    }

    public void addWorkout(Timer timer) {
        if(!db.isOpen()) {
            return;
        }

        db.beginTransaction();
        try{
            String sqlq = "INSERT INTO Workouts (name, warmup, interval, rest, rounds, cooldown) VALUES ('" + timer.name + "', '" + Integer.toString(timer.warmup) + "', '" + Integer.toString(timer.interval) + "', '" + Integer.toString(timer.restPeriod) + "', '" + Integer.toString(timer.rounds) + "', '" + Integer.toString(timer.cooldown) + "');";
            db.execSQL(sqlq);
            db.setTransactionSuccessful();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        finally
        {
            db.endTransaction();
        }
    }

    public ArrayList<Timer> GetAllWorkouts() {
        ArrayList<Timer> workouts = new ArrayList<Timer>();

        Cursor c = db.query("Workouts", null, null, null, null, null, null);


        if (c != null) {
            c.moveToFirst();

            for (int i = 0; i < c.getCount(); i++) {
                //int idColumn = c.getColumnIndex("id");
                int nameColumn = c.getColumnIndex("name");
                int warmupColumn = c.getColumnIndex("warmup");
                int intervalColumn = c.getColumnIndex("interval");
                int restColumn = c.getColumnIndex("rest");
                int roundsColumn = c.getColumnIndex("rounds");
                int cooldownColumn = c.getColumnIndex("cooldown");

                String name = c.getString(nameColumn);
                String warmup = c.getString(warmupColumn);
                String interval = c.getString(intervalColumn);
                String rest = c.getString(restColumn);
                String rounds = c.getString(roundsColumn);
                String cooldown = c.getString(cooldownColumn);

                Timer newTimer = new Timer();
                newTimer.name = name;
                newTimer.warmup = Integer.parseInt(warmup);
                newTimer.interval = Integer.parseInt(interval);
                newTimer.restPeriod = Integer.parseInt(rest);
                newTimer.rounds = Integer.parseInt(rounds);
                newTimer.cooldown = Integer.parseInt(cooldown);

                workouts.add(newTimer);

                c.moveToNext();
            }
            c.close();

        }

        return workouts;
    }

    public Timer GetWorkout(int id) {
        Cursor c = db.query("Workouts", null, null, null, null, null, null);

        if (c != null) {
            c.moveToFirst();

            for (int i = 0; i < c.getCount(); i++) {
                int idColumn = c.getColumnIndex("id");

                int mid = Integer.parseInt(c.getString(idColumn)); ;

                if(mid == id) {
                    int nameColumn = c.getColumnIndex("name");
                    int warmupColumn = c.getColumnIndex("warmup");
                    int intervalColumn = c.getColumnIndex("interval");
                    int restColumn = c.getColumnIndex("rest");
                    int roundsColumn = c.getColumnIndex("rounds");
                    int cooldownColumn = c.getColumnIndex("cooldown");

                    String name = c.getString(nameColumn);
                    String warmup = c.getString(warmupColumn);
                    String interval = c.getString(intervalColumn);
                    String rest = c.getString(restColumn);
                    String rounds = c.getString(roundsColumn);
                    String cooldown = c.getString(cooldownColumn);

                    Timer workout = new Timer();
                    workout.name = name;
                    workout.warmup = Integer.parseInt(warmup);
                    workout.interval = Integer.parseInt(interval);
                    workout.restPeriod = Integer.parseInt(rest);
                    workout.rounds = Integer.parseInt(rounds);
                    workout.cooldown = Integer.parseInt(cooldown);

                    return workout;
                }

                c.moveToNext();
            }
            c.close();

        }

        return null;
    }



    public String GetProgress() {
        Cursor c = db.rawQuery("SELECT * FROM Progress", null);
        c.moveToFirst();
        int secondsColumn = c.getColumnIndex("seconds");
        //int idColumn = c.getColumnIndex("id");
        //String currentid = c.getString(idColumn);
        String currentSeconds = c.getString(secondsColumn);
        c.close();

        return currentSeconds;
    }

    public void AddProgress(int seconds) {
        Cursor c = db.rawQuery("SELECT * FROM Progress", null);
        c.moveToFirst();
        int secondsColumn = c.getColumnIndex("seconds");
        String currentSeconds = c.getString(secondsColumn);

        c.close();

        int currentSecondsInt = Integer.parseInt(currentSeconds);
        currentSecondsInt += seconds;

        String currentSecondsIntString = Integer.toString(currentSecondsInt);

        if(!db.isOpen()) {
            return;
        }

        db.beginTransaction();
        try{
            //String sqlq = "INSERT INTO Progress (seconds) VALUES ('" + currentSecondsIntString +  "');";

            String sqlq = "UPDATE Progress SET seconds='" + currentSecondsIntString + "' WHERE id='1'";

            db.execSQL(sqlq);
            db.setTransactionSuccessful();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        finally
        {
            db.endTransaction();
        }
    }




}
