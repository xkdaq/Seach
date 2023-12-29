package com.shuati.application;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AppUtil {

    public static String getJson(Context context, String name) {
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getAssets().open(name));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
