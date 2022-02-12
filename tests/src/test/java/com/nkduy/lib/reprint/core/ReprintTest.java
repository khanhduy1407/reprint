package com.nkduy.lib.reprint.core;

import com.nkduy.lib.reprint.testing.TestReprintModule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
@RunWith(MockitoJUnitRunner.class)
public class ReprintTest {
    public TestReprintModule module;

    @Mock
    public AuthenticationListener listener;

    @Before
    public void setup() {
        module = new TestReprintModule();
        module.listener = null;
        module.cancellationSignal = null;
        Reprint.registerModule(module);

        Reprint.authenticate(listener);
        assertThat(module.listener).isNotNull();
    }

    @Test
    public void successfulRequest() throws Exception {
        verifyZeroInteractions(listener);
        module.listener.onSuccess(module.TAG);
        verify(listener).onSuccess(anyInt());
    }

    @Test
    public void failedRequest() throws Exception {
        verifyZeroInteractions(listener);
        module.listener.onFailure(AuthenticationFailureReason.AUTHENTICATION_FAILED, false, "", module.TAG, 0);
        verify(listener).onFailure(eq(AuthenticationFailureReason.AUTHENTICATION_FAILED), eq(false), anyString(), anyInt(), anyInt());
    }
}
