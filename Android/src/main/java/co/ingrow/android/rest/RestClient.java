package co.ingrow.android.rest;

import org.json.JSONObject;

import co.ingrow.android.Const;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class RestClient {

    private static final OkHttpClient client = new OkHttpClient();
    private static final String API_URL = "https://event.ingrow.co/v1";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static Call getInstance(String apiKey, JSONObject bodyJson) {
        RequestBody body = RequestBody.create(bodyJson.toString(), JSON);
        Request request = new Request.Builder()
                .header(Const.API_KEY, apiKey)
                .header("Cache-Control", "no-cache")
                .post(body)
                .url(API_URL)
                .build();
        return client.newCall(request);

    }

}
