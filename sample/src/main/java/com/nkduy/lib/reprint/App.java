package com.nkduy.lib.reprint;

import android.app.Application;
import android.util.Log;

import com.nkduy.lib.reprint.core.Reprint;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Reprint.initialize(this, new Reprint.Logger() {
            @Override
            public void log(String message) {
                Log.d("Reprint", message);
            }

            @Override
            public void logException(Throwable throwable, String message) {
                Log.e("Reprint", message, throwable);
            }
        });
    }
}
