package com.nkduy.lib.reprint.core;

import com.nkduy.lib.reprint.testing.TestApp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(application = TestApp.class, manifest = Config.NONE)
public class ReprintRobolectricTest {
    @Test
    public void reprint_initialize() {
        Reprint.initialize(RuntimeEnvironment.application);
    }
}
