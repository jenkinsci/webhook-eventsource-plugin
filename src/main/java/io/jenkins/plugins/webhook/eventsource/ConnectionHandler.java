package io.jenkins.plugins.webhook.eventsource;

interface ConnectionHandler {
  void setReconnectionTimeMs(long reconnectionTimeMs);
  void setLastEventId(String lastEventId);
}