package com.yourgame.model.ManuFactor.Artisan;

import com.yourgame.model.WeatherAndTime.TimeObserver;
import com.yourgame.model.WeatherAndTime.TimeSystem;

public class ArtisanMachine implements TimeObserver {
    private boolean processing = false;
    private boolean mustBeCollected = false;
    private float timeRemaining = 0f;
    private boolean paused = false;
    private final ArtisanMachineType artisanMachineType;

    public ArtisanMachine(ArtisanMachineType artisanMachineType) {
        this.artisanMachineType = artisanMachineType;
    }

    public boolean isProcessing() { return processing; }

    public void startProcessing(float hours) {
        processing = true;
        timeRemaining = hours;
        paused = false;
    }

    public void stopProcessing() {
        processing = false;
        timeRemaining = 0f;
        paused = false;
    }

    public boolean isMustBeCollected() { return mustBeCollected; }

    public void setMustBeCollected(boolean mustBeCollected) {
        this.mustBeCollected = mustBeCollected;
    }

    public float getTimeRemaining() { return timeRemaining; }

    public void setTimeRemaining(float hours) {
        timeRemaining = hours;
        if (timeRemaining <= 0) {
            processing = false;
            mustBeCollected = true;
        }
    }

    public void reduceTime(float hours) {
        if (!paused && processing) {
            timeRemaining -= hours;
            if (timeRemaining <= 0) {
                processing = false;
                mustBeCollected = true;
            }
        }
    }

    public ArtisanMachineType getArtisanMachineType() { return artisanMachineType; }

    public void pauseProcessing() { paused = true; }
    public void resumeProcessing() { paused = false; }
    public boolean isPaused() { return paused; }

    @Override
    public void onTimeChanged(TimeSystem timeSystem) {
        reduceTime(1); // reduce 1 hour if processing and not paused
    }
}
