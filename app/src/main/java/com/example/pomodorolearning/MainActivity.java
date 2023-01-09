package com.example.pomodorolearning;

import static java.lang.String.*;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    PomodoroClock clock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clock = new PomodoroClock();

        Button startButton = findViewById(R.id.btStart);
        startButton.setOnClickListener(view -> {
            clock = new PomodoroClock();
            clock.start();
        });

        ProgressBar progressBar;
        progressBar = findViewById(R.id.progressBar);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    int timeRemaining = clock.getTimeRemaining();
                    int pomodorosCompleted = clock.getPomodorosCompleted();
                    int maxValue;
                    if (pomodorosCompleted % 4 == 0 && pomodorosCompleted > 0) {
                        // Long break
                        maxValue = clock.getLongBreakTime() * 60;
                    } else // Short break
                        if (pomodorosCompleted % 4 == 0) {
                        // Work period
                        maxValue = clock.getWorkTime() * 60;
                    } else {
                            maxValue = clock.getShortBreakTime() * 60;
                        }
                    progressBar.setMax(maxValue);
                    progressBar.setProgress(timeRemaining);
                });
            }
        }, 1000, 1000);

        TextView textView = findViewById(R.id.remainingTime);
        timer.scheduleAtFixedRate(new TimerTask() {
            private void run2() {
                int timeRemaining = clock.getTimeRemaining();
                int minutes = timeRemaining / 60;
                int seconds = timeRemaining % 60;
                @SuppressLint("DefaultLocale") String timeString = format("%d:%02d", minutes, seconds);
                textView.setText(timeString);
            }
            @Override
            public void run() {
                runOnUiThread(this::run2);
            }
        }, 1000, 1000);

        Button exitButton = findViewById(R.id.btExit);
        exitButton.setOnClickListener(view -> finish());

    }
}