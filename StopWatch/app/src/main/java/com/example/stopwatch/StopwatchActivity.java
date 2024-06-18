package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StopwatchActivity extends AppCompatActivity {

    private TextView timerTextView;
    private Button startButton;
    private Button pauseButton;
    private Button resetButton;

    private long startTime = 0;
    private long currentTime = 0;
    private long pauseTime = 0;
    private boolean isRunning = false;
    private boolean isPaused = false;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_stopwatch);

        timerTextView = findViewById(R.id.timer_textview);
        startButton = findViewById(R.id.start_button);
        pauseButton = findViewById(R.id.pause_button);
        resetButton = findViewById(R.id.reset_button);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    startTime = System.currentTimeMillis();
                    isRunning = true;
                    isPaused = false;
                    handler.postDelayed(runnable, 0);
                }
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    pauseTime = System.currentTimeMillis();
                    isRunning = false;
                    isPaused = true;
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = 0;
                currentTime = 0;
                pauseTime = 0;
                isRunning = false;
                isPaused = false;
                timerTextView.setText("00:00.000");
            }
        });
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isRunning) {
                currentTime = System.currentTimeMillis() - startTime;
                if (isPaused) {
                    currentTime = pauseTime - startTime;
                }
                int minutes = (int) (currentTime / 60000);
                int seconds = (int) ((currentTime % 60000) / 1000);
                int milliseconds = (int) (currentTime % 1000);
                String timerText = String.format("%02d:%02d.%03d", minutes, seconds, milliseconds);
                timerTextView.setText(timerText);
                handler.postDelayed(this, 0);
            }
        }
    };
}

