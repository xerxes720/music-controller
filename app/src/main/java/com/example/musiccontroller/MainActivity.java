package com.example.musiccontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    long threadId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startTimerButton = findViewById(R.id.start_timer);
        startTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (threadId != 0){
//                    Thread thread = Thread.currentThread();
//                    thread.interrupt();
//                    threadId = 0;
//                }

                //Give you set of Threads
                Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();


                //Iterate over set to find yours
                for(Thread thread : setOfThread){
                    if(thread.getId()==threadId){
                        Log.e("interrupting" , "we're here with:"+Long.toString(thread.getId()));
                        thread.interrupt();
                        Log.e("is it working" ,Long.toString(threadId) + ":" + Boolean.toString(thread.isInterrupted()) + String.valueOf(Thread.interrupted()));
                        threadId = 0;
                        break;

                    }
                }

                EditText timeInHourEditText = findViewById(R.id.time_in_hour);
                EditText timeInMinEditText = findViewById(R.id.time_in_min);
                Log.e("threadCount" , Integer.toString(Thread.activeCount()));

                TextView timeRemainingTextView = findViewById(R.id.remaining_time);
                int timeInHour =Integer.parseInt(timeInHourEditText.getText().toString());
                int timeInMin =Integer.parseInt(timeInMinEditText.getText().toString());
                final int delay = (timeInHour * 60 * 60 + timeInMin * 60)*1000;
                Toast toast = Toast.makeText(getApplicationContext(),"Your music will pause in " + delay/60/1000 + " minutes", Toast.LENGTH_SHORT);
                toast.show();
                Thread thread = new Thread(new TimerThread(delay , timeRemainingTextView));
                threadId = thread.getId();
                Log.e("original id" , Long.toString(thread.getId()));

                thread.start();
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
                        // Request audio focus for playback
                        am.requestAudioFocus(null,
                                // Use the music stream.
                                AudioManager.STREAM_MUSIC,
                                // Request permanent focus.
                                AudioManager.AUDIOFOCUS_GAIN);
                    }
                };
                timer.schedule(task , delay);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
