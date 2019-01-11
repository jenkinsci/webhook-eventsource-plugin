package io.jenkins.plugins.webhook;

import io.jenkins.plugins.webhook.eventsource.EventSource;

import java.net.URI;

public class EventSourceUtils {
    private static boolean started;

    public static EventSource createEventSource(SampleConfiguration config) {
        return new EventSource.Builder(new EventHandlerImpl(config.getWebhook()),
                URI.create(config.getLabel())).build();
    }

    public static void start(SampleConfiguration config) {
        if(started) {
            return;
        }

        new Thread(){
            @Override
            public void run() {
                createEventSource(config).start();
            }
        }.start();
    }
}
