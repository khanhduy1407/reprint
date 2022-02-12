package com.nkduy.lib.reprint;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.nkduy.lib.reprint.core.AuthenticationFailureReason;
import com.nkduy.lib.reprint.core.AuthenticationListener;
import com.nkduy.lib.reprint.core.Reprint;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.result)
    TextView result;

    @BindView(R.id.hardware_present)
    TextView hardwarePresent;

    @BindView(R.id.fingerprints_registered)
    TextView fingerprintsRegistered;

    @BindView(R.id.radio_callbacks)
    CompoundButton radioCallbacks;

    @BindView(R.id.radio_rxjava1)
    CompoundButton radioRxJava1;

    @BindView(R.id.radio_rxjava2)
    CompoundButton radioRxJava2;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        hardwarePresent.setText(String.valueOf(Reprint.isHardwarePresent()));
        fingerprintsRegistered.setText(String.valueOf(Reprint.hasFingerprintRegistered()));

        running = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        cancel();
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        if (running) {
            cancel();
        } else {
            start();
        }
    }

    private void start() {
        running = true;
        result.setText("Listening");
        fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_close_white_24dp));

        if (radioCallbacks.isChecked()) {
            startTraditional();
        } else if (radioRxJava1.isChecked()) {
            startRxJava1();
        } else if (radioRxJava2.isChecked()) {
            startRxJava2();
        } else {
            throw new IllegalStateException();
        }
    }

    private void startTraditional() {
        Reprint.authenticate(new AuthenticationListener() {
            @Override
            public void onSuccess(int moduleTag) {
                showSuccess();
            }

            @Override
            public void onFailure(AuthenticationFailureReason failureReason, boolean fatal,
                                  CharSequence errorMessage, int moduleTag, int errorCode) {
                showError(failureReason, fatal, errorMessage, errorCode);
            }
        });
    }

    private void startRxJava1() {
        com.nkduy.lib.reprint.rxjava.RxReprint.authenticate()
                .subscribe(res -> {
                    switch (res.status) {
                        case SUCCESS:
                            showSuccess();
                            break;
                        case NONFATAL_FAILURE:
                            showError(res.failureReason, false, res.errorMessage, res.errorCode);
                            break;
                        case FATAL_FAILURE:
                            showError(res.failureReason, true, res.errorMessage, res.errorCode);
                            break;
                    }
                });
    }

    private void startRxJava2() {
        com.nkduy.lib.reprint.rxjava2.RxReprint.authenticate()
                .subscribe(res -> {
                    switch (res.status) {
                        case SUCCESS:
                            showSuccess();
                            break;
                        case NONFATAL_FAILURE:
                            showError(res.failureReason, false, res.errorMessage, res.errorCode);
                            break;
                        case FATAL_FAILURE:
                            showError(res.failureReason, true, res.errorMessage, res.errorCode);
                            break;
                    }
                });
    }

    private void cancel() {
        result.setText("Cancelled");
        running = false;
        fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fingerprint_white_24dp));
        Reprint.cancelAuthentication();
    }

    private void showSuccess() {
        result.setText("Success");
        fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fingerprint_white_24dp));
        running = false;
    }

    private void showError(AuthenticationFailureReason failureReason, boolean fatal,
                           CharSequence errorMessage, int errorCode) {
        result.setText(errorMessage);

        if (fatal) {
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_fingerprint_white_24dp));
            running = false;
        }
    }
}
