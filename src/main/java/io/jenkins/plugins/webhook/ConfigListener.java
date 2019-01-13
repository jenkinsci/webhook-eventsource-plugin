package io.jenkins.plugins.webhook;

import hudson.Extension;
import hudson.XmlFile;
import hudson.model.Saveable;
import hudson.model.listeners.SaveableListener;

@Extension
public class ConfigListener extends SaveableListener {
    @Override
    public void onChange(Saveable o, XmlFile file) {
        if(!(o instanceof EventSourceServerConfiguration)) {
            return;
        }

        EventSourceServerConfiguration config = (EventSourceServerConfiguration) o;
        if(config.isValid()) {
            WebHookProxyPlugin.restart();
        }
    }
}
