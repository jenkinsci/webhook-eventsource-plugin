package io.jenkins.plugins.webhook;

import hudson.Extension;
import hudson.Plugin;
import io.jenkins.plugins.webhook.eventsource.EventSource;
import java.util.logging.Logger;

@Extension
public class WebHookProxyPlugin extends Plugin {
    private Logger logger = Logger.getLogger(WebHookProxyPlugin.class.getName());
    private EventSource source;

    @Override
    public void start() throws Exception {
        SampleConfiguration config = SampleConfiguration.get();
        if(!config.isValid()) {
            logger.severe("config is not valid");
            return;
        }

        source = EventSourceUtils.createEventSource(config);
        new Thread(){
            @Override
            public void run() {
                source.start();
            }
        }.start();
    }

    @Override
    public void stop() throws Exception {
        source.close();
    }
}
