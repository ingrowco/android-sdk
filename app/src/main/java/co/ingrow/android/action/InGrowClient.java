package co.ingrow.android.action;

import android.app.Application;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import co.ingrow.android.Const;
import co.ingrow.android.InGrowLogging;
import co.ingrow.android.rest.RestClient;
import co.ingrow.android.util.InGrowNetworkStatusHandler;
import co.ingrow.android.util.NetworkStatusHandler;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class InGrowClient {

    private final InGrowProject inGrowProject;
    private final InGrowNetworkStatusHandler networkStatusHandler;
    private InGrowSession inGrowSession;
    private boolean isDebugMode;

    public static void enrichmentBySession(InGrowSession inGrowSession) {
        if (ClientSingleton.INSTANCE.client == null) {
            throw new IllegalStateException("Please call InGrowClient.initialize() before requesting the enrichmentBySession.");
        }
        if (ClientSingleton.INSTANCE.client.inGrowProject.getAnonymousId() == null) {
            throw new IllegalStateException("You had to set Anonymous ID while you were initializing InGrow.");
        }
        if (inGrowSession == null) {
            throw new IllegalStateException("InGrowSession must not be null.");
        }
        ClientSingleton.INSTANCE.client.inGrowSession = inGrowSession;
    }

    public static InGrowClient client() {
        if (ClientSingleton.INSTANCE.client == null) {
            throw new IllegalStateException("Please call InGrowClient.initialize() before requesting the client.");
        }
        return ClientSingleton.INSTANCE.client;
    }

    private enum ClientSingleton {
        INSTANCE;
        InGrowClient client;
    }


    public static void initialize(InGrowClient client) {
        if (client == null) {
            throw new IllegalArgumentException("Client must not be null");
        }

        if (ClientSingleton.INSTANCE.client != null) {
            // Do nothing.
            return;
        }
        ClientSingleton.INSTANCE.client = client;
    }

    public static class Builder {

        private final InGrowProject inGrowProject;

        private final InGrowNetworkStatusHandler networkStatusHandler;

        public Builder(InGrowProject inGrowProject, Application application) {

            this.inGrowProject = inGrowProject;
            this.networkStatusHandler = new NetworkStatusHandler(application);
        }


        public InGrowClient build() {

            return buildInstance();
        }

        protected InGrowClient buildInstance() {

            return new InGrowClient(this);
        }
    }

    private boolean isNetworkConnected() {
        return networkStatusHandler.isNetworkConnected();
    }

    public void setDebugMode(boolean isDebugMode) {
        this.isDebugMode = isDebugMode;
    }

    public void logEvents(HashMap events) {
        if (events.isEmpty()) {
            handleFailure(new Exception("Events must not be null"));
            return;
        }
        if (!isNetworkConnected()) {
            InGrowLogging.log("Couldn't send events because of no network connection.");
            handleFailure(new Exception("Network's not connected."));
            return;
        }
        JSONObject main = new JSONObject();
        JSONObject inGrowObject = new JSONObject();
        JSONObject eventObject = new JSONObject();
        JSONObject enrichmentObject = new JSONObject();
        JSONArray enrichmentArray = new JSONArray();
        JSONObject inputObject = new JSONObject();
        try {
            inGrowObject.put(Const.PROJECT, this.inGrowProject.getProject());
            inGrowObject.put(Const.STREAM, this.inGrowProject.getStream());
            for (Object key : events.keySet()) {

                eventObject.put(String.valueOf(key), events.get(key));
            }
            if (this.inGrowSession == null) {
                if (this.inGrowProject.getAnonymousId() != null) {
                    inputObject.put(ENRICHMENT_KEY.ANONYMOUS_ID.id, this.inGrowProject.getAnonymousId());
                    if (this.inGrowProject.getUserId() != null)
                        inputObject.put(ENRICHMENT_KEY.USER_ID.id, this.inGrowProject.getUserId());
                    enrichmentObject.put(Const.NAME, Const.SESSION);
                    enrichmentObject.put(Const.INPUT, inputObject);
                    enrichmentArray.put(enrichmentObject);
                    main.put(Const.ENRICHMENT, enrichmentArray);
                }
            } else {
//                inputObject.put(ENRICHMENT_KEY.ANONYMOUS_ID.id, this.inGrowSession.getAnonymousId());
                if (this.inGrowSession.getUserId() != null)
                    inputObject.put(ENRICHMENT_KEY.USER_ID.id, this.inGrowSession.getUserId());
                enrichmentObject.put(Const.NAME, Const.SESSION);
                enrichmentObject.put(Const.INPUT, inputObject);
                enrichmentArray.put(enrichmentObject);
                main.put(Const.ENRICHMENT, enrichmentArray);
            }
            main.put(Const.INGROW, inGrowObject);
            main.put(Const.EVENT, eventObject);
        } catch (JSONException e) {
            handleFailure(e);
        }

        sendRequest(main);

    }

    private void sendRequest(JSONObject main) {
        RestClient.getInstance(this.inGrowProject.getApiKey(), main).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("ERROR SENDING EVENTS:", e != null ? e.toString() : "UNKNOWN");
                Log.d("EVENTS:", main.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                Log.d("SEND EVENTS SUCCEEDED:", response != null ? response.toString() : "UNKNOWN");
            }
        });
    }

    public enum ENRICHMENT_KEY {
        ANONYMOUS_ID("anonymous_id"),
        USER_ID("user_id");

        private final String id;

        ENRICHMENT_KEY(String envUrl) {
            this.id = envUrl;
        }
    }

    InGrowClient(Builder builder) {
        if (builder.inGrowProject.isLoggingEnable())
            InGrowLogging.enableLogging();
        else
            InGrowLogging.disableLogging();
        this.inGrowProject = builder.inGrowProject;
        this.networkStatusHandler = builder.networkStatusHandler;
    }


    private void handleFailure(Exception e) {
        if (isDebugMode) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else {
                throw new RuntimeException(e);
            }
        } else {
            InGrowLogging.log("Encountered error: " + e.getMessage());
        }
    }

}

