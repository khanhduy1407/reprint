package com.nkduy.lib.reprint.testing;

import android.app.Application;

import com.nkduy.lib.reprint.core.Reprint;

public class TestApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Reprint.initialize(this);
    }
}
