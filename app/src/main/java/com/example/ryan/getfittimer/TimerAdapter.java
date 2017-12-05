package com.example.ryan.getfittimer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ryan.getfittimer.Timer.Timer;

import java.util.ArrayList;

/**
 * Created by Ryan on 12/2/2017.
 */

public class TimerAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Timer> mDataSource;

    public TimerAdapter(Context context, ArrayList<Timer> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.layout_list_view_row_items, parent, false);

        TextView titleTextView =
                (TextView) rowView.findViewById(R.id.listName);

        TextView warmupTextView =
                (TextView) rowView.findViewById(R.id.listWarmup);

        TextView intervalTextView =
                (TextView) rowView.findViewById(R.id.listInterval);

        TextView restTextView =
                (TextView) rowView.findViewById(R.id.listRest);

        TextView roundsTextView =
                (TextView) rowView.findViewById(R.id.listRounds);

        TextView cooldownTextView =
                (TextView) rowView.findViewById(R.id.listCooldown);

        Timer timer = (Timer) getItem(position);

        titleTextView.setText(timer.name);
        warmupTextView.setText("W: " + Integer.toString(timer.warmup) + "s");
        intervalTextView.setText("I: " + Integer.toString(timer.interval) + "s");
        restTextView.setText("Re: " + Integer.toString(timer.restPeriod) + "s");
        roundsTextView.setText("#Ro: " + Integer.toString(timer.rounds));
        cooldownTextView.setText("C: " + Integer.toString(timer.cooldown) + "s");

        return rowView;
    }

}
