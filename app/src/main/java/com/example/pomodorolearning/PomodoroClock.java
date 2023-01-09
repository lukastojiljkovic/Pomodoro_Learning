package com.example.pomodorolearning;

import java.util.Timer;
import java.util.TimerTask;

public class PomodoroClock {
    private final Timer timer;
    private int timeRemaining;
    private int pomodorosCompleted;
    private final int workTime;
    private final int shortBreakTime;
    private final int longBreakTime;
    private static final int DEFAULT_WORK_TIME = 20;
    private static final int DEFAULT_SHORT_BREAK_TIME = 5;
    private static final int DEFAULT_LONG_BREAK_TIME = 20;
    private boolean isRunning;

    public PomodoroClock() {
        this(DEFAULT_WORK_TIME, DEFAULT_SHORT_BREAK_TIME, DEFAULT_LONG_BREAK_TIME);
    }

    public PomodoroClock(int workTime, int shortBreakTime, int longBreakTime) {
        this.workTime = workTime;
        this.shortBreakTime = shortBreakTime;
        this.longBreakTime = longBreakTime;
        timeRemaining = workTime * 60;
        pomodorosCompleted = 0;
        timer = new Timer();
        isRunning = false;
    }

    public void start() {
        if (isRunning) {
            return;
        }
        isRunning = true;
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                timeRemaining--;
                if (timeRemaining == 0) {
                    pomodorosCompleted++;
                    if (pomodorosCompleted == 8) {
                        // Pomodoro session is finished
                        timer.cancel();
                        timer.purge();
                        isRunning = false;
                    } else if (pomodorosCompleted % 4 == 0) {
                        // Take a long break
                        timeRemaining = longBreakTime * 60;
                    } else {
                        // Take a short break
                        timeRemaining = shortBreakTime * 60;
                    }
                }
            }
        }, 1000, 1000);
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public int getPomodorosCompleted() {
        return pomodorosCompleted;
    }

    public int getLongBreakTime() {
        return longBreakTime;
    }

    public int getWorkTime() {
        return workTime;
    }

    public int getShortBreakTime() {
        return shortBreakTime;
    }
}