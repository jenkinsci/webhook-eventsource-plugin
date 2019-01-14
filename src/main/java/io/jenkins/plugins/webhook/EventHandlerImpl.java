package io.jenkins.plugins.webhook;

import io.jenkins.plugins.webhook.eventsource.EventHandler;
import io.jenkins.plugins.webhook.eventsource.MessageEvent;
import net.sf.json.JSONObject;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.logging.Logger;

public class EventHandlerImpl implements EventHandler {
    private Logger logger = Logger.getLogger(EventHandlerImpl.class.getName());
    private String webhook;

    public EventHandlerImpl(String webhook) {
        this.webhook = webhook;
    }

    public void onOpen() throws Exception {
        logger.info("open");
    }

    @Override
    public void onClosed() throws Exception {

    }

    public void onMessage(String event, MessageEvent messageEvent) throws Exception {
        if(!"message".equalsIgnoreCase(event)) {
            return;
        }

        JSONObject json = JSONObject.fromObject(messageEvent.getData());

        Request.Builder builder = new Request.Builder();
        builder.url(webhook);
        builder.post(RequestBody.create(MediaType.parse(json.getString("content-type")),
                json.getJSONObject("body").toString().getBytes()));
        json.forEach((key, value) -> {
            if(value instanceof String) {
                builder.addHeader(key, value.toString());
            }
        });
        Request request = builder.build();

        Call call = new OkHttpClient().newCall(request);
        try {
            Response response = call.execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onError(Throwable t) {
        logger.severe("Error: " + t);
    }

    public void onComment(String comment) {
        logger.info("comment: " + comment);
    }

}
