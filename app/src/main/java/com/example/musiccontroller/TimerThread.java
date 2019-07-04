package com.example.musiccontroller;

import android.util.Log;
import android.widget.TextView;

public class TimerThread implements Runnable {

    int delay;
    long threadId;
    TextView textView;
    boolean flag = true;
    Thread thread;

    public TimerThread(int delay , TextView textView) {
        this.delay = delay / 1000;
        this.textView = textView;
    }

    public TimerThread(Thread thread ,int delay , TextView textView) {
        this.delay = delay / 1000;
        this.textView = textView;
        this.thread = thread;
    }


    @Override
    public void run() {
        while(delay > 0 ){
//            Log.e("current Thread Id" , Long.toString(Thread.currentThread().getId()) +":"+ Boolean.toString(Thread.currentThread().isInterrupted()) + ":" + Thread.interrupted());

            if(Thread.interrupted()){
                Log.e("in Run", "about to break");
                break;
            }
            delay -= 1;
            int hour = delay/3600;
            int minute = (delay - hour * 3600) / 60;
            int second = delay - hour * 3600 - minute * 60;
            textView.setText(hour + ":" + minute +":"+second);


            if (Thread.interrupted()) {
                try {
                    Thread.sleep(1 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    Thread.sleep(1 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
