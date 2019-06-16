package com.example.musiccontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

                int timeInHour =Integer.parseInt(timeInHourEditText.getText().toString());
                int timeInMin =Integer.parseInt(timeInMinEditText.getText().toString());
                int delay = (timeInHour * 60 * 60 + timeInMin * 60)*1000;
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        Intent i = new Intent("com.android.music.musicservicecommand");
                        i.putExtra("command", "pause");
                        sendBroadcast(i);

                    }
                };
                timer.schedule(task , delay);


            }
        });

    }



    void onClickButton(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent("com.android.music.musicservicecommand");
                i.putExtra("command", "pause");
                sendBroadcast(i);

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
