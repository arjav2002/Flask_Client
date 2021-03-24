package com.example.flaskclient;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
    private AppCompatActivity activity;

    public ServerConnection(AppCompatActivity activityInstance, String url) {
        activity = activityInstance;
        this.url = url;
    }

    public void registerUser(User user) {
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        httpBuilder.addQueryParameter("type", "register");
        httpBuilder.addQueryParameter("name", user.getName());
        postRequest(httpBuilder);
    }

    public void sendMessage(User user, String message) {
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        httpBuilder.addQueryParameter("type", "response");
        httpBuilder.addQueryParameter("name", user.getName());
        httpBuilder.addQueryParameter("message", message);
        postRequest(httpBuilder);
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
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "Something went wrong:" + " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        call.cancel();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                System.out.println(response.body().string());
            }
        });
    }
}
