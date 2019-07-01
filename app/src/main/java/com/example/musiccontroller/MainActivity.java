package com.example.musiccontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startTimerButton = findViewById(R.id.start_timer);
        startTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText timeInHourEditText = findViewById(R.id.time_in_hour);
                EditText timeInMinEditText = findViewById(R.id.time_in_min);
                TextView timeRemainingTextView = findViewById(R.id.remaining_time);
                int timeInHour =Integer.parseInt(timeInHourEditText.getText().toString());
                int timeInMin =Integer.parseInt(timeInMinEditText.getText().toString());
                final int delay = (timeInHour * 60 * 60 + timeInMin * 60)*1000;
                Toast toast = Toast.makeText(getApplicationContext(),"Your music will pause in " + delay/60/1000 + " minutes", Toast.LENGTH_SHORT);
                toast.show();
                Thread thread = new Thread(new TimerThread(delay , timeRemainingTextView));

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

    public String[] getCurrentTime() {

//        Calendar cal = Calendar.getInstance();
//
//        Date date = cal.getTime();
//
//        DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.);
//
//        String formattedDate=dateFormat.format(date);
//
//        Log.e("MyTime", formattedDate);
////        System.out.println("Current time of the day using Calendar - 24 hour format: "+ formattedDate);
//        Instant instant = Instant.now();
//        Date date = new Date();
//        date.setHours(date.getHours() + 8);
//        SimpleDateFormat simpDate;
//        simpDate = new SimpleDateFormat("kk:mm:ss");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String currentTime = dtf.format(now);
        String[] time = currentTime.split(" ")[1].split(":");
        return time;

    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
