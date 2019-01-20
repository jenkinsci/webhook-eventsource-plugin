package io.jenkins.plugins.webhook;

import hudson.Extension;
import jenkins.model.GlobalConfiguration;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.StaplerRequest;

/**
 * EventSource server of Jenkins global configuration.
 */
@Extension @Symbol("EventSource")
public class EventSourceServerConfiguration extends GlobalConfiguration {

    /** @return the singleton instance */
    public static EventSourceServerConfiguration get() {
        return GlobalConfiguration.all().get(EventSourceServerConfiguration.class);
    }

    private String server;
    private String webhook;

    public EventSourceServerConfiguration() {
        // When Jenkins is restarted, load any saved configuration from disk.
        load();
    }

    @Override
    public boolean configure(StaplerRequest req, JSONObject json) throws FormException {
        boolean result = super.configure(req, json);
        save();
        return result;
    }

    public String getServer() {
        return server;
    }

    @DataBoundSetter
    public void setServer(String server) {
        this.server = server;
    }

    public String getWebhook() {
        return webhook;
    }

    @DataBoundSetter
    public void setWebhook(String webhook) {
        this.webhook = webhook;
    }

    public boolean isValid() {
        return StringUtils.isNotBlank(server) && StringUtils.isNotBlank(webhook);
    }
}
