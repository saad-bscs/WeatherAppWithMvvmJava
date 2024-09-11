package com.example.weatherappandroid_java.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class Constant {

    public static final String BASE_URL = "http://api.weatherapi.com";
    public static final String apiKey = "your api key";

    public static String getJsonFromAssets(Context context, String fileName) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return jsonString;
    }
}
