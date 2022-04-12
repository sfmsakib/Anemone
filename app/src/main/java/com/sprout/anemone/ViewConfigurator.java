package com.sprout.anemone;

import android.view.View;

public interface ViewConfigurator<T extends View> {
    void configureView(T v);
}