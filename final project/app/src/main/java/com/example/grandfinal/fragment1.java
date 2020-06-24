package com.example.grandfinal;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class fragment1 extends Fragment {
    Chronometer cm,cm2;
    Button bt,resetTime,Lap_bt;
    TextView time,time_his;
    long escapeTime = 0;
    long escapeTime2=0;
    long besttime=999999999;
    long crictime=0;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.frag1, container, false);
        bt=(Button)rootView.findViewById(R.id.butChronometer);
        cm= (Chronometer) rootView.findViewById(R.id.chronometer);
        cm2= (Chronometer) rootView.findViewById(R.id.chronometer4);
        Lap_bt=(Button)rootView.findViewById(R.id.lap);
        resetTime=(Button)rootView.findViewById(R.id.reset);
        time=(TextView) rootView.findViewById(R.id.textView2);
        time_his=(TextView)rootView.findViewById(R.id.textView3);
        resetTime.setClickable(false);
        Lap_bt.setClickable(false);
        bt.setBackgroundColor(Color.YELLOW);
        resetTime.setBackgroundColor(Color.GRAY);
        Lap_bt.setBackgroundColor(Color.GRAY);
        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(bt.getText().equals("start")) {
                    cm.setBase(SystemClock.elapsedRealtime());
                    cm2.setBase(SystemClock.elapsedRealtime());
                    cm.start();
                    cm2.start();
                    bt.setText("stop");
                    resetTime.setClickable(true);
                    Lap_bt.setClickable(true);
                    resetTime.setBackgroundColor(Color.YELLOW);
                    Lap_bt.setBackgroundColor(Color.YELLOW);
                }
                else if(bt.getText().equals("stop")){
                    cm.stop();
                    cm2.stop();
                    bt.setText("continue");
                    Lap_bt.setClickable(false);
                    Lap_bt.setBackgroundColor(Color.GRAY);
                    escapeTime = cm.getBase() - SystemClock.elapsedRealtime();
                    escapeTime2=cm2.getBase() - SystemClock.elapsedRealtime();
                }
                else if(bt.getText().equals("continue")){
                    bt.setText("stop");
                    Lap_bt.setClickable(true);
                    Lap_bt.setBackgroundColor(Color.YELLOW);
                    cm.setBase(SystemClock.elapsedRealtime() + escapeTime);
                    cm2.setBase(SystemClock.elapsedRealtime() + escapeTime2);
                    cm.start();
                    cm2.start();
                }
            }
        });
        resetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cm.setBase(SystemClock.elapsedRealtime());
                cm2.setBase(SystemClock.elapsedRealtime());
                cm.stop();
                cm2.stop();
                escapeTime = 0;
                escapeTime2=0;
                besttime=999999999;
                bt.setText("start");
                crictime=0;
                resetTime.setClickable(false);
                Lap_bt.setClickable(false);
                resetTime.setBackgroundColor(Color.GRAY);
                Lap_bt.setBackgroundColor(Color.GRAY);
                time.setText("");
                time_his.setText("");
            }
        });
        Lap_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crictime=SystemClock.elapsedRealtime()-cm2.getBase();
                long min,sec,msec;
                min=crictime/60000;
                sec=(crictime-min*60000)/1000;
                msec=crictime%1000;
                time.setText("單圈成績:\n"+min+":"+sec+":"+msec);
                if(crictime<besttime)
                    besttime=crictime;
                min=besttime/60000;
                sec=(besttime-min*60000)/1000;
                msec=besttime%1000;
                time_his.setText("歷史最佳:\n"+min+":"+sec+":"+msec);
                cm2.stop();
                cm2.setBase(SystemClock.elapsedRealtime());
                cm2.start();
            }
        });
        return rootView;
    }
}
