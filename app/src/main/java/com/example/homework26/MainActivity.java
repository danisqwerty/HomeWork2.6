package com.example.homework26;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    private Button buttonStart;
    private Button buttonPause;
    private Button buttonStop;

    private TextView stopwatchOut;

    private Button buttonTimeBack;

    private long startTime = 0L;
    private long timeInMilliseconds = 0L;
    private long timePause = 0L;
    private long updatedTime = 0L;



    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonStart = findViewById(R.id.buttonStart);
        buttonPause = findViewById(R.id.buttonPause);
        buttonStop = findViewById(R.id.buttonStop);
        stopwatchOut = findViewById(R.id.stopwatchOut);
        buttonTimeBack = findViewById(R.id.buttonTimeBack);
        // выполнение действий при нажании кнопки
        buttonStart.setOnClickListener(listener);
        buttonPause.setOnClickListener(listener);
        buttonStop.setOnClickListener(listener);
        buttonTimeBack.setOnClickListener(listener);
    }


    private View.OnClickListener listener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        public void onClick(View view) {



            switch (view.getId()) {
                case R.id.buttonStart: 
                    startTime = SystemClock.uptimeMillis();
                    handler.postDelayed(updateTimerThread, 0);
                    break;

                case R.id.buttonTimeBack: //
                    startTime = SystemClock.uptimeMillis();
                    handler.postDelayed(updateTimerBack, 0);

                case R.id.buttonPause:
                    timePause += timeInMilliseconds;
                    handler.removeCallbacks(  updateTimerThread );
                    break;

                case R.id.buttonStop:
                    startTime = 0L;
                    handler.removeCallbacks(  updateTimerThread );
                    stopwatchOut.setText(getString(R.string.messageStop));

                    break;
            }
        }
    };

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timePause + timeInMilliseconds;

            int milliseconds = (int) (updatedTime % 1000);
            int second = (int) (updatedTime / 1000);
            int minute = second / 60;
            int hour = minute / 60;
            int day = hour / 24;

            second = second % 60;
            minute = minute % 60;
            hour = hour % 24;


            stopwatchOut.setText("" + day + ":" + hour + ":" + minute + ":" + String.format("%02d", second) + ":" + String.format("%03d", milliseconds));
            handler.postDelayed(this, 0);

        }
    };
    private Runnable updateTimerBack = new Runnable() {
        public void run() {


            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timePause - timeInMilliseconds;

            int milliseconds = (int) (updatedTime % 1000);
            int second = (int) (updatedTime / 1000);
            int minute = second / 60;
            int hour = minute / 60;
            int day = hour / 24;

            second = second % 60;
            minute = minute % 60;
            hour = hour % 24;


            stopwatchOut.setText("" + day + ":" + hour + ":" + minute + ":" + String.format("%02d", second) + ":" + String.format("%03d", milliseconds));
            handler.postDelayed(this, 0);

        }
    };
}
