package com.example.grandfinal;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;
import java.util.Locale;

public class fragment2 extends DialogFragment {
    ProgressBar mProgressBar;
    TextView mPercentage;
    TextView mtext;
    CountDownTimer countDownTimer;
    Button show_picker,start,cancel;
    TimePickerDialog timePickerDialog;
    TimePickerDialog.OnTimeSetListener onTimeSetListener;
    int progress = 0;//變化參數
    boolean isRunning=false;
    long mTimeLeftInMills,end;
    int hours,mins;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag2, container, false);
        mtext=rootView.findViewById(R.id.textview);
        show_picker = rootView.findViewById(R.id.button);
        start=rootView.findViewById(R.id.button3);
        cancel=rootView.findViewById(R.id.button2);
        mProgressBar = rootView.findViewById(R.id.progress_bar);
        mPercentage = rootView.findViewById(R.id.progress_tv);
        onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                DecimalFormat decimalFormat = new DecimalFormat("00");
                mtext.setText(decimalFormat.format(hourOfDay) + ":" + decimalFormat.format(minute)+ ":"+decimalFormat.format(0));
                end=(long) ((hourOfDay*60*60*1000)+(minute*60*1000));
                mTimeLeftInMills=end;
                mProgressBar.setMax((int)mTimeLeftInMills);
                int hours=(int)((mTimeLeftInMills/1000)/60)/60;
                int minutes=(int) ((mTimeLeftInMills/1000)/60)%60;
                int seconds=(int) (mTimeLeftInMills/1000)%60;
                if(hours!=0||minutes!=0){
                    start.setBackgroundColor(Color.YELLOW);
                    start.setClickable(true);
                }
                else{
                    start.setBackgroundColor(Color.GRAY);
                    start.setClickable(false);
                }
                mPercentage.setText(0 + "%");
                mtext.setText(String.format(Locale.getDefault(),"%02d:%02d:%02d",hours,minutes,seconds));
            }
        };
        show_picker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(getActivity(), onTimeSetListener, hours,mins, true);
                timePickerDialog.show();
            }
        });
        start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                cancel.setClickable(true);
                cancel.setBackgroundColor(Color.YELLOW);
                show_picker.setClickable(false);
                show_picker.setBackgroundColor(Color.GRAY);
                if (isRunning){
                    countDownTimer.cancel();
                    isRunning=false;
                    start.setText("繼續");
                }
                else{
                    countDownTimer = new CountDownTimer(mTimeLeftInMills,500) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            mTimeLeftInMills=millisUntilFinished;
                            progress=(int) ( (((double)(end-mTimeLeftInMills))/end)*100);
                            mProgressBar.setProgress((int) ( end-mTimeLeftInMills));
                            mPercentage.setText(progress + "%");
                            int hours=(int)((mTimeLeftInMills/1000)/60)/60;
                            int minutes=(int) ((mTimeLeftInMills/1000)/60)%60;
                            int seconds=(int) (mTimeLeftInMills/1000)%60;
                            mtext.setText(String.format(Locale.getDefault(),"%02d:%02d:%02d",hours,minutes,seconds));
                        }
                        @Override
                        public void onFinish() {
                            mtext.setText("Done!");
                            if (progress != 0) {
                                mPercentage.setText("100%");
                                mProgressBar.setProgress((int)end);
                            } else {
                                mPercentage.setText("0%");
                            }
                            isRunning=false;
                            start.setText("開始");
                            show_picker.setClickable(true);
                            show_picker.setBackgroundColor(Color.YELLOW);
                            start.setBackgroundColor(Color.GRAY);
                            start.setClickable(false);
                            cancel.setBackgroundColor(Color.GRAY);
                            cancel.setClickable(false);
                        }
                    }.start();
                    isRunning=true;
                    start.setText("暫停");
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mTimeLeftInMills=end;
                int hours=(int)((mTimeLeftInMills/1000)/60)/60;
                int minutes=(int) ((mTimeLeftInMills/1000)/60)%60;
                int seconds=(int) (mTimeLeftInMills/1000)%60;
                mtext.setText(String.format(Locale.getDefault(),"%02d:%02d:%02d",hours,minutes,seconds));
                countDownTimer.cancel();
                isRunning=false;
                progress=0;
                mProgressBar.setProgress(0);
                mPercentage.setText("0%");
                start.setText("開始");
                show_picker.setBackgroundColor(Color.YELLOW);
                show_picker.setClickable(true);
                cancel.setBackgroundColor(Color.GRAY);
                cancel.setClickable(false);
            }
        });
        show_picker.setClickable(true);
        show_picker.setBackgroundColor(Color.YELLOW);
        start.setBackgroundColor(Color.GRAY);
        start.setClickable(false);
        cancel.setBackgroundColor(Color.GRAY);
        cancel.setClickable(false);
        int hours=(int)((mTimeLeftInMills/1000)/60)/60;
        int minutes=(int) ((mTimeLeftInMills/1000)/60)%60;
        int seconds=(int) (mTimeLeftInMills/1000)%60;
        mtext.setText(String.format(Locale.getDefault(),"%02d:%02d:%02d",hours,minutes,seconds));
        return rootView;
    }
}