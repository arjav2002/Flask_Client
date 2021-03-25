package com.example.flaskclient;

import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServerConnection {

    private String url;
    private MainActivity mainActivity;
    private volatile boolean waiting;
    private volatile String responseString;

    public ServerConnection(MainActivity activityInstance, String url) {
        mainActivity = activityInstance;
        this.url = url;
    }

    public void registerUser(User user) {
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        httpBuilder.addQueryParameter("type", "register");
        httpBuilder.addQueryParameter("name", user.getName());
        httpBuilder.addQueryParameter("address", user.getAddress());
        httpBuilder.addQueryParameter("age", user.getAge());
        httpBuilder.addQueryParameter("dad", user.getDad());
        httpBuilder.addQueryParameter("mom", user.getMom());
        httpBuilder.addQueryParameter("mom", user.getEmcon());
        httpBuilder.addQueryParameter("guardian", user.getGuardian());
        httpBuilder.addQueryParameter("phno", user.getPhno());

        postRequest(httpBuilder);
    }

    public String sendMessage(User user, String message) {
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        httpBuilder.addQueryParameter("type", "response");
        httpBuilder.addQueryParameter("message", message);
        postRequest(httpBuilder);
        waiting = true;
        while(waiting);
        return responseString;
    }

    private void postRequest(HttpUrl.Builder httpBuilder) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request
                .Builder()
                .url(httpBuilder.build())
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mainActivity, "Something went wrong:" + " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        call.cancel();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                responseString = response.body().string();
                waiting = false;
                System.out.println("Message response: " + responseString);
            }
        });
    }
}
