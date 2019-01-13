package io.jenkins.plugins.webhook;

import hudson.init.InitMilestone;
import hudson.init.Initializer;
import hudson.init.TermMilestone;
import hudson.init.Terminator;
import io.jenkins.plugins.webhook.eventsource.EventSource;

import java.net.URI;
import java.util.logging.Logger;

public class WebHookProxyPlugin {
    private static Logger logger = Logger.getLogger(WebHookProxyPlugin.class.getName());
    private static EventSource source;

    @Initializer(after = InitMilestone.JOB_LOADED, fatal = false)
    public static void start() {
        EventSourceServerConfiguration config = EventSourceServerConfiguration.get();
        if(config == null || !config.isValid()) {
            logger.warning("WebHookProxy config is invalid.");
            return;
        }

        source = new EventSource.Builder(new EventHandlerImpl(config.getWebhook()),
                URI.create(config.getServer())).build();
        source.start();
        logger.info("WebHookProxy is started.");
    }

    @Terminator(after = TermMilestone.STARTED)
    public static void stop() {
        if(source != null) {
            source.close();
            source = null;
        }
    }

    public static void restart() {
        stop();
        start();
    }
}
