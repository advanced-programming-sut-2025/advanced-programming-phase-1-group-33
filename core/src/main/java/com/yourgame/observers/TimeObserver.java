package com.yourgame.observers;

import com.yourgame.model.WeatherAndTime.TimeSystem;

public interface TimeObserver {
    void onTimeChanged(TimeSystem timeSystem);
}
