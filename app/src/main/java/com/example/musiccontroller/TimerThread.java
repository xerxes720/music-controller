package com.example.musiccontroller;

import android.widget.TextView;

public class TimerThread implements Runnable {

    int delay;
    TextView textView;

    public TimerThread(int delay , TextView textView) {
        this.delay = delay/1000;
        this.textView = textView;
    }

    @Override
    public void run() {
        while(delay < 0){
            int hour = delay/3600;
            int minute = (delay - hour * 3600) / 60;
            int second = delay - hour * 3600 - minute * 60;
            textView.setText(hour + ":" + minute +":"+second);
            try {
                Thread.sleep(1 * 1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            delay -= 1;
        }
    }
}
