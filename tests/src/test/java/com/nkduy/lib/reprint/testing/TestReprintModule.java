package com.nkduy.lib.reprint.testing;

import com.nkduy.lib.reprint.core.AuthenticationListener;
import com.nkduy.lib.reprint.core.Reprint;
import com.nkduy.lib.reprint.core.ReprintModule;

import java.util.Random;

import androidx.core.os.CancellationSignal;

public class TestReprintModule implements ReprintModule {
    public final int TAG = new Random().nextInt(); // Register a new module each test
    public CancellationSignal cancellationSignal;
    public AuthenticationListener listener;
    public Reprint.RestartPredicate restartPredicate;

    @Override public boolean isHardwarePresent() {
        return true;
    }

    @Override public boolean hasFingerprintRegistered() {
        return true;
    }

    @Override
    public void authenticate(CancellationSignal cancellationSignal, AuthenticationListener listener, Reprint.RestartPredicate restartPredicate) {
        this.cancellationSignal = cancellationSignal;
        this.listener = listener;
        this.restartPredicate = restartPredicate;
    }

    @Override public int tag() {
        return TAG;
    }
}
