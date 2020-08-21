package com.hezhiheng.todolist.api;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RequestUser {
    private static final String USER_URL = "https://twc-android-bootcamp.github.io/fake-data/data/user.json";

    public String getUserFromWeb() {
        OkHttpClient client = new OkHttpClient();
        String result = "";
        Request request = new Request.Builder().url(USER_URL).build();
        try(Response response = client.newCall(request).execute()) {
            if (response.body()!=null){
                result = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
